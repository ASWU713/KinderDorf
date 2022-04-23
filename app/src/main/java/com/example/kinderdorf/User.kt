package com.example.kinderdorf

import android.widget.TextView
import com.parse.Parse
import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("User")
class User : ParseObject(){

    fun getUser(): ParseUser?{
        return getParseUser(KEY_USER)
    }

    fun getFirstName():String?{
        return getString(KEY_FIRSTNAME)
    }

    fun getLastName():String?{
        return getString(KEY_LASTNAME)
    }

    companion object{
        fun getUser(): ParseUser? {
            return getUser()
        }

        const val KEY_USER = "username"
        const val KEY_LASTNAME = "lastname"
        const val KEY_FIRSTNAME = "firstName"
    }

}