package com.example.android_hilt_sample.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/***
 * this factory pattern came around 6 months back and using this we can do constructor injection in fragments
 * here inject all the dependencies which your fragments which will be created via this factory might need
 */
@ExperimentalCoroutinesApi
class MainFragmentFactory @Inject constructor(private val someString: String) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MainFragment::class.java.name -> {
                val fragment = MainFragment(someString)
                fragment
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}