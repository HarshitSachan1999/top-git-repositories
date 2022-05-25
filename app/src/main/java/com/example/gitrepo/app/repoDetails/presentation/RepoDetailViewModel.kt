package com.example.gitrepo.app.repoDetails.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitrepo.ViewState
import com.example.gitrepo.common.Resources
import com.example.gitrepo.domain.model.Readme
import com.example.gitrepo.domain.useCases.getReadme.GetReadmeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val getReadmeUseCase: GetReadmeUseCase
): ViewModel(), LifecycleObserver {

    val viewState = MutableLiveData<ViewState<Readme?>>()

    fun fetchReadme(owner:String, repo:String) = viewModelScope.launch {

        withContext(Dispatchers.IO){
            getReadmeUseCase.invoke(owner,repo).onEach {
                when(it){
                    is Resources.Success -> {
                        viewState.value = ViewState.success(it.data)
                    }
                    is Resources.Error -> {
                        viewState.value = ViewState.error(it.message)
                    }
                    is Resources.Loading -> {
                        viewState.value = ViewState.loading()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}