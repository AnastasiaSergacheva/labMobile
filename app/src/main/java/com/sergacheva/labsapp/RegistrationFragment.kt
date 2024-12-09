package com.sergacheva.labsapp

import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

var EMAIL_BUTTON_PRESSED = true


class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root =  inflater.inflate(R.layout.fragment_registration, container, false)
        val navController = NavHostFragment.findNavController(this)
        val sharedPreferences = requireActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)


        val by_phone = root.findViewById<TextView>(R.id.ByPhoneTextView)
        val by_email = root.findViewById<TextView>(R.id.ByEmailTextView)

        val enter_field = root.findViewById<EditText>(R.id.EntryEditText)
        // Делаем изначально вход по email
        enter_field.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        by_email.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
        enter_field.setHint(R.string.enter_email)
        val password_field = root.findViewById<EditText>(R.id.PasswordEditText)
        val repeat_password_field = root.findViewById<EditText>(R.id.RepeatPasswordEditText)

        val registration_button = root.findViewById<Button>(R.id.RegistrationButton)

        // Работа при нажатии на "По номеру"
        by_phone.setOnClickListener {
            enter_field.text.clear() // Очищаем поле для удобства
            enter_field.setHint(R.string.enter_phone) // Меняем задний текст
            enter_field.inputType = InputType.TYPE_CLASS_PHONE // Меняем тип клавиатуры
            by_email.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            by_phone.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))

            EMAIL_BUTTON_PRESSED = false
        }

        // Работа при нажатии на "По email"
        by_email.setOnClickListener {
            enter_field.text.clear() // Очищаем поле для удобства
            enter_field.setHint(R.string.enter_email) // Меняем задний текст
            enter_field.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS // Меняем тип клавиатуры
            by_email.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
            by_phone.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            EMAIL_BUTTON_PRESSED = true
        }

        // Работа при нажатии кнопки Регистрации
        registration_button.setOnClickListener {
            if(enter_field.text.isNotEmpty() && password_field.text.isNotEmpty()) {
                val auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(enter_field.text.toString(),
                    password_field.text.toString()).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            AddBooleanData(sharedPreferences, USER_REGISTERED, true)
                            navController.navigate(R.id.oneFragment)
                        }
                    }.addOnFailureListener { exception ->
                        errorHandling(enter_field, password_field, repeat_password_field)
                    }
            }

        }

        return root
    }

    private fun errorHandling(enterField: EditText, passwordField: EditText, repeatPasswordField: EditText): Boolean{
        when {
            enterField.text.isNotEmpty() && EMAIL_BUTTON_PRESSED && !enterField.text.contains("@") -> {
                showShortToast(R.string.error_invalid_email)
                return false
            }

            enterField.text.isNotEmpty() && !EMAIL_BUTTON_PRESSED && !enterField.text.contains("+") -> {
                showShortToast(R.string.error_invalid_phone)
                return false
            }

            passwordField.text.length in 1..7 -> {
                showShortToast(R.string.error_invalid_length_password)
                return false
            }

            passwordField.text.toString() != repeatPasswordField.text.toString() -> {
                showShortToast(R.string.error_mismatch_password)
                return false
            }
        }
        return true
    }

    private fun showShortToast(error: Int){
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}