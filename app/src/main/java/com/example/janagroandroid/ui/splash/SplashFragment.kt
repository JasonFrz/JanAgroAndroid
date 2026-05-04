package com.example.janagroandroid.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.janagroandroid.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logo = view.findViewById<ImageView>(R.id.ivLogo)
        val sawid = view.findViewById<ImageView>(R.id.ivSawid)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val subtitle = view.findViewById<TextView>(R.id.tvSubtitle)
        val logoSection = view.findViewById<View>(R.id.logoSection)

        logo.animate().alpha(1f).setDuration(700).start()
        title.animate().alpha(1f).setDuration(700).setStartDelay(150).start()
        subtitle.animate().alpha(1f).setDuration(700).setStartDelay(300).start()

        view.postDelayed({

            logoSection.animate()
                .alpha(0f)
                .setDuration(400)
                .withEndAction {

                    logoSection.visibility = View.GONE

                    sawid.visibility = View.VISIBLE
                    sawid.scaleX = 0.3f
                    sawid.scaleY = 0.3f
                    sawid.alpha = 1f

                    sawid.animate()
                        .scaleX(5f)
                        .scaleY(5f)
                        .setDuration(1200)
                        .setInterpolator(AccelerateInterpolator())
                        .withEndAction {

                            findNavController().navigate(R.id.homeFragment)
                        }
                        .start()
                }
                .start()

        }, 1700)
    }
}