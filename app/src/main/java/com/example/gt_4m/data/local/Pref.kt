package com.example.gt_4m.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun isUserSeen():Boolean{
        return pref.getBoolean(SEEN_KEY, false)
    }


    fun saveUserSeen() {
        pref.edit().putBoolean(SEEN_KEY, true).apply()
    }


    fun getUserName():String{
        return pref.getString(CHANGE_NAME, "User").toString()
    }

    fun saveNewName(name: String) {
        pref.edit().putString(CHANGE_NAME, name).apply()
    }

    companion object {
        const val PREF_NAME="pref.task"
        const val SEEN_KEY="seen.key"
        const val CHANGE_NAME="change.name"
    }
}