package com.absoft.rotateanimation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.absoft.rotateanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    private var frontDegree = 0
    private var backDegree = 0
    private var translationXVal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.ivFrontDown.setOnClickListener(this)
        binding.ivFrontUp.setOnClickListener(this)
        binding.ivEndUp.setOnClickListener(this)
        binding.ivEndDown.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_front_up -> {
                frontDegree += 10
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                handleFrontButtons()
            }
            R.id.iv_front_down -> {
                frontDegree -= 10
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                handleFrontButtons()
            }
            R.id.iv_end_up -> {
                backDegree += 10
                translationXVal += 12
                binding.view3.animate().setDuration(ANIM_DURATION).rotation(-backDegree.toFloat())
                binding.view4.animate().setDuration(ANIM_DURATION).rotation(backDegree.toFloat())
                    .translationXBy(-translationXVal.toFloat())
                handleBackButtons()
            }
            R.id.iv_end_down -> {
                backDegree -= 10
                binding.view3.animate().setDuration(ANIM_DURATION).rotation(-backDegree.toFloat())
                binding.view4.animate().setDuration(ANIM_DURATION).rotation(backDegree.toFloat())
                    .translationXBy(translationXVal.toFloat())
                translationXVal -= 12
                handleBackButtons()
            }
        }
    }

    private fun handleBackButtons() {
        binding.ivEndDown.isEnabled = backDegree > 0
        binding.ivEndUp.isEnabled = backDegree < MAX_ROTATION
    }

    private fun handleFrontButtons() {
        binding.ivFrontDown.isEnabled = frontDegree > 0
        binding.ivFrontUp.isEnabled = frontDegree < MAX_ROTATION
    }

    companion object {
        const val MAX_ROTATION = 90
        const val ANIM_DURATION = 1500L
    }
}