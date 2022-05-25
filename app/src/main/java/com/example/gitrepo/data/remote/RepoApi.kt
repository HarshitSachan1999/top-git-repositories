package com.example.gitrepo.data.remote

import com.example.gitrepo.data.remote.dtoModel.readmeDto.ReadmeDto
import com.example.gitrepo.data.remote.dtoModel.repoDto.Item
import com.example.gitrepo.data.remote.dtoModel.repoDto.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL
import java.nio.charset.Charset

interface RepoApi {
    @GET("/search/repositories?q=language:kotlin&sort=stars&order=desc&per_page=50")
    suspend fun getRepos() : RepoDto

    @GET("/repos/{owner}/{repo}/readme")
    suspend fun getReadme(@Path("owner")owner : String, @Path("repo")repo : String) : ReadmeDto

//    suspend fun getContent(url:String) : String  { return URL(url).readText(Charset.forName("ISO-8859-1"))}
}