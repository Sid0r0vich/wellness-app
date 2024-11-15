package com.example.wellness.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class UserDatabase {
    private val instance: FirebaseFirestore = Firebase.firestore

    fun getDatabase(): FirebaseFirestore = instance
}
