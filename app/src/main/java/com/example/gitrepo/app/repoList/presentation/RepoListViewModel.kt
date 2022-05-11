package com.example.gitrepo.app.repoList.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitrepo.ViewState
import com.example.gitrepo.domain.model.Repo
import com.example.gitrepo.domain.useCases.getRepos.GetReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase
):ViewModel(), LifecycleObserver {
    val viewState = MutableLiveData<ViewState<List<Repo>?>>()

    fun fetchRepos() = viewModelScope.launch {

        withContext(Dispatchers.IO){
            getReposUseCase.invoke().onEach { result ->
                when(result){
                    is com.example.gitrepo.common.Resources.Success -> {
                        viewState.value = ViewState.success(result.data)
                    }
                    is com.example.gitrepo.common.Resources.Error -> {
                        viewState.value = ViewState.error(result.message)
                    }
                    is com.example.gitrepo.common.Resources.Loading -> {
                        viewState.value = ViewState.loading()
                    }
                }

            }.launchIn(viewModelScope)
        }
    }
}