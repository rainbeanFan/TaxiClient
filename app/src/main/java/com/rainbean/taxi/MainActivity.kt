package com.rainbean.taxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rainbean.taxi.common.util.ToastUtil

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

    fun showLoginSuc() {
        ToastUtil.show(this, getString(R.string.login_suc));

    }

    private fun showPhoneInputDialog() {

    }
}
