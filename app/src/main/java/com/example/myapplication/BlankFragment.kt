package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private lateinit var binding: FragmentBlankBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("FRAGMENT", "onAttach")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( //Связываем xml с фрагментом
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //Тут пишем всю логику
        super.onViewCreated(view, savedInstanceState)
        binding.sw1.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.tv1.text = if(isChecked) "true" else "false"
            buttonView.text = if(isChecked) "Включено!" else "Выключено!"
        }
    }
}