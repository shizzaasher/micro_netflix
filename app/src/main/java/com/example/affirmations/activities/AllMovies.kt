package com.example.affirmations.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.affirmations.R
import com.example.affirmations.adapter.ItemAdapter
import com.example.affirmations.model.movie

class AllMovies : AppCompatActivity() {

    var movie_list = ArrayList<ArrayList<movie>>()

    val apiList = listOf("https://api.themoviedb.org/3/movie/popular?api_key=ae56940853447c09afaeddd6844a6595&language=en-US&page=1",
        "https://api.themoviedb.org/3/movie/top_rated?api_key=ae56940853447c09afaeddd6844a6595&language=en-US&page=1",
        "https://api.themoviedb.org/3/movie/upcoming?api_key=ae56940853447c09afaeddd6844a6595&language=en-US&page=1",)


    private val mNames = ArrayList<ArrayList<String>>()
    private val mImageUrls = ArrayList<ArrayList<String>>()
    private val mDates = ArrayList<ArrayList<String>>()
    private val mVotes = ArrayList<ArrayList<Int>>()
    private val mLanguages = ArrayList<ArrayList<String>>()
    private val mOverviews = ArrayList<ArrayList<String>>()
    private val mRatings = ArrayList<ArrayList<Float>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val reqQueue: RequestQueue = Volley.newRequestQueue(this)
        for (i in 0 until apiList.count()) {

            var temp_movie_list = ArrayList<movie>()

            var request = JsonObjectRequest(
                Request.Method.GET, apiList[i], null,
                { res ->
                    val jsonArray = res.getJSONArray("results")

                    for (j in 0 until jsonArray.length()) {
                        val jsonObj = jsonArray.getJSONObject(j)
                        val movie_ = movie(
                            jsonObj.getInt("id"),
                            jsonObj.getString("poster_path"),
                            jsonObj.getString("title"),
                            jsonObj.getString("release_date"),
                            jsonObj.getInt("vote_count"),
                            jsonObj.getString("original_language"),
                            jsonObj.getString("overview"),
                            jsonObj.getDouble("vote_count").toFloat()
                        )

                        temp_movie_list.add(movie_)

                    }


                    movie_list.add(temp_movie_list)
                    getImagesForView(i)
                    if (i == 2) {
                        initRecyclerViews()
                        setFeaturedMovie()
                    }
                    // Handle successful response
                },
                { err ->
                    // Handle error
                }
            )

            reqQueue.add(request)
            setContentView(R.layout.movies_page)
        }
    }

    private fun getImagesForView(viewNo: Int) {

        val temp_names = ArrayList<String>()
        val temp_urls = ArrayList<String>()
        val temp_dates = ArrayList<String>()
        val temp_votes = ArrayList<Int>()
        val temp_languages = ArrayList<String>()
        val temp_overviews = ArrayList<String>()
        val temp_ratings = ArrayList<Float>()

        for (i in 0 until movie_list[viewNo].count()) {

            temp_names.add(movie_list[viewNo][i].title)
            var temp = "https://image.tmdb.org/t/p/w500/"
            temp = temp + movie_list[viewNo][i].poster_path
            temp_urls.add(temp)
            temp = ""
            temp_dates.add(movie_list[viewNo][i].release_date)
            temp_votes.add(movie_list[viewNo][i].vote_count)
            temp_languages.add(movie_list[viewNo][i].original_language)
            temp_overviews.add(movie_list[viewNo][i].overview)
            temp_ratings.add(movie_list[viewNo][i].vote_average)
        }

        mNames.add(temp_names)
        mImageUrls.add(temp_urls)
        mDates.add(temp_dates)
        mVotes.add(temp_votes)
        mLanguages.add(temp_languages)
        mOverviews.add(temp_overviews)
        mRatings.add(temp_ratings)
    }

    // add another function here

    private fun initRecyclerViews() {

        val layoutManager0 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recyclerView0: RecyclerView = findViewById(R.id.recycler_view0)
        recyclerView0.layoutManager = layoutManager0
        val adapter0 = ItemAdapter(this, mNames[0], mImageUrls[0], mDates[0], mVotes[0], mLanguages[0], mOverviews[0], mRatings[0])
        recyclerView0.adapter = adapter0

        val layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recyclerView1: RecyclerView = findViewById(R.id.recycler_view1)
        recyclerView1.layoutManager = layoutManager1
        val adapter1 = ItemAdapter(this, mNames[1], mImageUrls[1], mDates[1], mVotes[1], mLanguages[1], mOverviews[1], mRatings[1])
        recyclerView1.adapter = adapter1

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recyclerView2: RecyclerView = findViewById(R.id.recycler_view2)
        recyclerView2.layoutManager = layoutManager2
        val adapter2 = ItemAdapter(this, mNames[2], mImageUrls[2], mDates[2], mVotes[2], mLanguages[2], mOverviews[2], mRatings[2])
        recyclerView2.adapter = adapter2
    }

    private fun setFeaturedMovie(){
        val featured_image_view = findViewById<ImageView>(R.id.featuredImageView)
        Glide.with(this)
            .asBitmap()
            .load(mImageUrls[1][4])
            .into(featured_image_view)
        val featured_title_view = findViewById<TextView>(R.id.featuredTitleTextView)
        featured_title_view.text = mNames[1][4]
        val featured_description_view = findViewById<TextView>(R.id.featuredDescriptionTextView)
        featured_description_view.text = mOverviews[1][4]
        val featured_votes_view = findViewById<TextView>(R.id.featuredVotesView)
        featured_votes_view.text = mVotes[1][4].toString() + " votes"
    }
}
