package com.bogdan.codeforceswatcher.features.problems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

class ProblemsFragmentNew: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AlgoismeTheme {
                    ProblemsView()
                }
            }
        }
    }
}