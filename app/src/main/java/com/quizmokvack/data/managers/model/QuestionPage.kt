package com.quizmokvack.data.managers.model

data class QuestionPage(
    val questionIndex: Int,
    val questionsCount: Int,
    val category: String,
    val question: String,
    val imageUrl: String?,
    val correctAnswer: String,
    val correctAnswerIndex: Int,
    val choices: List<String>
)