package com.rick.rcolestore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rick.rcolestore.MainActivity
import com.rick.rcolestore.R
import com.rick.rcolestore.fragments.CheckoutFragment
import com.rick.rcolestore.model.Currency
import com.rick.rcolestore.model.Product

class CheckoutAdapter(private val activity: MainActivity, private val fragment: CheckoutFragment) :
    RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    var products = mutableListOf<Product>()
    var currency: Currency? = null

    inner class CheckoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var image = view.findViewById<ImageView>(R.id.productImage)
        internal var name = view.findViewById<TextView>(R.id.productName)
        internal var price = view.findViewById<TextView>(R.id.productPrice)
        internal var remove = view.findViewById<ImageButton>(R.id.removeFromBasketBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        return CheckoutViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.basket_product, parent, false))
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val product = products[position]

        Glide.with(activity)
            .load(product.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .override(400)
            .into(holder.image)

        holder.name.text = product.name
        val price = if (currency?.exchangeRate == null) product.price
        else product.price * currency?.exchangeRate!!

        holder.price.text = activity.resources.getString(
            R.string.product_price,
            currency?.symbol,
            String.format("%.2f", price)
        )
        holder.remove.setOnClickListener {
            fragment.removeProduct(product)
        }
    }

    override fun getItemCount(): Int = products.size
}