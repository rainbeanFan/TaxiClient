package com.rainbean.taxi

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rainbean.mytaxi.R
import com.rainbean.taxi.main.view.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val anim = resources.getDrawable(R.drawable.anim) as AnimatedVectorDrawable
            val logo = findViewById<ImageView>(R.id.logo)
            logo.setImageDrawable(anim)
            anim.start()
        }


         /**
         * 延时 3 秒然后跳转到 main 页面
         */
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java));
            finish() }, 3000)

    }




}