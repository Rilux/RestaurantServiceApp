package com.example.restaurantserviceapp.forgot_password

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    fun resetPassword(email: String, callback: (Boolean, String?) -> Unit) {
        if (email.isEmpty()) {
            callback(false, "Email cannot be empty")
            return
        }
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message ?: "Failed to send reset email")
                }
            }
    }
}