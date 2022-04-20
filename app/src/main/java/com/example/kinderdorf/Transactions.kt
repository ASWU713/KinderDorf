package com.example.kinderdorf

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Transactions")
class Transactions: ParseObject() {

    fun getDateBuy(): Date?{
        return getDate(KEY_DATE_BUY)
    }

    fun setDateBuy(dateBuy: Date){
        put(KEY_DATE_BUY, dateBuy)
    }

    fun getDateRequest(): Date?{
        return getDate(KEY_DATE_REQUEST)
    }

    fun setDateRequest(dateBuy: Date){
        put(KEY_DATE_REQUEST, dateBuy)
    }

    fun getPrice() : Double?{
        return getDouble(KEY_PRICE)
    }

    fun setPrice(price: Double){
        put(KEY_PRICE, price)
    }

    fun getUserBuyer(): ParseUser?{
        return getParseUser(KEY_USER_BUYER)
    }

    fun setUserBuyer(user: ParseUser){
        put(KEY_USER_BUYER, user)
    }

    fun getUserSeller(): ParseUser?{
        return getParseUser(KEY_USER_SELLER)
    }

    fun setUserSeller(user: ParseUser){
        put(KEY_USER_SELLER, user)
    }

    companion object{
        const val KEY_USER_BUYER = "buyerUser"
        const val KEY_USER_SELLER = "sellerUser"
        const val KEY_DATE_BUY = "dateBuy"
        const val KEY_DATE_REQUEST = "dateRequest"
        const val KEY_PRICE = "price"
    }

}
