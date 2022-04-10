package com.example.kinderdorf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Check if user logged in
        //If there is, take them to main
        if(ParseUser.getCurrentUser() != null){
            goToMainActivity()
        }
        
        findViewById<Button>(R.id.login_button).setOnClickListener { 
            val userName = findViewById<EditText>(R.id.etUsername).text.toString()
            val passWord = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(userName, passWord)
        }
        findViewById<Button>(R.id.registerBtn).setOnClickListener {
            goToRegister()
        }
    }

    private fun loginUser(userName: String, passWord: String) {
        ParseUser.logInInBackground(userName, passWord, ({ user, e->
            if(user != null){
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error Logging In", Toast.LENGTH_SHORT).show()
            }
        }))
    }

    private fun goToRegister(){
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object{
        const val TAG = "LoginActivity"
    }
}
