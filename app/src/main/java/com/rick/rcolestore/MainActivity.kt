package com.rick.rcolestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rick.rcolestore.adapters.ProductAdapter
import com.rick.rcolestore.model.Product
import com.rick.rcolestore.viewmodels.StoreViewModel

class MainActivity : AppCompatActivity() {

    private val storeViewModel: StoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storeViewModel.products.value = items
    }
}


val broccoli = Product(com.google.android.material.R.drawable.notify_panel_notification_icon_bg, "Brocolli", 1.40)
val carrots = Product(com.google.android.material.R.drawable.notify_panel_notification_icon_bg, "Carrots", 1.40)
val strawberries = Product(com.google.android.material.R.drawable.notify_panel_notification_icon_bg, "Strawberries", 1.40)
val items = listOf(broccoli, carrots, strawberries)
