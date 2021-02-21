package com.app.assessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class signresult : AppCompatActivity() {
    lateinit var progressbar: View
    lateinit var textView1: TextView
    lateinit var zoname: TextView
    lateinit var zodes: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signresult)
        progressbar = findViewById(R.id.progressbar)
        textView1 = findViewById(R.id.textView1)
        zoname = findViewById(R.id.zoname1)
        zodes = findViewById(R.id.zodes1)
        val date = intent.extras?.getString("date")
        getZodiac(date)
    }

    private fun getZodiac(date: String?){
        val client: OkHttpClient = OkHttpClient().newBuilder()
            .build()
        val request: Request = Request.Builder()
            .url("https://zodiac-sign.p.rapidapi.com/sign?date=$date")
            .method("GET", null)
            .addHeader("x-rapidapi-key", "eb9ee04c89msh4409b314632bd3bp1ef4efjsn6d5926bc23bd")
            .addHeader("x-rapidapi-host", "zodiac-sign.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string()
                Log.d("LogTag", json.toString())
                fetchdata(json)
            }
        })
    }

    private fun fetchdata(json:String?){
        val client2: OkHttpClient = OkHttpClient().newBuilder()
            .build()
        val request2: Request = Request.Builder()
            .url("https://horoscope-api.herokuapp.com/horoscope/today/$json")
            .method("GET", null)
            .build()
        client2.newCall(request2).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("LogTag",e.message.toString())
            }

            override fun onResponse(call: Call, respons: Response) {
                if (respons.isSuccessful){
                    val res = respons.body()?.string()
                    val gson = GsonBuilder().serializeNulls().create()
                    val finaljs=gson.fromJson(res, datahoroscope::class.java)
                    val des=finaljs.horoscope
                    Log.d("LogTag", des)
                    Handler(Looper.getMainLooper())
                            .post {
                                progressbar.visibility = View.GONE
                                zoname.text=json
                                zodes.text=des
                                zoname.visibility = View.VISIBLE
                                zodes.visibility = View.VISIBLE
                            }
                }
            }
        })
    }
}
