package dev.qbikkx.core.domain

data class Result<T>(val data: T?, val status: Status) {

    sealed class Status {
        object Loading : Status()
        object Success : Status()
        class Error(val error: Throwable? = null) : Status()
    }
}

data class RefreshableResult<T>(
    val data: T?,
    val status: Result.Status,
    val refreshTrigger: (() -> Unit)?
)