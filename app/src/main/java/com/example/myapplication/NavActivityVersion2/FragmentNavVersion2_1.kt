package com.example.myapplication.NavActivityVersion2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNavVersion21Binding

class FragmentNavVersion2_1 : Fragment() {
    private lateinit var binding: FragmentNavVersion21Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentNavVersion21Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Находим навигационный контроллер, закреплённый за этим фрагментом
        val navController = NavHostFragment.findNavController(this)

        //Обработка переходов на следующие экраны
        binding.btn2.setOnClickListener { navController.navigate(R.id.fragmentNavVersion2_2) }
        binding.btn3.setOnClickListener { navController.navigate(R.id.fragmentNavVersion2_3) }
        binding.btn4.setOnClickListener { navController.navigate(R.id.fragmentNavVersion2_4) }


        return binding.root
    }
}