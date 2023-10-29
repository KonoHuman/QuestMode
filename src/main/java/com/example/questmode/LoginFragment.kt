package com.example.questmode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        auth = FirebaseAuth.getInstance()

        val editTextEmail = view.findViewById<EditText>(R.id.emailEditText)
        val editTextPassword = view.findViewById<EditText>(R.id.passwordEditText)
        val forgotPasswordTextView = view.findViewById<TextView>(R.id.forgotPasswordTextView)

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (validateInputs(email, password)) {
                loginUser(email, password)
            }
        }

        forgotPasswordTextView.setOnClickListener { view ->
            val action = LoginFragmentDirections.actionLoginFragmentToPasswordManagementFragment()
            findNavController(view).navigate(action)
        }


        return view
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Les champs e-mail et mot de passe sont requis.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Connexion réussie", Toast.LENGTH_SHORT).show()
            } else {
                val exception = task.exception
                val message = exception?.message ?: "Échec de la connexion"
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                Log.e("LoginFragment", "Échec de la connexion", exception)
            }
        }
    }
}
