package com.example.myapplication.Room.fragments.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.Room.model.User
import com.example.myapplication.databinding.FragmentUpdateBinding
import com.example.myapplication.Room.viewModel.UserViewModel


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>() //Для этой штуки нужны safe args, а в графе навигации добавить аргумент к экрану, которому мы хотим передать значения
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentUpdateBinding.inflate(layoutInflater)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.etFirstNameUpdate.setText(args.currentuser.firstName)
        binding.etLastNameUpdate.setText(args.currentuser.lastName)
        binding.etAgeUpdate.setText(args.currentuser.age.toString())

        binding.addBtnUpdate.setOnClickListener {
            if (binding.etFirstNameUpdate.text.isNotEmpty() && binding.etLastNameUpdate.text.isNotEmpty() && binding.etAgeUpdate.text.isNotEmpty()) {
                val updatedUser = User(
                    args.currentuser.id,
                    binding.etFirstNameUpdate.text.toString(),
                    binding.etLastNameUpdate.text.toString(),
                    binding.etAgeUpdate.text.toString().toInt()
                )
                mUserViewModel.updateUser(updatedUser)
                Toast.makeText(requireContext(), "updated successfully!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        binding.fabDelete.setOnClickListener {
            deleteUser()
        }

        return binding.root
    }

    private fun deleteUser(){
         val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("yes") {_, _ ->
            mUserViewModel.deleteUser(args.currentuser)
            Toast.makeText(requireContext(),"Successfelly removedL ${args.currentuser.firstName}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ ->

        }
        builder.setTitle("Delete ${args.currentuser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentuser.firstName}?")
        builder.create().show()
    }
}