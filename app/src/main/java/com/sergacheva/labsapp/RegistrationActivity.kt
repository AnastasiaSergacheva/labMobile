package com.sergacheva.labsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

var EMAIL_BUTTON_PRESSED = true

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        val intentToContent = Intent(this, ContentActivity::class.java)
        // Для доступа к локальному хранилищу
        val sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        val by_phone =findViewById<TextView>(R.id.ByPhoneTextView)
        val by_email = findViewById<TextView>(R.id.ByEmailTextView)

        val enter_field = findViewById<EditText>(R.id.EntryEditText)
        // Делаем изначально вход по email
        enter_field.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        by_email.setTextColor(ContextCompat.getColor(this, R.color.purple))
        enter_field.setHint(R.string.enter_email)
        val password_field = findViewById<EditText>(R.id.PasswordEditText)
        val repeat_password_field = findViewById<EditText>(R.id.RepeatPasswordEditText)

        val registration_button = findViewById<Button>(R.id.RegistrationButton)

        // Работа при нажатии на "По номеру"
        by_phone.setOnClickListener {
            enter_field.setHint(R.string.enter_phone)
            enter_field.inputType = InputType.TYPE_CLASS_PHONE
            by_email.setTextColor(ContextCompat.getColor(this, R.color.black))
            by_phone.setTextColor(ContextCompat.getColor(this, R.color.purple))
            EMAIL_BUTTON_PRESSED = false
        }

        // Работа при нажатии на "По email"
        by_email.setOnClickListener {
            enter_field.setHint(R.string.enter_email)
            enter_field.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            by_email.setTextColor(ContextCompat.getColor(this, R.color.purple))
            by_phone.setTextColor(ContextCompat.getColor(this, R.color.black))
            EMAIL_BUTTON_PRESSED = true

        }

        // Работа при нажатии кнопки Регистрации
        registration_button.setOnClickListener {
            if (errorHandling(enter_field, password_field, repeat_password_field)) {
                // Записываем данные в лок.хранилище
                if(EMAIL_BUTTON_PRESSED)
                    AddStringData(sharedPreferences, USER_EMAIL, enter_field.text.toString())
                else
                    AddStringData(sharedPreferences, USER_PHONE, enter_field.text.toString())
                AddStringData(sharedPreferences, USER_PASSWORD, password_field.text.toString())
                AddBooleanData(sharedPreferences, USER_REGISTERED, true)
                startActivity(intentToContent)
            }
        }

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
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}