package com.inhouse.cleannytimesapp.base.viewmodel

import androidx.lifecycle.ViewModel
import com.inhouse.cleannytimesapp.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import kotlin.properties.Delegates

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {

    private val viewStateMutableStateFlow = MutableStateFlow(initialState)
    val viewStateFlow = viewStateMutableStateFlow

    private var stateTimeTravelDebugger: StateTimeTravelDebugger? = null

    init {
        if (BuildConfig.DEBUG) {
            stateTimeTravelDebugger = StateTimeTravelDebugger(this::class.java.simpleName)
        }
    }

    // Delegate will handle state event deduplication
    // (multiple states of the same type holding the same data will not be dispatched multiple times to LiveData stream)
    var state by Delegates.observable(initialState) { _, old, new ->
        viewStateMutableStateFlow.value = new

        if (new != old) {
            stateTimeTravelDebugger?.apply {
                addStateTransition(old, new)
                logLast()
            }
        }
    }

    fun sendAction(viewAction: ViewAction) {
        stateTimeTravelDebugger?.addAction(viewAction)
        Timber.d("sendAction state=$state")
        state = onReduceState(viewAction)
    }

    fun loadData() {
        onLoadData()
    }

    protected open fun onLoadData() {}

    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}
