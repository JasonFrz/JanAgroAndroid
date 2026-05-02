package com.example.janagroandroid.ui.auth

import android.app.DatePickerDialog
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
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.launch
import java.util.Calendar

class RegisterFragment : Fragment() {
    private val vm: AuthViewModel by viewModels { AppViewModelFactory(requireActivity().application, AppGraph.repository(requireContext())) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etName = view.findViewById<EditText>(R.id.etName)
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etEmail = view.findViewById<EditText>(R.id.etEmailReg)
        val etDob = view.findViewById<EditText>(R.id.etDob)
        val etPass = view.findViewById<EditText>(R.id.etPassReg)
        val etConfirmPass = view.findViewById<EditText>(R.id.etConfirmPass)

        val toggleGender = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGender)
        val toggleRole = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleRole)

        // Date Picker Setup
        etDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                val formattedDate = "$day/${month + 1}/$year"
                etDob.setText(formattedDate)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        view.findViewById<TextView>(R.id.tvGoLogin).setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        view.findViewById<View>(R.id.btnRegister).setOnClickListener {
            val name = etName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val dob = etDob.text.toString().trim()
            val pass = etPass.text.toString()
            val confirm = etConfirmPass.text.toString()

            // Basic Validation
            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || pass.isEmpty() || dob.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                Toast.makeText(requireContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get selected values from toggles
            val gender = if (toggleGender.checkedButtonId == R.id.btnFemale) "Female" else "Male"
            val role = if (toggleRole.checkedButtonId == R.id.btnFarmer) "farmer" else "customer"

            lifecycleScope.launch {
                vm.register(name, username, email, pass, dob, gender, role) { isSuccess ->
                    if (isSuccess) {
                        Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}