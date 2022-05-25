package com.example.gitrepo.app.repoDetails

import android.R.attr
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gitrepo.R
import com.example.gitrepo.ViewState
import com.example.gitrepo.app.repoDetails.presentation.RepoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repo_details.*
import java.nio.charset.StandardCharsets


@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    lateinit var readme:String
    private val viewModel: RepoDetailViewModel by viewModels()
    lateinit var owner:String
    lateinit var repo:String
    lateinit var starCount:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        owner = arguments!!.getString("owner").toString()
        repo = arguments!!.getString("repo").toString()
        starCount = arguments!!.getString("starCount").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        star_count.text = "⭐: $starCount"
        readme_text.movementMethod = ScrollingMovementMethod()

        viewModel.fetchReadme(owner, repo)
        viewModel.viewState.observe(requireActivity(), {
            if (it.status == ViewState.Status.SUCCESS) {
                val text:String? = it.data?.content
                val data: ByteArray = Base64.decode(text, Base64.DEFAULT)
                readme_text.text = String(data, StandardCharsets.UTF_8)
            }else if (it.status == ViewState.Status.ERROR){
                readme_text.text = "Something went wrong"
            }
        })

    }
}