package com.rick.rcolestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.rick.rcolestore.MainActivity
import com.rick.rcolestore.R
import com.rick.rcolestore.adapters.CheckoutAdapter
import com.rick.rcolestore.databinding.CheckoutFragmentBinding
import com.rick.rcolestore.model.Currency
import com.rick.rcolestore.model.Product
import com.rick.rcolestore.viewmodels.StoreViewModel

class CheckoutFragment:Fragment() {

    private var _binding: CheckoutFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StoreViewModel by activityViewModels()
    private lateinit var mActivity: MainActivity
    private lateinit var checkoutAdapter: CheckoutAdapter

    private var amount: Double? = null
    private var currency: Currency? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CheckoutFragmentBinding.inflate(inflater, container, false)
        mActivity = activity as MainActivity

        checkoutAdapter = CheckoutAdapter(mActivity, this)
        binding.cartRV.layoutManager = LinearLayoutManager(mActivity)
        binding.cartRV.itemAnimator = DefaultItemAnimator()
        binding.cartRV.adapter = checkoutAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.products.observe(viewLifecycleOwner){
            it?.let {
                val basket = it.filter { p ->
                    p.inCart
                }
                if (basket.isEmpty()) binding.emptyCart.visibility = View.VISIBLE
                else binding.emptyCart.visibility = View.GONE

                val adapterBasket = checkoutAdapter.products
                when {
                    basket.size > adapterBasket.size -> {
                        val newProducts = basket - adapterBasket
                        for (p in newProducts) {
                            checkoutAdapter.products.add(p)
                            checkoutAdapter.notifyItemInserted(checkoutAdapter.products.size - 1)
                        }
                    }
                    basket.size < adapterBasket.size -> {
                        val removedProducts = adapterBasket - basket
                        for (p in removedProducts) {
                            val index = checkoutAdapter.products.indexOf(p)
                            checkoutAdapter.products.removeAt(index)
                            checkoutAdapter.notifyItemRemoved(index)
                        }
                    }
                    adapterBasket.isEmpty() && basket.isNotEmpty() -> {
                        checkoutAdapter.products = basket.toMutableList()
                        checkoutAdapter.notifyItemRangeInserted(0, basket.size)
                    }
                    basket.isEmpty() -> {
                        checkoutAdapter.notifyItemRangeRemoved(0, basket.size)
                        checkoutAdapter.products = mutableListOf()
                    }
                }
                viewModel.orderTotal.observe(viewLifecycleOwner) {
                    amount = it
                    updateOrderTotal()
                }

                viewModel.currency.observe(viewLifecycleOwner) {
                    it?.let {
                        currency = it
                        if (checkoutAdapter.currency == null || it.symbol != checkoutAdapter.currency?.symbol) {
                            checkoutAdapter.currency = it
                            checkoutAdapter.notifyItemRangeChanged(0, checkoutAdapter.itemCount)
                        }
                        updateOrderTotal()
                    }
                }
            }
        }
    }

    private fun updateOrderTotal() {
        if (currency == null || amount == null) return
        val total = currency!!.symbol + String.format("%.2f", amount)
        binding.orderTotal.text = resources.getString(R.string.order_total, total)
    }

    fun removeProduct(product: Product) {
        product.inCart = !product.inCart
        val products = viewModel.products.value?.toMutableList() ?: mutableListOf()
        val position = products.indexOf(product)
        if (position != -1) {
            products[position] = product
            viewModel.products.value = products
            viewModel.calculateOrderTotal()
        }
    }
}