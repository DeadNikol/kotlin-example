package com.example.myapplication

import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivitySaveDataOnAndroidBinding
import java.io.File
import java.io.FileOutputStream

class SaveDataOnAndroidActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveDataOnAndroidBinding
    val REQUEST_CODE = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySaveDataOnAndroidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text : String = "Это строка для проверки сохранения файла в общую папку в формате хотя бы .txt"
        binding.btnToSaveInDownloads.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 29) {
                saveFileUsingMediaStore("Дежурный файл", text)
                binding.tvForURI.text = Environment.DIRECTORY_DOWNLOADS
            }
            else{
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
                }
                saveFileToDownloads("Дежурный файл 2.txt", text)
            }
        }

        binding.btnToSaveInDownloadsWithLowAPI.setOnClickListener {
            saveFileToDownloads("Дежурный файл 2.txt", text)
        }

        binding.btnToCreateJSON.setOnClickListener {
            val pipa: Pipa = Pipa("Николай", 20)
            Toast.makeText(this, pipa.print(), Toast.LENGTH_SHORT).show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveFileUsingMediaStore(fileName: String, content: String) {
        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "text/plain")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        uri?.let {
            contentResolver.openOutputStream(it).use { outputStream ->
                outputStream?.write(content.toByteArray())
                Toast.makeText(this, "Файл сохранен: $fileName", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveFileToDownloads(fileName: String, content: String) {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        try {
            FileOutputStream(file).use { output ->
                output.write(content.toByteArray())
                output.flush()
            }
            Toast.makeText(this, "Файл сохранен: ${file.absolutePath}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show()
        }
    }

}

data class Pipa(val name: String, val age: Int){
    fun print(): String{
        return "{\"name\":$name, \"age\":$age}"
    }
}