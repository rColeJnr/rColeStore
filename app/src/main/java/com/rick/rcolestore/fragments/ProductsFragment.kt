package com.rick.rcolestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rick.rcolestore.MainActivity
import com.rick.rcolestore.databinding.FragmentProductsBinding

class ProductsFragment: Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        return binding.root
    }

}