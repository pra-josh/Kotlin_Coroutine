package com.pra.coroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pra.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val IMAGE_URL =
        "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        coroutineScope.launch {
            val originalDeffered = coroutineScope.async(Dispatchers.IO) { getOriginalBitmap() }
            val originalbitmap = originalDeffered.await()

            val filterredBitmap = coroutineScope.async(Dispatchers.Default) { getFilteredBitmap(originalbitmap) }

            val filterredresult = filterredBitmap.await()
            loadImage(filterredresult)
        }
    }

    fun getOriginalBitmap() = URL(IMAGE_URL).openStream().use {
        BitmapFactory.decodeStream(it)
    }

    fun getFilteredBitmap(bmp: Bitmap) = Filter.apply(bmp)

    private fun loadImage(bmp: Bitmap) {
        mBinding.progressBar.visibility = View.GONE
        mBinding.imageView.visibility = View.VISIBLE
        mBinding.imageView.setImageBitmap(bmp)
    }


}