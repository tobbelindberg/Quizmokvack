package com.quizmokvack.domain.model

data class Question(
    val category: String,
    val question: String,
    val imageUrl: String?,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)