package com.example.pokemonbook.custonView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.pokemonbook.R
import com.example.pokemonbook.databinding.LoadingableImagevieBinding

class LoadingableImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val picCompressSize = 200

    private var binding: LoadingableImagevieBinding
    private var src: Any? = null


    init {
        val layoutInflater = LayoutInflater.from(this.context)
        binding = LoadingableImagevieBinding.inflate(layoutInflater, this, false)
        addView(binding.root)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.LoadingableImageView,
                defStyleAttr,
                0
            )
            val imgSrc: String? =
                typedArray.getNonResourceString(R.styleable.LoadingableImageView_imgSrc)
            this.src = imgSrc
            typedArray.recycle()
            imgSrc?.let {
                startLoadImage(imgSrc)
            }
        }
    }

    fun startLoadImage(srcUrl: String?) {
        binding.lv.setStatus(LoadingView.SHOW_WHITE_LOADING)
        val requestOptions = RequestOptions()
            .override(picCompressSize, picCompressSize)
        Glide.with(binding.root.context)
            .load(srcUrl)
            .listener(createGlideListener())
            .error(R.drawable.error)
            .apply(requestOptions)
            .into(binding.iv)
    }

    private fun createGlideListener(): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                binding.lv.setStatus(LoadingView.HIDE_LOADING)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                binding.lv.setStatus(LoadingView.HIDE_LOADING)
                return false
            }
        }
    }
}