package com.kei.ntmdb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kei.ntmdb.R
import com.kei.ntmdb.model.ResultsItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MOVIE = "extra_movie"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()
        ib_back_detail.setOnClickListener{ startActivity(Intent(MainActivity.getLaunchService(this)))}
        val movie = intent.getParcelableExtra<ResultsItem>("EXTRA_MOVIE") as ResultsItem
        Glide.with(this).load(movie.posterPath).into(iv_poster_detail)
        Glide.with(this).load(movie.backdropPath).into(iv_back_path)
        tv_synopsis_detail.text = movie.overview
        tv_title_detail.text = movie.title
        tv_language_type_detail.text = movie.originalLanguage
        tv_release_date_detail.text = movie.releaseDate
        tv_rating_imdb_detail.text = movie.voteCount.toString()
    }
}