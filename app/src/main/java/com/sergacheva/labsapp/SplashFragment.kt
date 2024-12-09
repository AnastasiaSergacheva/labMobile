package com.sergacheva.labsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

// Константы для SharedPreferences
const val SETTINGS = "settings"
const val AUTOMATIC_ENTRANCE = "user_auto_log_in"
const val USER_REGISTERED = "user_registered"

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_splash, container, false)
        val navController = NavHostFragment.findNavController(this)
        val sharedPreferences = requireActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        Log.i("InfoSharedPreferences", sharedPreferences.all.toString())
        //sharedPreferences.edit().clear().apply()
        //Если пол-ть зареган, то проверяем дальше, иначе сразу регаться
        if (getBooleanData(sharedPreferences, USER_REGISTERED)) {
            // Если есть автомат.вход, то на фрагмент со списком
            if (thisDataSaved(sharedPreferences, AUTOMATIC_ENTRANCE) && getBooleanData(sharedPreferences, AUTOMATIC_ENTRANCE)) {
                navController.navigate(R.id.oneFragment)
            }
            else {
                navController.navigate(R.id.loginFragment)
            }
        }
        else {
            navController.navigate(R.id.registrationFragment)
        }
        return root
    }
}


// Функции для работы с SharedPreferences
fun thisDataSaved(settings: SharedPreferences, data: String): Boolean {
    return settings.contains(data)
}

fun getBooleanData(settings: SharedPreferences, data: String):Boolean {
    return settings.getBoolean(data, false)
}

fun AddBooleanData(settings: SharedPreferences, typeData: String, data: Boolean) {
    return settings.edit().putBoolean(typeData,data).apply()
}

fun getStringData(settings: SharedPreferences, data: String): String? {
    return settings.getString(data,"")
}

fun AddStringData(settings: SharedPreferences, typeData: String, data: String) {
    return settings.edit().putString(typeData,data).apply()
}