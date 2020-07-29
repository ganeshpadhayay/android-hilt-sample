package com.example.android_hilt_sample.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.android_hilt_sample.model.Blog
import com.example.android_hilt_sample.repository.MainRepository
import com.example.android_hilt_sample.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/***
 * We would be using MVI here which is basically an enhanced MVVM pattern
 */
@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(@Assisted private val savedStateHandle: SavedStateHandle,
                                                 private val mainRepository: MainRepository) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>> get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetBlogEvents -> {
                    mainRepository.getBlogs()
                            .onEach {
                                _dataState.value = it
                            }
                            .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    //who cares
                }
            }
        }
    }

}

/***
 * this is the MVI pattern which can handle all the states in which our view could be in
 */
sealed class MainStateEvent {

    object GetBlogEvents : MainStateEvent()

    object None : MainStateEvent()
}