package com.rick.rcolestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rick.rcolestore.MainActivity
import com.rick.rcolestore.R
import com.rick.rcolestore.adapters.ProductAdapter
import com.rick.rcolestore.databinding.FragmentProductsBinding
import com.rick.rcolestore.model.Product
import com.rick.rcolestore.viewmodels.StoreViewModel

class ProductsFragment: Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productsAdapter: ProductAdapter
    private lateinit var mainActivity: MainActivity

    private val viewModel: StoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        productsAdapter = ProductAdapter(mainActivity, this)
        binding.productsRV.layoutManager = LinearLayoutManager(activity)
        binding.productsRV.itemAnimator = DefaultItemAnimator()
        binding.productsRV.adapter = productsAdapter

        productsAdapter.products = viewModel.products.value?.toMutableList() ?: mutableListOf()
        productsAdapter.notifyItemRangeInserted(0, productsAdapter.products.size)

        return binding.root
    }

    fun updateCart(position: Int) {
        val products = productsAdapter.products.toMutableList()
        products[position].inCart = !products[position].inCart
        productsAdapter.products = products

        productsAdapter.notifyItemChanged(position)
        viewModel.products.value = products
        viewModel.calculateOrderTotal()
    }

}
