package com.example.myapplication.Room.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.Room.model.User
import com.example.myapplication.databinding.FragmentAddBinding
import com.example.myapplication.Room.viewModel.UserViewModel


class addFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var mUserviewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mUserviewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.addBtn.setOnClickListener {
            insertDataToDatabase( )
        }

        return binding.root
    }

    private fun insertDataToDatabase(){
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val age = binding.etAge.text.toString()

        if(binding.etFirstName.text.isNotEmpty() && binding.etLastName.text.isNotEmpty() && binding.etAge.text.isNotEmpty()){
            val user = User(0, firstName, lastName, age.toInt())
            mUserviewModel.addUser(user)
            Toast.makeText(requireContext(), "successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }



}