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
import javax.inject.Qualifier
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
        println(someClass.showWelcomeMessage1() + "----------" + someClass.showWelcomeMessage2())
    }
}

/***
 * Dagger in the background creating this dependency at compile time and injecting it into the activity at run time
 * Here we are injecting SomeOtherClass via constructor so it is a constructor injection
 */
class SomeClass @Inject constructor(@Impl1 private val someInterfaceImpl1: SomeInterface, @Impl2 private val someInterfaceImpl2: SomeInterface) {
    fun showWelcomeMessage1(): String {
        return "Hi There, " + someInterfaceImpl1.getAThing()
    }

    fun showWelcomeMessage2(): String {
        return "Hi There, " + someInterfaceImpl2.getAThing()
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

class SomeInterfaceImpl1 @Inject constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing1"
    }
}

class SomeInterfaceImpl2 @Inject constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing2"
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

    @Impl1
    @Singleton
    @Provides
    fun provideSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl1()
    }

    @Impl2
    @Singleton
    @Provides
    fun provideSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}

/***
 * The problem of providing two objects of the same types can be solved using @Named annotation or maybe using scopes or we can
 * also make custom annotation to solve this issue
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2
