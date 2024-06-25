package com.rafiul.lovenotes.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.rafiul.lovenotes.R
import com.rafiul.lovenotes.base.BaseFragment
import com.rafiul.lovenotes.databinding.FragmentSplashBinding
import com.rafiul.lovenotes.utils.hideAppBar


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::class) {

    companion object {
        private const val HALT: Long = 2000L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppBar()
        Handler(Looper.getMainLooper()).postDelayed({
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_splashFragment_to_homeFragment,
                null,
                navOptions
            )
        }, HALT)
    }
}