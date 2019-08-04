package com.junmo.weather_application

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_account_setting.*

class AccountSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)
        setupListener()
    }

    private fun setupListener() {
        account_setting_back.setOnClickListener {
            onBackPressed()
        }

        account_setting_logout.setOnClickListener {
            signOutAccount()
        }

        account_setting_delete.setOnClickListener {
            deleteAccount()
        }
    }

    private fun signOutAccount() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                moveToMainActivity()
                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteAccount() {
        account_setting_logout.setOnClickListener {
            AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener {
                    moveToMainActivity()
                    Toast.makeText(this, "계정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
