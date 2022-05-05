package com.rick.rcolestore

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import com.rick.rcolestore.model.Currency
import com.rick.rcolestore.model.Product
import com.rick.rcolestore.viewmodels.StoreViewModel
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val storeViewModel: StoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        getCurrencyData()
        storeViewModel.products.value = items
    }

    private fun getCurrencyData(): JSONObject? {
        val client = AsyncHttpClient()
        //TODO: replace with APIKEY
        client.get(
            "https://v6.exchangerate-api.com/v6/API-KEY/latest/$defCurrency",
            object : TextHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?
                ) {
                    if (responseString != null) {
                        exchangeData = JSONObject(responseString)
                        val currencyPreference =
                            sharedPreferences.getString("currency", defCurrency) ?: defCurrency
                        setCurrency(currencyPreference)
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseString: String?,
                    throwable: Throwable?
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        resources.getString(R.string.exchange_data_unavailable),
                        Toast.LENGTH_LONG
                    ).show()
                    setCurrency(defCurrency)
                }
            })
        return null
    }

    private fun setCurrency(isoCode: String) {
        val exchangeRate = exchangeData?.getJSONObject("conversion_rates")?.getDouble(isoCode)
        //TODO: define the base currency
        var currency = Currency(defCurrency, "$", null)
        if (exchangeRate != null) {
            when (isoCode) {
                "USD" -> currency = Currency(isoCode, "$", exchangeRate)
                "Ruble" -> currency = Currency(isoCode, "$", exchangeRate)
            }
        }
        sharedPreferences.edit().apply {
            putString("currency", isoCode)
            apply()
        }
        selectedCurrency = currency
        storeViewModel.currency.value = currency
        storeViewModel.calculateOrderTotal()
    }
}


val broccoli = Product(
    com.google.android.material.R.drawable.notify_panel_notification_icon_bg,
    "Brocolli",
    1.40
)
val carrots = Product(
    com.google.android.material.R.drawable.notify_panel_notification_icon_bg,
    "Carrots",
    1.40
)
val strawberries = Product(
    com.google.android.material.R.drawable.notify_panel_notification_icon_bg,
    "Strawberries",
    1.40
)
val items = listOf(broccoli, carrots, strawberries)

private val defCurrency = "GBP"
private var exchangeData: JSONObject? = null
private var selectedCurrency: Currency? = null
private lateinit var sharedPreferences: SharedPreferences
