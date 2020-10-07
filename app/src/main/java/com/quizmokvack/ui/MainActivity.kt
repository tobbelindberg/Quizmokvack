package com.quizmokvack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quizmokvack.R
import com.quizmokvack.base.QuizmokvackApplication

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as QuizmokvackApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
