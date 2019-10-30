package com.rainbean.taxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLoginState()
    }

    private fun checkLoginState() {

        var tokenValid = false;

        if (!tokenValid){
            showPhoneInputDialog()
        }else{

        }


    }

    private fun showPhoneInputDialog() {

    }
}
