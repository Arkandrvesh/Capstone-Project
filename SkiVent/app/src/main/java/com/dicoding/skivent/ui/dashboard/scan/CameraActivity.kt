package com.dicoding.skivent.ui.dashboard.scan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.Gravity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.dicoding.skivent.R
import com.dicoding.skivent.databinding.ActivityCameraBinding
import com.dicoding.skivent.utils.Helper
import java.io.File
import java.lang.StringBuilder
import com.yalantis.ucrop.UCrop

class CameraActivity : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        initGallery()
        binding.let {
            it.btnShutter.setOnClickListener {
                takePhoto()
            }
            it.btnSwitch.setOnClickListener {
                cameraSelector =
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }
            it.btnGallery.setOnClickListener {
                startGallery()
            }
//            it.btnInfo.setOnClickListener {
//                Helper.showDialogInfo(this, getString(androidx.camera.core.R.string.UI_info_camera), Gravity.START)
//            }
        }
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(480, 720))
                .setTargetRotation(binding.viewFinder.display.rotation)
                .build()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().setTargetResolution(Size(480, 720)).build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture, imageAnalysis
                )
            } catch (e: Exception) {
                Helper.showDialogInfo(
                    this,
                    StringBuilder(getString(R.string.UI_error_camera_error))
                        .append(" : ")
                        .append(e.message)
                        .toString()
                )
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun initGallery() {
        openGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                val myFile = Helper.uriToFile(selectedImg, this@CameraActivity)
                val intent = Intent(this@CameraActivity, ScanResultActivity::class.java)
                intent.putExtra(ScanResultActivity.EXTRA_PHOTO_RESULT, myFile)
                intent.putExtra(
                    ScanResultActivity.EXTRA_CAMERA_MODE,
                    cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                )
                intent.flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
                startActivity(intent)
                this@CameraActivity.finish()
            }
        }
    }
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                startCrop(selectedImg)
            }
        }
    private fun startGallery() {
//        val intent = Intent()
//        intent.action = Intent.ACTION_GET_CONTENT
//        intent.type = "image/*"
//        val chooser = Intent.createChooser(intent, getString(R.string.UI_info_intent_image))
//        openGalleryLauncher.launch(chooser)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = Helper.createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Helper.showDialogInfo(
                        this@CameraActivity,
                        "${getString(R.string.UI_error_camera_take_photo)} : ${exc.message}"
                    )
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val sourceUri = Uri.fromFile(photoFile)
                    startCrop(sourceUri)
                }
            }
        )
    }

    private fun startCrop(sourceUri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "croppedImage.jpg"))
        val uCrop = UCrop.of(sourceUri, destinationUri)
            .withOptions(UCrop.Options().apply {
                setCompressionQuality(100) // Atur kualitas gambar yang di-crop
            })
            .withAspectRatio(2f, 3f) // Atur aspek rasio gambar yang di-crop
            .withMaxResultSize(480, 720) // Atur ukuran maksimum gambar yang di-crop
        val cropIntent = uCrop.getIntent(this@CameraActivity)
        cropLauncher.launch(cropIntent)
    }

    private val cropLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val croppedUri = UCrop.getOutput(result.data!!)
            if (croppedUri != null) {
                val intent = Intent(this@CameraActivity, ScanResultActivity::class.java)
                intent.putExtra(ScanResultActivity.EXTRA_PHOTO_RESULT, croppedUri.toString())
                intent.putExtra(ScanResultActivity.EXTRA_CAMERA_MODE, cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                intent.flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
                startActivity(intent)
                this@CameraActivity.finish()
            } else {
                Log.e("CameraActivity", "Cropped image URI is null")
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(result.data!!)
            // Tangani error (jika ada)
        }
    }
}