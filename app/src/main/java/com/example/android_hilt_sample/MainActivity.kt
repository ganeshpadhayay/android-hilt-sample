package com.example.android_hilt_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Singleton

/***
 * This simple annotation is replacing the need of telling dagger to inject dependencies into this android components
 * We used to write something like this "(application as MyApplication).appComponent.inject(this)" in here and "fun inject(mainActivity: MainActivity)"
 * in the AppComponent class, this annotation gets rid of all that boilerplate
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //this is field injection
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.showWelcomeMessage())
    }
}

/***
 * Dagger in the background creating this dependency at compile time and injecting it into the activity at run time
 * Here we are injecting SomeOtherClass via constructor so it is a constructor injection
 */
class SomeClass @Inject constructor(private val someInterfaceImpl: SomeInterface, private val gson: Gson) {
    fun showWelcomeMessage(): String {
        return "Hi There, " + someInterfaceImpl.getAThing() + gson.hashCode()
    }
}

//this is a normal class and can be injected easily via constructor
class UserNameClass @Inject constructor() {
    fun showUserName(): String {
        return "Ganesh!"
    }
}

interface SomeInterface {
    fun getAThing(): String
}

class SomeInterfaceImpl @Inject constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing"
    }
}

/***
 * this is one way to bind interfaces to their implementations and then we can inject these implementations via their interfaces
 * @InstallIN is another new annotation to attach the module to these components which are now tier wise along with their respective
 * Scopes
 */
//@InstallIn(ApplicationComponent::class)
//@Module
//abstract class MyModule {
//    //since we are attaching this to ApplicationComponent that is why we are using Singleton here otherwise we would need to add
//    //an appropriate scope here
//    @Singleton
//    @Binds
//    abstract fun bindSomeDependency(someInterfaceImpl: SomeInterfaceImpl): SomeInterface
//}


/***
 * Here we will use @Provides which works for all the cases, this is relatively simpler than the @Binds method
 */
@InstallIn(ApplicationComponent::class)
@Module
class MyModule {

    @Singleton
    @Provides
    fun provideSomeInterface(): SomeInterface {
        return SomeInterfaceImpl()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}
