package com.example.gt_4m.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream


class Pref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun isUserSeen(): Boolean {
        return pref.getBoolean(SEEN_KEY, false)
    }


    fun saveUserSeen() {
        pref.edit().putBoolean(SEEN_KEY, true).apply()
    }


    fun getUserName(): String {
        return pref.getString(CHANGE_NAME, "User").toString()
    }

    fun saveNewName(name: String) {
        pref.edit().putString(CHANGE_NAME, name).apply()
    }

    fun getUserImg(): String {
        return pref.getString(CHANGE_IMG, "Image").toString()
    }

    fun saveNewImg(img: Uri) {
        pref.edit().putString(CHANGE_IMG, img.toString()).apply()
    }


    companion object {
        const val PREF_NAME = "pref.task"
        const val SEEN_KEY = "seen.key"
        const val CHANGE_NAME = "change.name"
        const val CHANGE_IMG = "change.img"
    }
}