package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityThirdDemoBinding

class ThirdDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdDemoBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel //Создаём объект класса ViewModel для сохранения состояний

    private val startForResult = //Это нужно проссто запомнить
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if (result.resultCode == RESULT_OK){
                val reply = result.data?.getStringExtra("EXTRA_REPLY")
                binding.tv1.text = "Answer: $reply"
            }
            else
                binding.tv1.text = "Ответ со второго экрана не пришёл("
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModelProvider = ViewModelProvider(this) //Явно создаём этот элемент
        mainActivityViewModel = viewModelProvider[MainActivityViewModel::class.java] //Привязываем его к этой активности

        binding = ActivityThirdDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainActivityViewModel.getEditText().observe(this@ThirdDemoActivity){ //Вешаем наблюдателя, что бы это ни значило
            binding.btnFromEditTextFirst.text = it.toString()
        }
        binding.etFirst.addTextChangedListener { //Слушаем, меняется ли поле или нет
            mainActivityViewModel.getEditText().value = it.toString()
        }

        with(binding){
            btnFromEditTextFirst.setOnClickListener {
                val intent = Intent(this@ThirdDemoActivity, ThirdDemoActivity2::class.java)
                intent.putExtra("string", etFirst.text.toString())
//                startActivity(intent) Теперь переходим к новому экрану по новому
                startForResult.launch(intent)
            }
        }

    }
}