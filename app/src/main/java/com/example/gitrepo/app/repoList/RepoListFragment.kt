package com.example.gitrepo.app.repoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitrepo.R
import com.example.gitrepo.ViewState
import com.example.gitrepo.app.repoList.adapter.RepoListAdapter
import com.example.gitrepo.app.repoList.presentation.RepoListViewModel
import com.example.gitrepo.domain.model.Repo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repo_list.*

@AndroidEntryPoint
class RepoListFragment : Fragment() {

    lateinit var navController: NavController
    var repoList:ArrayList<Repo> = arrayListOf()
    private val viewModel:RepoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        repo_list_rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = RepoListAdapter(requireContext(), repoList)
        repo_list_rv.adapter = adapter


        adapter.onItemClick = {
            val bundle = bundleOf(
                "repo" to repoList[it].name,
                "owner" to repoList[it].owner,
                "starCount" to repoList[it].starCount.toString()
            )
            navController.navigate(R.id.action_repoListFragment_to_repoDetailsFragment2, bundle)
        }
            viewModel.fetchRepos()
            viewModel.viewState.observe(requireActivity(), {
                if (it.status == ViewState.Status.SUCCESS) {
                    repoList.addAll(it.data!!)
                    adapter.notifyDataSetChanged()
                }
            })

    }

}

