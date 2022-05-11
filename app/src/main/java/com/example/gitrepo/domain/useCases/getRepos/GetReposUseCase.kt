package com.example.gitrepo.domain.useCases.getRepos

import com.example.gitrepo.domain.repository.RepoRepository
import javax.inject.Inject

class GetReposUseCase @Inject constructor(private val repository: RepoRepository) {
    suspend fun invoke() = repository.getRepos()
}