package com.example.janagroandroid.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.janagroandroid.R
import com.example.janagroandroid.databinding.FragmentProductDetailBinding
import com.example.janagroandroid.di.AppGraph
import com.example.janagroandroid.ui.AppViewModelFactory

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductDetailViewModel by viewModels {
        AppViewModelFactory(requireActivity().application, AppGraph.repository(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentProductDetailBinding.bind(view)

        val id = arguments?.getLong("id") ?: 0L
        val name = arguments?.getString("name").orEmpty()
        val price = arguments?.getDouble("price") ?: 0.0
        val imageUrl = arguments?.getString("imageUrl").orEmpty()
        val description = arguments?.getString("description").orEmpty()

        binding.tvName.text = name
        binding.tvPrice.text = "Rp $price"
        binding.tvDescription.text = description

        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.ivProduct)

        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart(
                productId = id,
                productName = name,
                price = price,
                imageUrl = imageUrl,
                qty = 1
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}