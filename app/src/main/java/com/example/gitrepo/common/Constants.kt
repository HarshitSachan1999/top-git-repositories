package com.example.gitrepo.common

import android.content.Context

object Constants {
    const val repoApi:String = "https://api.github.com/"
    fun lineNumber(e:Exception) = e.stackTrace[0].lineNumber
//    search = "/search/repositories?q=language:python&sort=stars&order=desc"
//    readme = "/repos/{owner}/{repo}/readme"
}