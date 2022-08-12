package com.example.youtube_campoverdebrandon

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.VideoView
import androidx.annotation.RequiresApi

class VideoPrincipalActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_principal)
        val Url = intent.getStringExtra("url")
        val videoView = findViewById<WebView>(R.id.wb_videoYT)
        videoView.settings.javaScriptEnabled = true
        videoView.webViewClient = WebViewClient()

        videoView.loadUrl(Url.toString())

    }
}