package com.example.testmodel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testmodel.databinding.ViewDoubleTextBinding

class DoubleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewDoubleTextBinding

    init {
        // Inflate layout
        binding = ViewDoubleTextBinding.inflate(LayoutInflater.from(context), this)

        // Retrieve custom attributes
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DoubleTextView)
        setLeftText(attributes.getString(R.styleable.DoubleTextView_leftText) ?: "")
        setRightText(attributes.getString(R.styleable.DoubleTextView_rightText) ?: "")
        attributes.recycle()
    }


    // Public methods to set text dynamically
    fun setLeftText(text: String) {
        binding.tvLeft.text = text
    }

    fun setRightText(text: String) {
        binding.tvRight.text = text
    }

    fun getLeftText(): String {
        return binding.tvLeft.text.toString()
    }

    fun getRightText(): String {
        return binding.tvRight.text.toString()
    }
}
