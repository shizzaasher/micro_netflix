package com.example.affirmations.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.affirmations.R


class MovieScreen : AppCompatActivity() {

    private lateinit var title: String
    private lateinit var imageURL: String
    private lateinit var releaseDate: String
    private var votes: Int = 0
    private lateinit var language: String
    private lateinit var description: String
    private var rating_: Float = 0.0f

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(com.example.affirmations.R.layout.redirected_to_movie)

        // Assign the values to the variables

        val intent = getIntent()
        title = intent.getStringExtra("title").toString()
        imageURL = intent.getStringExtra("url").toString()
        releaseDate = intent.getStringExtra("release_date").toString()
        votes = intent.getIntExtra("vote_count", 0)
        language = intent.getStringExtra("original_language").toString()
        description = intent.getStringExtra("overview").toString()
        rating_ = intent.getFloatExtra("vote_average", 0.0f)

        rating_ /= 2.0f

        // Call a method to update the XML layout with the data
        updateUI()

        val ratingBar = findViewById<RatingBar>(com.example.affirmations.R.id.Rating)

        ratingBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating_, fromUser ->
                Toast.makeText(
                    this,
                    rating_.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun updateUI() {

        // Update the XML layout with the data
        val poster_view = findViewById<ImageView>(com.example.affirmations.R.id.posterView)
        val title_view = findViewById<TextView>(com.example.affirmations.R.id.titleView)
        val date_view = findViewById<TextView>(com.example.affirmations.R.id.dateView)
        val language_view = findViewById<TextView>(com.example.affirmations.R.id.languageView)
        val votes_view = findViewById<TextView>(com.example.affirmations.R.id.votesView)
        val description_view = findViewById<TextView>(com.example.affirmations.R.id.descriptionView)
        val ratingBar = findViewById<RatingBar>(com.example.affirmations.R.id.Rating)

        Glide.with(this)
            .asBitmap()
            .load(imageURL)
            .into(poster_view)

        title_view.text = title
        date_view.text = releaseDate
        if (language.equals("en", true)) {
            language_view.text = "English"
        }
        votes_view.text = votes.toString() + " votes"
        description_view.text = description
        ratingBar.rating = rating_
    }
}
