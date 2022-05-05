package com.rick.rcolestore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rick.rcolestore.model.Product

class StoreViewModel: ViewModel() {
    fun calculateOrderTotal() {
        TODO("Not yet implemented")
    }

    var products = MutableLiveData<List<Product>>()



}