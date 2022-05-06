package com.rick.rcolestore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rick.rcolestore.MainActivity
import com.rick.rcolestore.databinding.FragmentCheckoutBinding
import com.rick.rcolestore.viewmodels.StoreViewModel

class CheckoutFragment:Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StoreViewModel by activityViewModels()
    private lateinit var mActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        mActivity = activity as MainActivity


        return binding.root
    }
}