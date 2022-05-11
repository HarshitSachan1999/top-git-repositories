package com.example.gitrepo.data.remote.dtoModel.repoDto

import com.example.gitrepo.domain.model.Repo

data class RepoDto(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)

