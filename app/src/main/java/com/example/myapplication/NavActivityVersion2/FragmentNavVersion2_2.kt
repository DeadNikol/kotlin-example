package com.example.myapplication.NavActivityVersion2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNavVersion22Binding

class FragmentNavVersion2_2 : Fragment() {
    private lateinit var binding: FragmentNavVersion22Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNavVersion22Binding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = NavHostFragment.findNavController(this)

        val bottomBar = binding.bottomNavigationBarInFragment
        bottomBar.setupWithNavController(navController)
        //Эта команда именно в такой конфигурации
        bottomBar.setOnNavigationItemSelectedListener{item ->
            navController.navigate(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        return binding.root
    }

}