package com.rainbean.taxi

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val anim = resources
                .getDrawable(R.drawable.anim) as AnimatedVectorDrawable
            val logo = findViewById<ImageView>(R.id.logo)
            logo.setImageDrawable(anim)
            anim.start()
        }
    }

    

}