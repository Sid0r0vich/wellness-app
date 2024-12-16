package com.example.wellness.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface DocumentRepository {
    fun getDocumentStream(documentNum: Int): Flow<Document?>
    fun getStream(): List<Flow<Document>>
    fun insertDocument(document: Document)
    fun deleteDocument(document: Document)
    fun updateDocument(document: Document)
}

class DocumentMockRepository @Inject constructor(): DocumentRepository {
    override fun getDocumentStream(documentNum: Int): Flow<Document?> =
        flow {
            emit(DocumentStorage.getAll()[documentNum])
        }

    override fun getStream(): List<Flow<Document>> =
        DocumentStorage.getAll().map { document ->
            flow { emit(document) }
        }

    override fun insertDocument(document: Document) { TODO() }
    override fun deleteDocument(document: Document) { TODO() }
    override fun updateDocument(document: Document) { TODO() }

}