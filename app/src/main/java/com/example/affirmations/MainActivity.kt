package com.example.affirmations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.affirmations.adapter.ItemAdapter
import com.example.affirmations.model.movie

class MainActivity : AppCompatActivity() {


    var movie_list = ArrayList<ArrayList<movie>>()

    val apiList = listOf("https://api.themoviedb.org/3/movie/popular?api_key=ae56940853447c09afaeddd6844a6595&language=en-US&page=1",
            "https://api.themoviedb.org/3/movie/top_rated?api_key=ae56940853447c09afaeddd6844a6595&language=en-US&page=1",
        "https://api.themoviedb.org/3/movie/upcoming?api_key=ae56940853447c09afaeddd6844a6595&language=en-US&page=1",)


    private val mNames = ArrayList<ArrayList<String>>()
    private val mImageUrls = ArrayList<ArrayList<String>>()

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
                            jsonObj.getString("poster_path"),
                            jsonObj.getString("title")
                        )

                        temp_movie_list.add(movie_)

                    }


                    movie_list.add(temp_movie_list)
                    getImagesForView(i)
                    if (i == 2) {
                        initRecyclerViews()
                    }
                    // Handle successful response
                },
                { err ->
                    // Handle error
                }
            )

            reqQueue.add(request)
            setContentView(R.layout.activity_main)
        }
    }

    private fun getImagesForView(viewNo: Int) {

        val temp_names = ArrayList<String>()
        val temp_urls = ArrayList<String>()

        for (i in 0 until movie_list[viewNo].count()) {

            temp_names.add(movie_list[viewNo][i].title)
            var temp = "https://image.tmdb.org/t/p/w500/"
            temp = temp + movie_list[viewNo][i].poster_path
            temp_urls.add(temp)
            temp = ""
        }

        mNames.add(temp_names)
        mImageUrls.add(temp_urls)
    }

    // add another function here

    private fun initRecyclerViews() {

        val layoutManager0 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recyclerView0: RecyclerView = findViewById(R.id.recycler_view0)
        recyclerView0.layoutManager = layoutManager0
        val adapter0 = ItemAdapter(this, mNames[0], mImageUrls[0])
        recyclerView0.adapter = adapter0

        val layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recyclerView1: RecyclerView = findViewById(R.id.recycler_view1)
        recyclerView1.layoutManager = layoutManager1
        val adapter1 = ItemAdapter(this, mNames[1], mImageUrls[1])
        recyclerView1.adapter = adapter1

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recyclerView2: RecyclerView = findViewById(R.id.recycler_view2)
        recyclerView2.layoutManager = layoutManager2
        val adapter2 = ItemAdapter(this, mNames[2], mImageUrls[2])
        recyclerView2.adapter = adapter2
    }
}
