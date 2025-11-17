package com.example.myapplication.CameraX

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import com.example.myapplication.databinding.ActivityCameraXactivityBinding

class CameraXActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityCameraXactivityBinding
    private lateinit var imageCapture: ImageCapture
    private var camera: Camera? = null
    private val REQUEST_CODE_PERMISSIONS = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCameraXactivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Запускаем камеру при получении разрешения
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnTakePhoto.setOnClickListener {
            takePhoto()
        }

        binding.btnToGallery.setOnClickListener {
            val intentPic = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startForResult.launch(intentPic)
        }
    }

    private val startForResult = //Это я свистнул из старого проекта для получения фотографии
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                var a = this.contentResolver.openInputStream(result.data?.data!!)
                    ?.use { inputStream -> //Я полчил Uri(Вроде), а теперь я говорю, что этот Uri я раскодирую через Bitmap, то бишь картинку
                        BitmapFactory.decodeStream(inputStream)
                    }
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Разрешения не получены",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //Функция для привязки камеры к объекту в разметке
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider = binding.pv.surfaceProvider
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Отвязываем все use cases перед привязкой
                cameraProvider.unbindAll()

                // Привязываем камеру к lifecycle фрагмента
                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Log.e("Photo", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

//    Функция для сохранения фото в память
    private fun takePhoto() {
        // Получаем ссылку на медиа хранилище
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Создаем объект output options
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            this.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Фото сохранено: ${output.savedUri}"
                    Toast.makeText(this@CameraXActivity, msg, Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Photo", "Photo capture failed: ${exception.message}", exception)
                }
            }
        )
    }
}