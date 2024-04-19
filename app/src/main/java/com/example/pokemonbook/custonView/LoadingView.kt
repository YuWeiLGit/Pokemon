package com.example.pokemonbook.custonView

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import com.example.pokemonbook.R
import com.example.pokemonbook.databinding.LoadingviewBinding

class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    companion object {
        const val SHOW_WHITE_LOADING = 0
        const val HIDE_LOADING = 1
        const val NO_NETWORK = 2
        const val NO_DATA = 3
    }


    private lateinit var binding: LoadingviewBinding
    private val viewFadeOutListener: Animation.AnimationListener =
        object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                binding.loadingLinearLayout.visibility = GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        }

    init {
        val layoutInflater = LayoutInflater.from(this.context)
        binding = LoadingviewBinding.inflate(layoutInflater, this, false)
        addView(binding.root)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.LoadingView,
                defStyleAttr,
                0
            )
            val isTransBackground =
                typedArray.getBoolean(R.styleable.LoadingView_isTransparentBg, false)
            typedArray.recycle()
            val bgColor = if (isTransBackground) {
                Color.TRANSPARENT
            } else {
                Color.WHITE
            }
            binding.alphaView.background = ColorDrawable(bgColor)
        }
    }


    fun setStatus(state: Int) {
        visibility = VISIBLE
        when (state) {
            SHOW_WHITE_LOADING -> {
                binding.alphaView.alpha = 1f
                binding.loadingLinearLayout.visibility = VISIBLE
            }


            HIDE_LOADING -> {
                visibility = GONE
            }

            NO_NETWORK -> {
                AnimationUtils.loadAnimation(binding.root.context, R.anim.fade_out)
                    .setAnimationListener(viewFadeOutListener)
                binding.loadingLinearLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        binding.root.context, R.anim.fade_out
                    )
                )
            }

            NO_DATA -> {
                binding.loadingLinearLayout.visibility = GONE
            }
        }
    }


}
