package com.example.myapplication

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityCreateUiinCodeBinding

class CreateUIInCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateUiinCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUiinCodeBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        val tv: TextView = TextView(this) //Создаём объект
//        tv.text = "Проверка текста" //Задаём его свойства
//        tv.textSize=(35f)
//        tv.setBackgroundColor(getColor(R.color.testColor))
//
//        val tv1 = TextView(this) //Повторим для наглядности
//        tv1.text = "Иная проверка текста"
//        tv1.textSize=20f
//
//        val ll = LinearLayout(this) //Созадим себе Layout
//        val llparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) //Задаём ему параметры
//        ll.orientation = LinearLayout.HORIZONTAL //Добавим ориентацию для линейного layout
//
//        tv.layoutParams = llparams //Задаём эти параметры каждому элементу
//        tv1.layoutParams = llparams
//
//        ll.addView(tv) //Добавляем их на линейный макет
//        ll.addView(tv1)
//        setContentView(ll) //Отображаем макет

        val pipa = resources.getStringArray(R.array.stringFor24)

        val display: Display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenWidth: Int = point.x
        val screenHeight: Int = point.y

        val width = screenWidth.toString()
        val height = screenHeight.toString()

        val pipaHeight = screenHeight / 25


        val ll = LinearLayout(this)
        ll.weightSum=25f
        ll.orientation = LinearLayout.VERTICAL

        val llparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val etparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val tvInnerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, pipaHeight)
        val etInnerParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pipaHeight)

        val llList: MutableList<LinearLayout> = mutableListOf()
        val tvList: MutableList<TextView> = mutableListOf()
        val etList: MutableList<EditText> = mutableListOf()

        for(i in 0..24) {
            tvList.add(TextView(this))
            tvList[i].text = pipa[i]
            tvList[i].gravity = Gravity.CENTER
            tvList[i].layoutParams = tvInnerParams

            etList.add(EditText(this))
            etList[i].hint = pipa[i]
            etList[i].textSize = 10f
            etList[i].layoutParams = etInnerParams

            llList.add(LinearLayout(this))
            llList[i].orientation = LinearLayout.HORIZONTAL
            llList[i].addView(tvList[i])
            llList[i].addView(etList[i])

            ll.addView(llList[i])
        }

        setContentView(ll)
    }
}