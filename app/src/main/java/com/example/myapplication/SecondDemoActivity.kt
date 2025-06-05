package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.FirstDemoActivity.extraWord
import com.example.myapplication.databinding.ActivitySecondDemoBinding

class SecondDemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondDemoBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivitySecondDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)



        with(binding){
            btnOpenFirst.setOnClickListener {
                val intent = Intent(this@SecondDemoActivity, FirstDemoActivity::class.java)
                startActivity(intent)
                finish()
            }

            val text = intent.getStringExtra("EXTRA_KEY_TEXT")
            val number = intent.getIntExtra("EXTRA_KEY_NUMBER", 0)

            //serializable
//            val word: extraWord = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                intent.getSerializableExtra("EXTRA_KEY_WORD", extraWord::class.java) as extraWord
//            } else {
//                intent.getSerializableExtra("EXTRA_KEY_WORD") as extraWord
//            }

//            parcelable
            val word= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("EXTRA_KEY_WORD", extraWord::class.java)
            } else {
                intent.getParcelableExtra("EXTRA_KEY_WORD")
            }


//            //bundle
//            val bundle = intent.extras
//            val text1 = bundle?.getString("EXTRA_KEY_TEXT")
//            val text2 = bundle?.getParcelable("EXTRA_KEY_WORD", extraWord::class.java)


            tvText.text = text
            tvNumber.text = number.toString()
            tvWord.text = word?.original
            btnCounter.setOnClickListener {
                tvCounter.text = (tvCounter.text.toString().toInt() + 1).toString()
            }
            btnCounter.setOnLongClickListener {
                Toast.makeText(this@SecondDemoActivity, "It just works", Toast.LENGTH_SHORT).show()
                true }
            tvText.setOnTouchListener { v, event -> when(event.action){
                MotionEvent.ACTION_DOWN -> Toast.makeText(this@SecondDemoActivity,"works", Toast.LENGTH_SHORT).show() }
                true
            }

        }
    }



}
