package com.rick.rcolestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rick.rcolestore.viewmodels.StoreViewModel

class MainActivity : AppCompatActivity() {

    private val storeViewModel: StoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}