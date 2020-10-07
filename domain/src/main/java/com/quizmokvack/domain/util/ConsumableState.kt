package com.quizmokvack.domain.util


class ConsumableState<T>(private val value: Optional<T>) {

    private var isConsumable = true

    private constructor() : this(Optional.empty())

    fun consume(callback: (T) -> Unit) {
        value.getOrDefault(null)
            ?.takeIf { isConsumable }?.also {
                callback(it)
                isConsumable = false
            }
    }

    companion object {

        fun <T> of(value: T): ConsumableState<T> {
            return ConsumableState(Optional(value))
        }

        fun <T> of(): ConsumableState<T> {
            return ConsumableState()
        }

    }

}