package com.example.affirmations.model

data class movie(var id: Int, var poster_path: String, var title: String, var release_date: String, var vote_count: Int, var original_language: String, var overview: String, var vote_average: Float) {
}


//data class movie(var adult: Boolean, var backdrop_path: String, var genre_ids: List<Int>, var id: Int, var original_language: String, var original_title: String, var overview: String, var popularity: Double, var poster_path: String, var release_date: String, var title: String, var video: Boolean, var vote_average: Double, var vote_count: Int) {
