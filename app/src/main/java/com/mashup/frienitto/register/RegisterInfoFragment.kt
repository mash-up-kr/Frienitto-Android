package com.mashup.frienitto.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mashup.frienitto.R
import com.mashup.frienitto.base.BaseFragment
import com.mashup.frienitto.databinding.FragmentRegisterInfoBinding
import kotlinx.android.synthetic.main.fragment_email_auth.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RegisterInfoFragment : BaseFragment<FragmentRegisterInfoBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_register_info

    private val viewModel: RegisterFragmentViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.signinComplete.observe(this, Observer {
            activity?.finish()
        })
    }
}