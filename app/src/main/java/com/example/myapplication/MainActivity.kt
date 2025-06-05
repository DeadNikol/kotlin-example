package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ContentInfoCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityLearnWordBinding
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_learn_word)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.layoutAnswer2.setOnClickListener{
            markAnswerCorrect()
        }

        binding.layoutAnswer1.setOnClickListener{
            markAnswerWrong()
        }

        binding.btnCorrectSkip.setOnClickListener {
            markAnswerNeutral()
        }
        binding.btnWrongSkip.setOnClickListener {
            markAnswerNeutral()
        }
        with(binding){
            btnExit.setOnClickListener {
                val intent = Intent(this@MainActivity, FirstDemoActivity::class.java)
            }
        }
    }

    private fun markAnswerNeutral() {
        with(binding){
            for (layout in listOf(layoutAnswer1, layoutAnswer2)){
                layout.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_container)
            }

            for (textView in listOf(tvVariantValue1, tvVariantValue2)){
                textView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.textVariant))
            }

            for (textView in listOf(tvVariatnNumber1, tvVariantNumber2)){
                textView.apply{
                    background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_variants)
                    setTextColor(ContextCompat.getColor(this@MainActivity, R.color.textVariant))
                }
            }
            layoutCorrect.isVisible = false
            layoutWrong.isVisible = false
            btnSkip.isVisible = true
        }
    }

    private fun markAnswerWrong() {
        binding.layoutAnswer1.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_container_wrong)

        binding.tvVariatnNumber1.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong)

        binding.tvVariatnNumber1.setTextColor(
            ContextCompat.getColor(this@MainActivity, R.color.white))

        binding.tvVariantValue1.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.wrongAnswerColor))

        binding.btnSkip.isVisible = false
        binding.layoutCorrect.isVisible = false
        binding.layoutWrong.isVisible = true
    }

    private fun markAnswerCorrect() {
        binding.layoutAnswer2.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_container_correct)

        binding.tvVariantNumber2.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )

        binding.tvVariantNumber2.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        binding.tvVariantValue2.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            )
        )

        binding.btnSkip.isVisible = false
        binding.layoutWrong.isVisible = false
        binding.layoutCorrect.isVisible = true
    }
}

