package com.example.kinderdorf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<Button>(R.id.registerBtn).setOnClickListener {
            val userName = findViewById<EditText>(R.id.etUsername).text.toString()
            val passWord = findViewById<EditText>(R.id.etPassword).text.toString()
            val firstname = findViewById<EditText>(R.id.etFirstName).text.toString()
            val lastname = findViewById<EditText>(R.id.etLastName).text.toString()
            registerUser(userName, passWord, firstname, lastname)

        }
    }
    private fun registerUser(username: String, password: String, firstname: String, lastname: String){
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)
        user.put("firstName", firstname)
        user.put("lastName", lastname)


        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
                Toast.makeText(this, "Succesfully Registered!", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                e.printStackTrace()
                Toast.makeText(this, "Error Registering", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun goToMainActivity(){
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}