package com.doublejj.edit.ui.modules.main.home.waiting_for_comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doublejj.edit.R
import com.doublejj.edit.databinding.WaitingForCommentFragmentBinding

class WaitingForCommentFragment : Fragment() {
    private lateinit var binding: WaitingForCommentFragmentBinding
    private lateinit var viewModel: WaitingForCommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.waiting_for_comment_fragment, container, false)
        viewModel = ViewModelProvider(this).get(WaitingForCommentViewModel::class.java)

        binding.waitingForCommentViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}