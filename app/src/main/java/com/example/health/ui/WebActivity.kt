package com.ssionii.bloodNote.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityWebBinding
import com.ssionii.bloodNote.ui.main.EmptyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class WebActivity : BaseActivity<ActivityWebBinding, EmptyViewModel>() {

    override val layoutResID: Int
        get() = R.layout.activity_web
    override val viewModel: EmptyViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actWebWv.apply {
            settings.run {
                javaScriptEnabled = true
                domStorageEnabled = true
                useWideViewPort = true
            }

            webChromeClient = WebChromeClient()

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    viewDataBinding.actWebPb.visibility = View.VISIBLE
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    viewDataBinding.actWebPb.visibility = View.GONE
                    super.onPageFinished(view, url)
                }
            }
        }

        intent.getStringExtra("url")?.let {
            viewDataBinding.actWebWv.loadUrl(it)
        }

        viewDataBinding.actWebToolbar.toolbarBackTvTitle.text = intent.getStringExtra("title")

        setButton()

    }


    private fun setButton() {
        viewDataBinding.actWebToolbar.toolbarBackIvBack.setOnClickListener {
            finish()
        }

    }
}