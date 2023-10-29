package com.example.questmode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        auth = FirebaseAuth.getInstance()

        val editTextUsername = view.findViewById<EditText>(R.id.usernameEditText)
        val editTextEmail = view.findViewById<EditText>(R.id.emailEditText)
        val editTextPassword = view.findViewById<EditText>(R.id.passwordEditText)
        val editTextConfirmPassword = view.findViewById<EditText>(R.id.confirmPasswordEditText)

        view.findViewById<Button>(R.id.registerButton).setOnClickListener {
            val username = editTextUsername.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (validateInputs(username, email, password, confirmPassword)) {
                registerUser(email, password)
            }
        }

        return view
    }

    private fun validateInputs(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Tous les champs sont requis", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(requireContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        return true
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Inscription réussie", Toast.LENGTH_SHORT).show()
                // Navigation vers le fragment de vérification d'email
            } else {
                val exception = task.exception
                val message = when (exception) {
                    is FirebaseAuthWeakPasswordException -> "Le mot de passe est trop faible."
                    is FirebaseAuthInvalidCredentialsException -> "L'adresse e-mail est mal formée."
                    is FirebaseAuthUserCollisionException -> "Un compte existe déjà avec cette adresse e-mail."
                    else -> "Échec de l'inscription."
                }
                Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                Log.e("SignUpFragment", "Inscription échouée", exception)
            }

        }
    }
}
