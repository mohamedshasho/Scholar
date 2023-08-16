package com.scholar.center.ui.materials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.scholar.center.R
import com.scholar.center.databinding.FragmentPdfBinding
import com.scholar.center.ui.dialogs.LoadingDialogFragment
import com.scholar.center.unit.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PdfFragment : Fragment(R.layout.fragment_pdf) {
    private val viewModel: MaterialContentVM by viewModels()
    private lateinit var binding: FragmentPdfBinding

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPdfBinding.bind(view)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->
                findNavController().popBackStack()
            }
        val loadingDialog = LoadingDialogFragment()
        loadingDialog.show(parentFragmentManager, "loading_dialog")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.material.collectLatest {
                    it?.let { materialWithDetail ->
                        val material = materialWithDetail.material
                        material.content?.let { link ->
                            val pdfLink = "${BASE_URL}${link}"
                            openPdfByIntent(pdfLink)
                        }
                        if (loadingDialog.isVisible) {
                            loadingDialog.dismiss()
                        }
                    }
                }
            }
        }
    }


    private fun openPdfByIntent(pdfLink: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfLink))
        launcher.launch(browserIntent)
    }

    private fun openPdfByWebView(pdfLink: String) {
//      binding.webView.settings.javaScriptEnabled = true
//      binding.webView.settings.builtInZoomControls = true
//      binding.webView.settings.displayZoomControls = false
//      val pdfUrl = "${BASE_URL}${link}"
//      binding.webView.loadUrl("https://docs.google.com/gview?embedded=true&url=pdfLink")
    }
}