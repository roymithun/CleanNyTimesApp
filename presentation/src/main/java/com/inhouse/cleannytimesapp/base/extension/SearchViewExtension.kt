package com.inhouse.cleannytimesapp.base.extension

import android.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun SearchView.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean = false
            override fun onQueryTextChange(s: String?): Boolean {
                trySend(s)
                return false
            }
        }
        setOnQueryTextListener(listener)
        awaitClose { setOnQueryTextListener(null) }
    }.onStart { emit(query) }
}
