package com.scholar.center.ui.materials

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.scholar.center.R
import com.scholar.center.databinding.FragmentPdfBinding
import com.scholar.center.unit.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL


@AndroidEntryPoint
class PdfFragment : Fragment(R.layout.fragment_pdf) {
    private val viewModel: MaterialContentVM by viewModels()
    private lateinit var binding: FragmentPdfBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPdfBinding.bind(view)


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.material.collect {
                    it?.let { materialWithDetail ->
                        Log.d("LOadPDF", "${materialWithDetail.material}")
                        val material = materialWithDetail.material
                        material.content?.let { link ->
                            Log.d("LOadPDF", "${BASE_URL}${link}")



                            binding.webView.settings.javaScriptEnabled = true
                            binding.webView.settings.builtInZoomControls = true
                            binding.webView.settings.displayZoomControls = false
                            val pdfUrl = "${BASE_URL}${link}"

                            binding.webView.webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView, url: String) {
                                    binding.webView.loadUrl(
                                        "javascript:(function() { " +
                                                "document.querySelector('[role=\"toolbar\"]').remove();})()"
                                    )

                                }
                            }


                            binding.webView.loadUrl("$pdfUrl")
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.byteArray.collect {
                    it?.let { pdfArray ->
                        Log.d("LOadPDF", "${pdfArray}")

//                        binding.pdfView.fromFile(pdfArray)
//                            .defaultPage(0)
//                            .enableSwipe(true)
//                            .swipeHorizontal(false)
//                            .load()
                    }
                }
            }
        }
    }

}