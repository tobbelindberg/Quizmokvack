package com.quizmokvack.data.network.model

import com.quizmokvack.domain.model.Question

data class QuestionDTO(
    val category: String,
    val question: String,
    val image_url: String?,
    val correct_answer: String,
    val incorrect_answers: List<String>
) {

    internal fun toDomain() = Question(category, question, image_url, correct_answer, incorrect_answers)

}