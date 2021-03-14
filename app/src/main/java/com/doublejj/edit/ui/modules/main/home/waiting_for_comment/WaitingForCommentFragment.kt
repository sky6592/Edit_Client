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
import com.doublejj.edit.ui.modules.main.MainActivity

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
        (activity as MainActivity).increaseFragmentCount()

        // TODO : toolbar tv_title 값 바꾸기
//        binding.toolbarTitleWithButtons.

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).decreaseFragmentCount()
    }
}