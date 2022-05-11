package com.example.gitrepo.domain.repository

import com.example.gitrepo.common.Resources
import com.example.gitrepo.domain.model.Readme
import com.example.gitrepo.domain.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun getRepos() : Flow<Resources<List<Repo>>>

    suspend fun getReadme(owner:String, repo:String) : Flow<Resources<Readme>>
}