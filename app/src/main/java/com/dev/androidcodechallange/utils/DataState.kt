package com.dev.androidcodechallange.utils

data class DataState<T>(
    var error: StateResource.Event<StateResource.StateError>? = null,
    var loading: StateResource.Loading = StateResource.Loading(false),
    var data: StateResource.Data<T>? = null
) {

    companion object {

        fun <T> error(
            response: StateResource.Response
        ): DataState<T> {
            return DataState(
                error = StateResource.Event(
                    StateResource.StateError(
                        response
                    )
                ),
                loading = StateResource.Loading(false),
                data = null
            )
        }

        fun <T> loading(
            isLoading: Boolean,
            cachedData: T? = null
        ): DataState<T> {
            return DataState(
                error = null,
                loading = StateResource.Loading(isLoading),
                data = StateResource.Data(
                    StateResource.Event.dataEvent(
                        cachedData
                    ), null
                )
            )
        }

        fun <T> data(
            data: T? = null,
            response: StateResource.Response? = null
        ): DataState<T> {
            return DataState(
                error = null,
                loading = StateResource.Loading(false),
                data = StateResource.Data(
                    StateResource.Event.dataEvent(data),
                    StateResource.Event.responseEvent(response)
                )
            )
        }
    }
}
