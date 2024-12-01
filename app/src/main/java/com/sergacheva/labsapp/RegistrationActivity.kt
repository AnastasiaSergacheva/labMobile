package com.sergacheva.labsapp

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

var EMAIL_BUTTON_PRESSED = false
var PHONE_BUTTON_PRESSED = false


class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        val by_phone =findViewById<TextView>(R.id.ByPhoneTextView)
        val by_email = findViewById<TextView>(R.id.ByEmailTextView)

        val enter_field = findViewById<EditText>(R.id.EntryEditText)
        val password_field = findViewById<EditText>(R.id.PasswordEditText)
        val repeat_password_field = findViewById<EditText>(R.id.RepeatPasswordEditText)

        val registration_button = findViewById<Button>(R.id.RegistrationButton)

        by_phone.setOnClickListener {
            //Чтобы не переобозначать по каждому нажатию
            if (!PHONE_BUTTON_PRESSED) {
                enter_field.setHint(R.string.enter_phone)
                enter_field.inputType = InputType.TYPE_CLASS_PHONE
                changeColorText(by_email, by_phone)
                PHONE_BUTTON_PRESSED = true
                EMAIL_BUTTON_PRESSED = false
            }
        }

        by_email.setOnClickListener {
            //Чтобы не переобозначать по каждому нажатию
            if (!EMAIL_BUTTON_PRESSED) {
                enter_field.setHint(R.string.enter_email)
                enter_field.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                changeColorText(by_email, by_phone)
                EMAIL_BUTTON_PRESSED = true
                PHONE_BUTTON_PRESSED = false
            }
        }

        registration_button.setOnClickListener {
            (errorHandling(enter_field, password_field, repeat_password_field))
        }

    }

    fun changeColorText (email_text: TextView, phone_text: TextView, ) {
        if (EMAIL_BUTTON_PRESSED) {
            email_text.setTextColor(ContextCompat.getColor(this, R.color.purple))
            phone_text.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
        else {
            email_text.setTextColor(ContextCompat.getColor(this, R.color.black))
            phone_text.setTextColor(ContextCompat.getColor(this, R.color.purple))
        }
    }

    private fun errorHandling(enterField: EditText, passwordField: EditText, repeatPasswordField: EditText): Boolean{
        when {
            enterField.text.isNotEmpty() && EMAIL_BUTTON_PRESSED && !enterField.text.contains("@") -> {
                showShortToast(R.string.error_invalid_email)
                return false
            }

            enterField.text.isNotEmpty() && PHONE_BUTTON_PRESSED && !enterField.text.contains("+") -> {
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