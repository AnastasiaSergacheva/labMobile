package com.sergacheva.labsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_login, container, false)
        val navController = NavHostFragment.findNavController(this)
        val sharedPreferences = requireActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        val enter_field =  root.findViewById<EditText>(R.id.EntryEditText)
        val password_field =  root.findViewById<EditText>(R.id.PasswordEditText)
        val checkbox =  root.findViewById<CheckBox>(R.id.LoginAutomaticallyCheckBox)

        val login_button =  root.findViewById<Button>(R.id.LoginButton)

        login_button.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(enter_field.text.toString(),
                password_field.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        AddBooleanData(sharedPreferences, AUTOMATIC_ENTRANCE, checkbox.isChecked)
                        navController.navigate(R.id.oneFragment)
                    }
                }.addOnFailureListener { exception ->
                    showShortToast(R.string.error_invalid_login_or_phone)
                }
        }
        return root
    }

    private fun showShortToast(error: Int){
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

}