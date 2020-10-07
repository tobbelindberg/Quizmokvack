package com.quizmokvack.base

import android.app.Application
import com.quizmokvack.di.AppComponent
import com.quizmokvack.di.AppModule
import com.quizmokvack.di.DaggerAppComponent


open class QuizmokvackApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()

    }

}