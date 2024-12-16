package com.example.wellness.ui.viewModels

import androidx.lifecycle.viewModelScope
import com.example.wellness.auth.Auth
import com.example.wellness.data.Document
import com.example.wellness.data.DocumentRepository
import com.example.wellness.data.InitDocument
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.ui.viewModels.UserViewModel.Companion.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DocumentsViewModel @Inject constructor(
    auth: Auth,
    userInfoRepository: UserInfoRepository,
    private val documentRepository: DocumentRepository
) : UserViewModel(auth, userInfoRepository) {
    fun getDocumentStateFlows(): List<StateFlow<Document>> =
        documentRepository.getIndicatorStateFlows(viewModelScope)
}

fun DocumentRepository.getIndicatorStateFlows(scope: CoroutineScope): List<StateFlow<Document>> =
    this.getStream().map { flow ->
        flow.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = InitDocument.document
        )
    }