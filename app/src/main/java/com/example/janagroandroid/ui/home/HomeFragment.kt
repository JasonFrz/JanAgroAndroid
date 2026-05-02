package com.example.janagroandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.janagroandroid.R
import com.example.janagroandroid.databinding.FragmentHomeBinding
import com.example.janagroandroid.di.AppGraph
import com.example.janagroandroid.ui.AppViewModelFactory
import com.example.janagroandroid.ui.adapters.ProductAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        AppViewModelFactory(requireActivity().application, AppGraph.repository(requireContext()))
    }

    private lateinit var adapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)

        adapter = ProductAdapter(emptyList()) { product ->
            val bundle = bundleOf(
                "id" to product.id,
                "name" to product.name,
                "price" to product.price,
                "stock" to product.stock,
                "imageUrl" to product.imageUrl,
                "description" to product.description,
                "category" to product.category
            )
            findNavController().navigate(R.id.productDetailFragment, bundle)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
        })

        viewModel.refreshRemote()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}