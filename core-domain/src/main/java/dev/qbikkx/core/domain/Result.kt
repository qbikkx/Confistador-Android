package dev.qbikkx.core.domain

import android.util.Log

data class Result<T>(val data: T?, val status: Status) {

    sealed class Status {
        object Loading : Status()
        object Success : Status()
        class Error(val error: Throwable? = null) : Status() {
            init {
                Log.e("error", "non fatal", error)
            }
        }
    }
}

data class RefreshableResult<T>(
    val data: T?,
    val status: Result.Status,
    val refreshTrigger: (() -> Unit)?
)