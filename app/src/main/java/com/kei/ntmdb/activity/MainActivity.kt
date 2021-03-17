package com.kei.ntmdb.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kei.ntmdb.MovieAdapter
import com.kei.ntmdb.R
import com.kei.ntmdb.model.ResponseMovie
import com.kei.ntmdb.service.RetrofitConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    val date = getCurrentDateTime()

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
    fun Date.toString(format : String, locale: Locale = Locale.getDefault()):String{
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
    companion object {
        fun getLaunchService (from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ib_profile.setOnClickListener(this)
        tv_date_main.text = date.toString("dd/MM/yyyy")
        getMovie()

    }

    private fun getMovie() {
        val apiKey = "bc66f598a02d872482d1777f3980a893"
        val lang = "en-US"

        val loading = ProgressDialog.show(this, "Request Data", "Loading...")
        RetrofitConfig.getInstance().getMovieData(apiKey, lang).enqueue(
            object : Callback<ResponseMovie>{
                override fun onResponse(
                    call: Call<ResponseMovie>,
                    response: Response<ResponseMovie>
                ){
                    Log.d("Response", "Success" + response.body()?.results)
                    loading.dismiss()
                    if (response.isSuccessful){
                        Log.e("TAG", "onResponse: ${response.body()?.results?.get(0)?.title}" )
                        Toast.makeText(this@MainActivity, "Data Success !", Toast.LENGTH_SHORT).show()
                        val movieData = response.body()?.results
                        val movieAdapter = MovieAdapter(this@MainActivity, movieData)
                        rv_main.adapter = movieAdapter
                        rv_main.layoutManager = LinearLayoutManager(this@MainActivity)

                        val dataHighlight = response.body()
                        Glide.with(this@MainActivity).load(dataHighlight?.results?.component2()?.posterPath).centerCrop().into(iv_highlight)
                        tv_title_highlight.text = dataHighlight?.results?.component2()?.title
                        tv_date_highlight.text = dataHighlight?.results?.component2()?.releaseDate
                    }else{
                        Toast.makeText(this@MainActivity, "Data Failed !", Toast.LENGTH_SHORT).show()
                    }

                }
                override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                    Log.d("Response","Failed : " + t.localizedMessage)
                    loading.dismiss()
                }
            }
        )
    }
    override fun onClick(p0: View) {
        when(p0.id){
            R.id.ib_profile -> startActivity(Intent(ProfileActivity.getLaunchService(this)))
        }
    }
}