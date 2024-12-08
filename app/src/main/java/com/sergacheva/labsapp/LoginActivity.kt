package com.sergacheva.labsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val intentToContent = Intent(this, ContentActivity::class.java)
        // Для доступа к локальному хранилищу
        val sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        val enter_field = findViewById<EditText>(R.id.EntryEditText)
        val password_field = findViewById<EditText>(R.id.PasswordEditText)
        val checkbox = findViewById<CheckBox>(R.id.LoginAutomaticallyCheckBox)

        val login_button = findViewById<Button>(R.id.LoginButton)

        login_button.setOnClickListener {
            if (errorHandling(sharedPreferences, enter_field, password_field)) {
                AddBooleanData(sharedPreferences, USER_AUTHORISED, true)
                AddBooleanData(sharedPreferences, AUTOMATIC_ENTRANCE, checkbox.isChecked)
                startActivity(intentToContent)
            }
        }
    }

    private fun errorHandling(settings: SharedPreferences, enterField: EditText, passwordField: EditText): Boolean{
        when {
            enterField.text.isEmpty() || passwordField.text.isEmpty() -> {
                showShortToast(R.string.error_invalid_login_or_phone)
                return false
            }

            enteredDataIsCorrect(settings, enterField.text.toString(), passwordField.text.toString()) -> {
                showShortToast(R.string.error_invalid_login_or_phone)
                return false
            }
        }
        return true
    }

    fun enteredDataIsCorrect(settings: SharedPreferences, enter_data: String, password_data: String): Boolean {
        val userEmail = getStringData(settings, USER_EMAIL)
        val userPhone = getStringData(settings, USER_PHONE)
        val userPassword = getStringData(settings, USER_PASSWORD)

        return ((userEmail == enter_data) || (userPhone == enter_data)) &&
                (userPassword == password_data)
    }

    private fun showShortToast(error: Int){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}