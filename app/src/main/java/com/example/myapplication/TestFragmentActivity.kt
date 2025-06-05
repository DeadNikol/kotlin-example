package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityTestFragmentBinding

class TestFragmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestFragmentBinding
    val TAG = "LISTVIEW"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestFragmentBinding.inflate(layoutInflater)
        supportFragmentManager.beginTransaction().add(R.id.flFirstFragment, BlankFragment()).commit() //Связываем FrameLayout с фрагментом

        enableEdgeToEdge()
        setContentView(binding.root)


        val list: MutableList<String> = mutableListOf()
        for(i in 1..30){
            when(i%3){
                0 -> list.add( "A")
                1 -> list.add( "B")
                2 -> list.add("C")
            }
        }


        val adapter = ArrayAdapter(this@TestFragmentActivity, android.R.layout.simple_list_item_1, list)
        binding.lv1.adapter = adapter

    }
}