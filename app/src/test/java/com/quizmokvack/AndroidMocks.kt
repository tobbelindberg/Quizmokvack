package com.quizmokvack

import android.content.Context
import android.content.res.Resources
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class AndroidMocks {


    @Mock
    lateinit var mockApplicationContext: Context

    @Mock
    private lateinit var mockContextResources: Resources

    init {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockApplicationContext.resources).thenReturn(mockContextResources)

        Mockito.`when`(
            mockContextResources.getQuantityString(
                Matchers.anyInt(),
                Matchers.anyInt(),
                Matchers.anyInt()
            )
        ).thenReturn("Some string with a quantity in it")
        Mockito.`when`(
            mockContextResources.getString(Matchers.anyInt())
        ).thenReturn("Some string")
        Mockito.`when`(
            mockContextResources.getString(Matchers.anyInt(), Matchers.any())
        ).thenReturn("Some string with a parameter in it")

    }

}