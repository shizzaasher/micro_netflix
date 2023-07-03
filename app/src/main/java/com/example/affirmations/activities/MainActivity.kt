package com.example.affirmations.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.affirmations.R
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var enterButton: Button
    private lateinit var errorMessageTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.editEmailAddress)
        passwordEditText = findViewById(R.id.editPassword)
        enterButton = findViewById(R.id.button)
        errorMessageTextView = findViewById(R.id.errorMessageTextView)

        // calling on click listener for the enter button
        val button_view = findViewById<Button>(com.example.affirmations.R.id.button)
        button_view.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Both email and password fields are filled

            val url = "https://reqres.in/api/register"
            val queue = Volley.newRequestQueue(this)

            val request = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    // Registration successful, process the response here
                    try {
                        val jsonObject = JSONObject(response)
                        val id = jsonObject.getInt("id")
                        val token = jsonObject.getString("token")

                        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                        //// Proceed to the next activity
                        //startActivity(Intent(this, AllMovies::class.java))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Error parsing response!", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    // Registration failed, handle the error here
                    Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show()
                    // You can display an error message or handle the error in another way
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["email"] = email
                    params["password"] = password
                    return params
                }

            }

            // Proceed to the next activity
            startActivity(Intent(this, AllMovies::class.java))
            
            queue.add(request)
        } else {
            // Display error message
            val errorMessage = "Please fill in both email and password"
            errorMessageTextView.text = errorMessage
            errorMessageTextView.visibility = View.VISIBLE
        }
    }


    // Implement any additional functionality or event handling here

}


