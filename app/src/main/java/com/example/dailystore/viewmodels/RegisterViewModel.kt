package com.example.dailystore.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dailystore.data.User
import com.example.dailystore.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
    ) : ViewModel() {

        private val _register = MutableStateFlow<Resource<FirebaseUser>>(Resource.Unspecified())
        val register : Flow<Resource<FirebaseUser>> = _register

        fun crateAccountWithEmailAndPassword(user: User, password: String) {
            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user?.let {
                        _register.value = Resource.Success(it)
                    }
                }
                .addOnFailureListener {
                    _register.value = Resource.Error(it.message.toString())
                }
        }

    }