package com.sergacheva.labsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

// Константы для SharedPreferences
const val SETTINGS = "settings"
const val USER_AUTHORISED = "user_authorized"
const val AUTOMATIC_ENTRANCE = "user_auto_log_in"
const val USER_REGISTERED = "user_registered"
const val USER_EMAIL= "user_email"
const val USER_PHONE = "user_phone"
const val USER_PASSWORD= "user_password"

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Интенты для перехода между активити
        val intentToContent = Intent(this, ContentActivity::class.java)
        val intentToLogin = Intent(this, LoginActivity::class.java)
        val intentToRegistration= Intent(this, RegistrationActivity::class.java)

        // Для доступа к локальному хранилищу
        val sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

        // Если уже записан email/телефон и пароль
        if ((thisDataSaved(sharedPreferences, USER_EMAIL) || thisDataSaved(sharedPreferences, USER_PHONE))
            && thisDataSaved(sharedPreferences, USER_PASSWORD)){
            // Смотрим отмечена ли галочка
            if ( thisDataSaved(sharedPreferences, AUTOMATIC_ENTRANCE) && getBooleanData(sharedPreferences, AUTOMATIC_ENTRANCE)) {
                startActivity(intentToContent)
            }
            else {
                startActivity(intentToLogin)
            }
        }
        else {
        // Иначе идем регистрироваться
            startActivity(intentToRegistration)
        }

    }
}

// Функции для работы с SharedPreferences
fun thisDataSaved(settings: SharedPreferences,data: String): Boolean {
    return settings.contains(data)
}

fun getBooleanData(settings: SharedPreferences, data: String):Boolean {
    return settings.getBoolean(data, false)
}

fun AddBooleanData(settings: SharedPreferences,typeData: String, data: Boolean) {
    return settings.edit().putBoolean(typeData,data).apply()
}

fun getStringData(settings: SharedPreferences, data: String): String? {
    return settings.getString(data,"")
}

fun AddStringData(settings: SharedPreferences,typeData: String, data: String) {
    return settings.edit().putString(typeData,data).apply()
}

