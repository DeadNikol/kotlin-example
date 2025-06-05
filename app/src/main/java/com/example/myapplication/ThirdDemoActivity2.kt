package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityThirdDemo2Binding

class ThirdDemoActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityThirdDemo2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdDemo2Binding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        with(binding){

            tv1.text = intent.getStringExtra("string")

            btn1.setOnClickListener {
                val replyIntent = Intent().putExtra("EXTRA_REPLY",et1.text.toString())
                if(et1.text.isNotEmpty()){
                    setResult(RESULT_OK, replyIntent)}
                else{
                    setResult(RESULT_CANCELED, replyIntent)
                }
                finish()
            }
        }

    }
}