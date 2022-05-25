package com.example.gitrepo.app.repoDetails

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gitrepo.R
import com.example.gitrepo.ViewState
import com.example.gitrepo.app.repoDetails.presentation.RepoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repo_details.*


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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        star_count.text = "⭐: $starCount"
        webView.isEnabled = false
        webView.isClickable = false

        viewModel.fetchReadme(owner, repo)
        viewModel.viewState.observe(requireActivity(), {
            if (it.status == ViewState.Status.SUCCESS) {
                val url:String = it.data?.download_url!!
                loadReadme(url)

            }else if (it.status == ViewState.Status.ERROR){
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadReadme(currUrl:String){
        webView.settings.javaScriptEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                activity?.title = "Loading..."
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                activity?.setTitle(R.string.app_name)
            }

            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                return false
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == currUrl) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        webView.loadUrl(currUrl)
    }
}