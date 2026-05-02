package com.example.janagroandroid.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.janagroandroid.R
import com.example.janagroandroid.ui.AppViewModelFactory
import com.example.janagroandroid.di.AppGraph
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val vm: AuthViewModel by viewModels { AppViewModelFactory(requireActivity().application, AppGraph.repository(requireContext())) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPass = view.findViewById<EditText>(R.id.etPassword)

        view.findViewById<TextView>(R.id.tvGoRegister).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        view.findViewById<View>(R.id.btnLogin).setOnClickListener {
            val emailOrUsername = etEmail.text.toString().trim()
            val password = etPass.text.toString()

            if (emailOrUsername.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Note: Pastikan repository backend-mu mendukung validasi via username ATAU email.
            lifecycleScope.launch {
                vm.login(emailOrUsername, password) { isSuccess ->
                    if (isSuccess) {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}