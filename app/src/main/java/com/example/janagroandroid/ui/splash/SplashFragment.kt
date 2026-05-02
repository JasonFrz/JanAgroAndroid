package com.example.janagroandroid.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.janagroandroid.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1400)
            val repo = com.example.janagroandroid.di.AppGraph.repository(requireContext())
            if (repo.isLoggedIn()) findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            else findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}
