package com.example.pokemonbook.custonView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import com.example.pokemonbook.R
import com.example.pokemonbook.databinding.CvTopButtonBinding


class TopButton : RelativeLayout {

    private lateinit var binding: CvTopButtonBinding

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        initAttrs(context, attr)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.TopButton)
        val b = attr.getBoolean(R.styleable.TopButton_isSelect, false)
        val text = attr.getText(R.styleable.TopButton_tbText)
        binding = CvTopButtonBinding.inflate(LayoutInflater.from(context), this, true)
        setSelect(b)
        setText(text)
        attr.recycle()
    }

    fun setSelect(isSelect: Boolean) {
        binding.bg.background = if (isSelect) {
            ResourcesCompat.getDrawable(resources, R.drawable.bg_top_button_selected, null)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.bg_top_button_unselect, null)
        }
    }

    private fun setText(text: CharSequence) {
        binding.textview.text = text
    }

}