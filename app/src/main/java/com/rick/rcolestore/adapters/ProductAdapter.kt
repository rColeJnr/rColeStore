package com.rick.rcolestore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rick.rcolestore.MainActivity
import com.rick.rcolestore.R
import com.rick.rcolestore.fragments.ProductsFragment
import com.rick.rcolestore.model.Product

class ProductAdapter(private val activity: MainActivity, private val fragment: ProductsFragment) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var products = mutableListOf<Product>()

    inner class ProductViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        internal val productImage = view.findViewById<ImageView>(R.id.productImage)
        internal val productName = view.findViewById<TextView>(R.id.productName)
        internal val productPrice = view.findViewById<TextView>(R.id.productPrice)
        internal val productAddToBusketBtn = view.findViewById<Button>(R.id.addToBasketButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_entry, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = products[position]

        Glide.with(activity)
            .load(current.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .override(600)
            .into(holder.productImage)

        holder.productName.text = current.name

        if (current.inCart) {
            with(holder){
                this.productAddToBusketBtn.text = activity.resources.getString(R.string.remove_from_basket)
                this.productAddToBusketBtn.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_red_dark))
            }
        } else {
            with(holder){
                this.productAddToBusketBtn.text = activity.resources.getString(R.string.add_to_basket)
                this.productAddToBusketBtn.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_green_dark))
            }
        }

        holder.productAddToBusketBtn.setOnClickListener {
            fragment.updateCart(position)
        }
    }

    override fun getItemCount(): Int = products.size
}