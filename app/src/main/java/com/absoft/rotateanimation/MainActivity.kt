package com.absoft.rotateanimation

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.absoft.rotateanimation.databinding.ActivityMainBinding
import com.absoft.rotateanimation.extensions.dp
import com.absoft.rotateanimation.extensions.setMultiFunctionalTouchListener
import com.absoft.rotateanimation.listeners.MultiFunctionalTouchListener

class MainActivity : AppCompatActivity(), MultiFunctionalTouchListener.EventListener,
    View.OnLayoutChangeListener {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    private var frontDegree = 0
    private var backDegree = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.ivFrontUp.setMultiFunctionalTouchListener(this)
        binding.ivFrontDown.setMultiFunctionalTouchListener(this)
        binding.ivEndUp.setMultiFunctionalTouchListener(this)
        binding.ivEndDown.setMultiFunctionalTouchListener(this)
        binding.view1.addOnLayoutChangeListener(this)
        binding.view3.addOnLayoutChangeListener(this)
        binding.view4.addOnLayoutChangeListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_front_up -> {
                frontDegree =
                    if (frontDegree + 10 > MAX_ROTATION) MAX_ROTATION else frontDegree + 10
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_front_down -> {
                frontDegree = if (frontDegree - 10 < 0) 0 else frontDegree - 10
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_end_up -> {
                backDegree = if (backDegree + 10 > MAX_ROTATION) MAX_ROTATION else backDegree + 10
                binding.view3.animate().setDuration(ANIM_DURATION).rotation(-backDegree.toFloat())
                binding.view4.animate().setDuration(ANIM_DURATION)
                    .translationX(-backDegree.toFloat().dp).rotation(backDegree.toFloat())
                handleBackButtons()
            }

            R.id.iv_end_down -> {
                backDegree = if (backDegree - 10 < 0) 0 else backDegree - 10
                binding.view3.animate().setDuration(ANIM_DURATION).rotation(-backDegree.toFloat())
                binding.view4.animate().setDuration(ANIM_DURATION)
                    .translationX(-backDegree.toFloat().dp).rotation(backDegree.toFloat())
                handleBackButtons()
            }
        }
    }

    override fun onHolding(v: View?) {
        when (v?.id) {
            R.id.iv_front_up -> {
                if (frontDegree < MAX_ROTATION) {
                    frontDegree += 1
                    binding.view1.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(frontDegree.toFloat())
                    binding.tvFrontDegree.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(-frontDegree.toFloat())
                    handleFrontButtons()
                }
            }

            R.id.iv_front_down -> {
                if (frontDegree > 0) {
                    frontDegree -= 1
                    binding.view1.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(frontDegree.toFloat())
                    binding.tvFrontDegree.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(-frontDegree.toFloat())
                    handleFrontButtons()
                }
            }

            R.id.iv_end_up -> {
                if (backDegree < MAX_ROTATION) {
                    backDegree += 1
                    binding.view3.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(-backDegree.toFloat())
                    binding.view4.animate().setDuration(HOLD_ANIM_DURATION)
                        .translationX(-backDegree.toFloat().dp)
                        .rotation(backDegree.toFloat())
                    handleBackButtons()
                }
            }

            R.id.iv_end_down -> {
                if (backDegree > 0) {
                    backDegree -= 1
                    binding.view3.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(-backDegree.toFloat())
                    binding.view4.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(backDegree.toFloat())
                        .translationX(-backDegree.toFloat().dp)
                    handleBackButtons()
                }
            }
        }
        v?.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }

    override fun onDoubleClick(v: View?) {
        when (v?.id) {
            R.id.iv_front_up -> {
                frontDegree = MAX_ROTATION
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_front_down -> {
                frontDegree = 0
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_end_up -> {
                backDegree = MAX_ROTATION
                binding.view3.animate().setDuration(ANIM_DURATION).rotation(-backDegree.toFloat())
                binding.view4.animate().setDuration(ANIM_DURATION)
                    .translationX(-backDegree.toFloat().dp).rotation(backDegree.toFloat())
                handleBackButtons()
            }

            R.id.iv_end_down -> {
                backDegree = 0
                binding.view3.animate().setDuration(ANIM_DURATION).rotation(-backDegree.toFloat())
                binding.view4.animate().setDuration(ANIM_DURATION)
                    .translationX(-backDegree.toFloat().dp).rotation(backDegree.toFloat())
                handleBackButtons()
            }
        }
    }

    override fun onHoldStart(v: View?) {
        binding.tvFrontDegree.visibility = View.VISIBLE
        binding.tvFrontDegree.animate().setDuration(ANIM_DURATION).alpha(1f)
        super.onHoldStart(v)
    }

    override fun onHoldEnd(v: View?) {
        binding.tvFrontDegree.animate().setDuration(ANIM_DURATION).alpha(0f)
        super.onHoldEnd(v)
    }

    private fun handleBackButtons() {
        binding.ivEndDown.isEnabled = backDegree > 0
        binding.ivEndUp.isEnabled = backDegree < MAX_ROTATION
    }

    private fun handleFrontButtons() {
        binding.tvFrontDegree.text = frontDegree.toString()
        binding.ivFrontDown.isEnabled = frontDegree > 0
        binding.ivFrontUp.isEnabled = frontDegree < MAX_ROTATION
    }

    override fun onLayoutChange(
        view: View?,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Int,
        p5: Int,
        p6: Int,
        p7: Int,
        p8: Int
    ) {
        view?.let {
            when (it.id) {
                R.id.view1, R.id.view4 -> it.pivotX = it.width.toFloat()
                R.id.view3 -> view.pivotX = 0f
            }
            it.pivotY = it.height / 2f
        }
    }

    companion object {
        const val MAX_ROTATION = 60
        const val ANIM_DURATION = 1500L
        const val HOLD_ANIM_DURATION = 150L
    }
}