package com.example.wellness.rustore

import android.content.Context
import android.widget.Toast
import com.example.wellness.R
import ru.rustore.sdk.core.exception.RuStoreApplicationBannedException
import ru.rustore.sdk.core.exception.RuStoreNotInstalledException
import ru.rustore.sdk.core.exception.RuStoreOutdatedException
import ru.rustore.sdk.core.exception.RuStoreUserBannedException
import ru.rustore.sdk.core.exception.RuStoreUserUnauthorizedException
import ru.rustore.sdk.review.errors.RuStoreInvalidReviewInfo
import ru.rustore.sdk.review.errors.RuStoreRequestLimitReached
import ru.rustore.sdk.review.errors.RuStoreReviewExists

class ReviewMessageNotifier(private val context: Context) {
    fun notifySuccess() {
        showToast(R.string.success)
    }

    fun notifyError(errorType: Throwable) {
        val message = when (errorType) {
            is RuStoreNotInstalledException -> R.string.rustore_not_installed
            is RuStoreOutdatedException -> R.string.rustore_outdated
            is RuStoreUserUnauthorizedException -> R.string.rustore_unauthorized
            is RuStoreUserBannedException -> R.string.rustore_user_banned
            is RuStoreApplicationBannedException -> R.string.rustore_app_banned
            is RuStoreRequestLimitReached -> R.string.rustore_request_limit
            is RuStoreReviewExists -> R.string.rustore_review_exists
            is RuStoreInvalidReviewInfo -> R.string.rustore_review_info
            else -> R.string.unknown_exception
        }
        showToast(message)
    }

    private fun showToast(messageId: Int) =
        Toast.makeText(
            context,
            context.getString(messageId),
            Toast.LENGTH_SHORT
        ).show()
}