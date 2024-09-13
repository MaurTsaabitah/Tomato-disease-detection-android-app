# Tomato Leaf Disease Detection App

## Overview

This application is designed to detect diseases in tomato leaves using a Convolutional Neural Network (CNN). The model is integrated into an Android application, developed using Android Studio with Kotlin. The app allows users to upload images of tomato leaves and get predictions on whether the leaves are healthy or affected by a particular disease.

## Features

- **Image Upload**: Users can upload images of tomato leaves.
- **Disease Detection**: The app uses a CNN model to analyze the uploaded image and predict if the leaf is healthy or affected by disease.
- **User Interface**: Built with a user-friendly interface for easy navigation and interaction.

## Technologies Used

- **Convolutional Neural Network (CNN)**: For disease detection.
- **Android Studio**: IDE used for app development.
- **Kotlin**: Programming language used for Android app development.
- **TensorFlow Lite**: Framework for deploying machine learning models on mobile devices.

## Installation

1. **Clone the Repository**

    ```bash
    git clone https://github.com/yourusername/tomato-leaf-disease-detection.git
    ```

2. **Open the Project**

    Open the project in Android Studio.

3. **Build the Project**

    Click on `Build` > `Rebuild Project` in Android Studio.

4. **Add TensorFlow Lite Model**

    Place the TensorFlow Lite model file (`model.tflite`) in the `assets` directory of your Android project.

5. **Run the Application**

    Connect your Android device or start an emulator and run the application.

## Usage

1. **Open the App**

    Launch the app on your device.

2. **Capture or Upload Image**

    Use the "pilih gambar" option to capture or upload an image of a tomato leaf.

3. **Get Prediction**

    The app will process the image after you click the "identifikasi" button and display whether the leaf is healthy or affected by disease.

## Model Details

- **Model Type**: Convolutional Neural Network (CNN)
- **Framework**: TensorFlow Lite
- **Accuracy**: 99.5%

## Contact

For any questions or feedback, please reach out to mauratsaabitah@gmail.com


