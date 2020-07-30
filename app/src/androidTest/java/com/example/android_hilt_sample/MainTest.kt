package com.example.android_hilt_sample

import androidx.test.core.app.launchActivity
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.example.android_hilt_sample.di.AppModule
import com.example.android_hilt_sample.ui.MainActivity
import com.example.android_hilt_sample.ui.MainFragment
import com.example.android_hilt_sample.ui.MainFragmentFactory
import com.example.android_hilt_sample.util.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@UninstallModules(AppModule::class)
@HiltAndroidTest
class MainTest {

    //we can use order available after JUnit 4.13 to give order to executions
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var someTestingString: String

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testSomething() {
        assertThat(someTestingString, containsString("testing"))
    }

    @Test
    fun mainActivityTest() {
        val activityScenario = launchActivity<MainActivity>()
    }

    @Test
    fun mainFragmentTest() {
        val fragmentScenario = launchFragmentInHiltContainer<MainFragment>(factory = fragmentFactory) {

        }
    }

    @InstallIn(ApplicationComponent::class)
    @Module
    class TestAppModule {
        @Singleton
        @Provides
        fun provideSomeString(): String {
            return "some testing string"
        }

    }
}