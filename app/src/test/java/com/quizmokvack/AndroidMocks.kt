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

    //@Mock
    //private lateinit var mockMainThreadLooper: Looper

    // private val mainThread: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

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

        /**
        PowerMockito.mockStatic(Looper::class.java)
        val mockMainThreadLooper: Looper = mock(Looper::class.java)
        Mockito.`when`(Looper.getMainLooper()).thenReturn(mockMainThreadLooper)
        val mockMainThreadHandler: Handler = mock(Handler::class.java)
        val handlerPostAnswer: Answer<Boolean> =
        Answer<Boolean> { invocation ->
        val runnable = invocation.getArgumentAt(0, Runnable::class.java)
        var delay: Long? = 0L
        if (invocation.arguments.size > 1) {
        delay = invocation.getArgumentAt(1, Long::class.java)
        }
        if (runnable != null) {
        mainThread.schedule(runnable, delay, TimeUnit.MILLISECONDS)
        }
        true
        }
        Mockito.doAnswer(handlerPostAnswer).`when`(mockMainThreadHandler).post(any(Runnable::class.java))
        Mockito.doAnswer(handlerPostAnswer).`when`(mockMainThreadHandler)
        .postDelayed(any(Runnable::class.java), anyLong())
        PowerMockito.whenNew(Handler::class.java).withArguments(mockMainThreadLooper)
        .thenReturn(mockMainThreadHandler)
         */
    }

}