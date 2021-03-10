package com.doublejj.edit.ui.modules.main.myedit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.MyeditFragmentBinding

class MyeditFragment : Fragment() {
    private lateinit var binding: MyeditFragmentBinding
    private lateinit var viewModel: MyeditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.myedit_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MyeditViewModel::class.java)

        binding.myeditViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}