package com.rick.rcolestore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rick.rcolestore.model.Currency
import com.rick.rcolestore.model.Product
import java.math.BigDecimal
import java.math.RoundingMode

class StoreViewModel: ViewModel() {

    var products = MutableLiveData<List<Product>>()

    var currency = MutableLiveData<Currency>()

    var orderTotal = MutableLiveData(0.00)

    fun calculateOrderTotal() {
        val basket = products.value?.filter {
            it.inCart
        } ?: listOf()

        var total = 0.0
        for (p in basket) total += p.price

        if (currency.value != null) total *= currency.value?.exchangeRate ?: 1.00
        orderTotal.value = BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}