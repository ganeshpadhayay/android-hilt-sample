package com.example.android_hilt_sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/***
 * this simple annotation removes all the boilerplate of creating AppComponent and instantiating it via factory and all
 */
@HiltAndroidApp
class MyApplication : Application() {
}