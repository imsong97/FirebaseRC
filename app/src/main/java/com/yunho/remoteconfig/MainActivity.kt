package com.yunho.remoteconfig

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.get
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseDatabase.reference.child("pathString").push().setValue("value")

        FirebaseRemoteConfig.getInstance().run {
            this.setDefaultsAsync(R.xml.remote_config)
            configText.text = this.getString("key")

            // 데이터 업데이트
            this.fetch(60).addOnCompleteListener {
                if (it.isSuccessful) {
                    this.activate() // 값 적용

                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Remote Config")
                        .setMessage("Data :: ${this.getString("key")}")
                        .show()
                }
            }
        }
    }
}