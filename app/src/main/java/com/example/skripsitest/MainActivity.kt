package com.example.skripsitest

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.skripsitest.ml.Model2Dauntomat
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : AppCompatActivity() {

    private lateinit var selectBtn: Button
    private lateinit var identifyBtn: Button
    private lateinit var resView: TextView
    private lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap

    // Preprocess image
    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(256, 256, ResizeOp.ResizeMethod.BILINEAR))
        .add(NormalizeOp(0.0f, 1.0f / 255.0f))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        selectBtn = findViewById(R.id.selectBtn)
        identifyBtn = findViewById(R.id.identifyBtn)
        resView = findViewById(R.id.resView)
        imageView = findViewById(R.id.imageView)

        val labels = application.assets.open("labels.txt").bufferedReader().readLines()

        // Set padding for insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Setup listener for selectBtn
        selectBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // Setup listener for predictBtn
        identifyBtn.setOnClickListener {
            if (::bitmap.isInitialized) {
                predictImage(labels)
            } else {
                resView.text = "Please select an image first."
            }
        }
    }

    fun preprocessImage(bitmap: Bitmap): TensorImage {
        // Convert the Bitmap to a TensorImage
        val tensorImage = TensorImage()
        tensorImage.load(bitmap)

        // Expand the dimensions to match the expected input shape of the model
        val expandedTensorImage = TensorImage(DataType.FLOAT32)
        expandedTensorImage.load(bitmap)
        expandedTensorImage.buffer.rewind() // Rewind the buffer to the beginning
        return expandedTensorImage
    }

    private fun predictImage(labels: List<String>) {
        val tensorImage = preprocessImage(bitmap)


        val model = Model2Dauntomat.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxIdx = 0
        outputFeature0.forEachIndexed { index, fl ->
            if (outputFeature0[maxIdx]<fl){
                maxIdx = index
            }
        }

        resView.setText(labels[maxIdx])

        model.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val uri: Uri? = data.data
            if (uri != null) {
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}