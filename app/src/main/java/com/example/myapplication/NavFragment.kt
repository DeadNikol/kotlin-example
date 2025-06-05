package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.myapplication.databinding.FragmentNavBinding

class NavFragment : Fragment() {
    private lateinit var binding: FragmentNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNavBinding.inflate(layoutInflater)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bundle = Bundle()
        binding.btnToSecondNavFragment.setOnClickListener {view: View -> //Для кнопки создаём слушателя
            val text = binding.et1.text.toString()
            val hello = "Hello, $text!"

            val action = NavFragmentDirections.actionNavFragmentToNavFragment2(text) //Тут происходит передача файлов через safeargs, которые ещё нужно подключить по удобной инструкции с офф. сайта.
//            bundle.putString("MyArg", hello)

//            val extras = FragmentNavigatorExtras( //Мне не нравится, переход между фрагментами прикольнее
//                binding.et1 to "editText" //Штука для анимаций. Конкретный объект привязывается к TransitionName, который у обоих компонентов должен совпадать
//            )

            view.findNavController().navigate(action) //Вызываем метод перехода, описанный в nav_draph: это навигационный файл, что бы сам себе менял активности и/или фрагменты
//            view.findNavController().navigate(action, extras)
        }
    }
}