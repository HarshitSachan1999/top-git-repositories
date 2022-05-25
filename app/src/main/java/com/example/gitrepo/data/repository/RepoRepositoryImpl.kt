package com.example.gitrepo.data.repository

import com.example.gitrepo.common.Resources
import com.example.gitrepo.data.remote.RepoApi
import com.example.gitrepo.data.remote.dtoModel.readmeDto.toReadme
import com.example.gitrepo.data.remote.dtoModel.repoDto.toRepo
import com.example.gitrepo.domain.model.Readme
import com.example.gitrepo.domain.model.Repo
import com.example.gitrepo.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RepoRepositoryImpl @Inject constructor(private val api:RepoApi) : RepoRepository {
    override suspend fun getRepos(): Flow<Resources<List<Repo>>> = flow {
        try {
            emit(Resources.Loading<List<Repo>>())
            val repos = api.getRepos().items.map { it.toRepo() }
            emit(Resources.Success<List<Repo>>(repos))
        }catch (e: Exception){
            emit(Resources.Error<List<Repo>>(e.localizedMessage?: "Unable to parse data"))
        }
    }

    override suspend fun getReadme(owner: String, repo: String): Flow<Resources<Readme>> = flow {
        try {
            emit(Resources.Loading<Readme>())
            val repos = api.getReadme(owner, repo).toReadme()
            emit(Resources.Success<Readme>(repos))
        }catch (e: Exception){
            emit(Resources.Error<Readme>(e.localizedMessage?: "Unable to parse data"))
        }
    }
}