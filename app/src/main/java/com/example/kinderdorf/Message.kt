package com.example.kinderdorf
import com.parse.*
@ParseClassName("Chat")
class Message:ParseObject() {

    fun getMessageText(): String? {
        return getString(KEY_MESSAGETEXT)
    }

    fun setMessageText(messageText: String) {
        put(KEY_MESSAGETEXT, messageText)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(parseUser: ParseUser) {
        put(KEY_USER, parseUser)
    }

    fun getId(): String? {
        return getString(KEY_ID)
    }

    fun getDate(): String? {
        return getString(KEY_DATE)
    }

    fun setDate(createdDate: String) {
        put(KEY_DATE, createdDate)
    }



    companion object {
        const val KEY_MESSAGETEXT = "messageText"
        const val KEY_DATE = "date"
        const val KEY_USER = "user"
        const val KEY_ID = "objectId"

    }
}