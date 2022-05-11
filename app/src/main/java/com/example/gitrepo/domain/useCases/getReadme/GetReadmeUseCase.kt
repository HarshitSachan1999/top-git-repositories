package com.example.gitrepo.domain.useCases.getReadme

import com.example.gitrepo.domain.repository.RepoRepository
import javax.inject.Inject

class GetReadmeUseCase @Inject constructor(private val repository: RepoRepository) {
    suspend fun invoke(owner:String, repo:String) = repository.getReadme(owner, repo)
}