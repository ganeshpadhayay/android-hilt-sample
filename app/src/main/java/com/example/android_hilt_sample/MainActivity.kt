package com.example.android_hilt_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/***
 * This simple annotation is replacing the need of telling dagger to inject dependencies into this android components
 * We used to write something like this "(application as MyApplication).appComponent.inject(this)" in here and "fun inject(mainActivity: MainActivity)"
 * in the AppComponent class, this annotation gets rid of all that boilerplate
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //this is field injection
    @Inject
    lateinit var welcomeClass: WelcomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(welcomeClass.showWelcomeMessage())
    }
}

/***
 * Dagger in the background creating this dependency at compile time and injecting it into the activity at run time
 * Here we are injecting SomeOtherClass via constructor so it is a constructor injection
 */
class WelcomeClass @Inject constructor(private val userNameClass: UserNameClass) {
    fun showWelcomeMessage(): String {
        return "Hi There, " + userNameClass.showUserName()
    }
}

class UserNameClass @Inject constructor() {
    fun showUserName(): String {
        return "Ganesh!"
    }
}