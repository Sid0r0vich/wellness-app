package com.example.wellness.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class UserDatabase {
    private var Instance: FirebaseFirestore = Firebase.firestore

    fun getDatabase(): FirebaseFirestore = Instance
}