package com.quizmokvack.ui.question

//import org.powermock.core.classloader.annotations.PowerMockIgnore
//import org.powermock.modules.junit4.PowerMockRunner
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.quizmokvack.AndroidMocks
import com.quizmokvack.RxRule
import com.quizmokvack.TestFileLoader
import com.quizmokvack.di.DaggerTestAppComponent
import com.quizmokvack.di.GenericViewModelFactory
import com.quizmokvack.di.TestAppModule
import org.junit.*
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class QuestionViewModelTest {

    @Rule
    @JvmField
    val rxRules = RxRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val androidMocks = AndroidMocks()

    private var okHttpCountDownLatch: CountDownLatch = CountDownLatch(1)

    private lateinit var testAppModule: TestAppModule

    @Inject
    lateinit var viewModelFactory: GenericViewModelFactory<QuestionViewModel>

    private val viewModelStore = ViewModelStore()
    private val viewModel: QuestionViewModel by lazy {
        ViewModelProvider(viewModelStore, viewModelFactory)[QuestionViewModel::class.java]
    }

    @Before
    fun setup() {
        okHttpCountDownLatch = CountDownLatch(1)
        testAppModule = TestAppModule(
            androidMocks.mockApplicationContext,
            Runnable {
                okHttpCountDownLatch.countDown()
            },
            "/quiz.json" to TestFileLoader.readJsonFileFromAssets("quiz.json")
        )
        DaggerTestAppComponent.builder().appModule(testAppModule).build().inject(this)
    }

    @After
    fun tearDown() {
        viewModelStore.clear()
        testAppModule.shutDownServer()
    }

    @Test
    fun testLifelinesEnabled() {
        viewModel.initStateObservable()
        okHttpCountDownLatch.await()

        Assert.assertTrue(
            " Expected boost time to be enabled",
            viewModel.boostTimeEnabled.get()
        )

        Assert.assertTrue(
            " Expected fifty fifty to be enabled",
            viewModel.fiftyFiftyEnabled.get()
        )
    }

    @Test
    fun testLifelinesDisabled() {
        viewModel.initStateObservable()
        okHttpCountDownLatch.await()

        viewModel.onBoostTimeClick()

        Assert.assertFalse(
            " Expected boost time to be disabled",
            viewModel.boostTimeEnabled.get()
        )

        viewModel.onFiftyFiftyClick()

        Assert.assertFalse(
            " Expected fifty fifty to be disabled",
            viewModel.fiftyFiftyEnabled.get()
        )
    }

    @Test
    fun testNextButtonDisabled() {
        viewModel.initStateObservable()
        okHttpCountDownLatch.await()

        Assert.assertFalse(
            "Expecting next button to be disabled",
            viewModel.nextButtonEnabled.get()
        )
    }

    @Test
    fun testTimeIsUp() {
        viewModel.initStateObservable()
        okHttpCountDownLatch.await()

        viewModel.onTimeIsUpCallback()

        Assert.assertTrue(
            "Expected next button to be enabled",
            viewModel.nextButtonEnabled.get()
        )

        Assert.assertFalse(
            "Expected choices buttons to be disabled",
            viewModel.choicesEnabled.get()
        )

        Assert.assertFalse(
            "Expected fifty fifty to be disabled",
            viewModel.fiftyFiftyEnabled.get()
        )

        Assert.assertFalse(
            "Expected boost time buttons to be disabled",
            viewModel.boostTimeEnabled.get()
        )
    }

}