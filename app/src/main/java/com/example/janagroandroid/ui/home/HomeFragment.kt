package com.example.janagroandroid.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    // Inisialisasi ViewModel
    private val viewModel: HomeViewModel by viewModels {
        AppViewModelFactory(requireActivity().application, AppGraph.repository(requireContext()))
    }

    private lateinit var adapter: ProductAdapter

    // Variabel penanda apakah user sudah login atau masih Guest
    private var isUserLoggedIn = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        observeData()
        setupClickListeners()

        // Panggil data dari API/Server saat halaman dibuka
        viewModel.refreshRemote()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(emptyList()) { product ->
            // CEK STATUS LOGIN SEBELUM MEMBUKA DETAIL PRODUK
            if (isUserLoggedIn) {
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
            } else {
                showLoginPrompt()
            }
        }

        // Atur RecyclerView menjadi Horizontal (Kiri ke Kanan)
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProducts.adapter = adapter
    }

    private fun observeData() {
        // Amati perubahan list produk
        viewModel.products.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // Amati status user aktif
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            isUserLoggedIn = user != null

            if (isUserLoggedIn) {
                // Tampilan jika SUDAH LOGIN
                binding.tvGreetingName.text = user?.name ?: "Hello!"
                binding.tvLocation.text = "Suroboyo mas, ID"
                binding.ivProfile.setImageResource(R.drawable.ic_user_placeholder)

                // (Opsional) Jika kamu mau tombol 'Tambah Produk' hanya untuk Petani:
                // binding.fabAddProduct.visibility = if (user?.role == "farmer") View.VISIBLE else View.GONE


            } else {
                // Tampilan jika BELUM LOGIN (Guest)
                binding.tvGreetingName.text = "Guest User"
                binding.tvLocation.text = "Log in to explore more"
                binding.ivProfile.setImageResource(R.drawable.ic_user_placeholder)

            }
        }
    }

    private fun setupClickListeners() {
        // Klik pada gambar profil (Atau area teks sapaan)
        val profileClickListener = View.OnClickListener {
            if (isUserLoggedIn) {
                findNavController().navigate(R.id.profileFragment)
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        binding.ivProfile.setOnClickListener(profileClickListener)
        binding.tvGreetingName.setOnClickListener(profileClickListener)
        binding.tvLocation.setOnClickListener(profileClickListener)


    }

    // Fungsi bantuan untuk menampilkan peringatan dan melempar ke Login
    private fun showLoginPrompt() {
        Toast.makeText(requireContext(), "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}