package com.example.wellness.rustore

import android.content.Context
import android.util.Log
import ru.rustore.sdk.review.RuStoreReviewManagerFactory

class ReviewManager(context: Context) {
    private val manager = RuStoreReviewManagerFactory.create(context)
    private val messageNotifier = ReviewMessageNotifier(context)

    fun review() {
        manager.requestReviewFlow()
            .addOnSuccessListener { reviewInfo ->
                manager.launchReviewFlow(reviewInfo)
                    .addOnSuccessListener { messageNotifier.notifySuccess() }
                    .addOnFailureListener {
                        throwable -> messageNotifier.notifyError(throwable)
                        Log.d("RuStore", throwable.toString())
                    }
            }
            .addOnFailureListener {
                throwable -> messageNotifier.notifyError(throwable)
                Log.d("RuStore", throwable.toString())
            }
    }
}