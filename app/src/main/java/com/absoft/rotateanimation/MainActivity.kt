package com.absoft.rotateanimation

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.absoft.rotateanimation.databinding.ActivityMainBinding
import com.absoft.rotateanimation.extensions.dp
import com.arpitbandil.multifunctionalbutton.MultiFunctionalTouchListener
import com.arpitbandil.multifunctionalbutton.setMultiFunctionalTouchListener

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
    private var railDown = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.ivFrontUp.setMultiFunctionalTouchListener(this)
        binding.ivFrontDown.setMultiFunctionalTouchListener(this)
        binding.ivEndUp.setMultiFunctionalTouchListener(this)
        binding.ivEndDown.setMultiFunctionalTouchListener(this)
        binding.ivSideRail.setMultiFunctionalTouchListener(this)
        binding.ivRailUp.setMultiFunctionalTouchListener(this)
        binding.ivRailDown.setMultiFunctionalTouchListener(this)
        binding.ivTopCover.setMultiFunctionalTouchListener(this)
        binding.ivTopCover.addOnLayoutChangeListener(this)
        binding.ivSideRail.addOnLayoutChangeListener(this)
        binding.ivSideFrontRail.addOnLayoutChangeListener(this)
        binding.view1.addOnLayoutChangeListener(this)
        binding.view3.addOnLayoutChangeListener(this)
        binding.view4.addOnLayoutChangeListener(this)
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.iv_front_up -> {
                frontDegree =
                    if (frontDegree + 10 > MAX_FRONT_ROTATION) MAX_FRONT_ROTATION else frontDegree + 10
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                binding.ivSideRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_front_down -> {
                frontDegree = if (frontDegree - 10 < 0) 0 else frontDegree - 10
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                binding.ivSideRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_end_up -> {
                backDegree =
                    if (backDegree + 10 > MAX_BACK_ROTATION) MAX_BACK_ROTATION else backDegree + 10
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

            R.id.iv_side_rail -> {
                v.let {
                    it.pivotY = it.height.toFloat()
                    it.animate().setDuration(ANIM_DURATION)
                        .scaleY(if (it.scaleY <= 0.4f) 1f else 0.4f)
                    it.pivotY = it.height.toFloat() / 2
                }
            }

            R.id.iv_top_cover -> {
                v.animate().setDuration(ANIM_DURATION)
                    .scaleX(if (v.scaleX <= 0.2f) 1f else 0.2f)
            }

            R.id.iv_rail_up -> {
                railDown = if (railDown - 10 < 0) 0 else railDown - 10
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .translationY(railDown.toFloat())
                handleRailButtons()
            }

            R.id.iv_rail_down -> {
                railDown = if (railDown + 10 > MAX_RAIL_DOWN) MAX_RAIL_DOWN else railDown + 10
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .translationY(railDown.toFloat())
                handleRailButtons()
            }
        }
    }

    override fun onHolding(v: View?) {
        when (v?.id) {
            R.id.iv_front_up -> {
                if (frontDegree < MAX_FRONT_ROTATION) {
                    frontDegree += 1
                    binding.view1.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(frontDegree.toFloat())
                    binding.ivSideRail.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(frontDegree.toFloat())
                    binding.ivSideFrontRail.animate().setDuration(HOLD_ANIM_DURATION)
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
                    binding.ivSideRail.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(frontDegree.toFloat())
                    binding.ivSideFrontRail.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(frontDegree.toFloat())
                    binding.tvFrontDegree.animate().setDuration(HOLD_ANIM_DURATION)
                        .rotation(-frontDegree.toFloat())
                    handleFrontButtons()
                }
            }

            R.id.iv_end_up -> {
                if (backDegree < MAX_BACK_ROTATION) {
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

            R.id.iv_rail_up -> {
                if (railDown > 0) {
                    railDown -= 1
                    binding.ivSideFrontRail.animate().setDuration(HOLD_ANIM_DURATION)
                        .translationY(railDown.toFloat())
                    handleRailButtons()
                }
            }

            R.id.iv_rail_down -> {
                if (railDown < MAX_RAIL_DOWN) {
                    railDown += 1
                    binding.ivSideFrontRail.animate().setDuration(HOLD_ANIM_DURATION)
                        .translationY(railDown.toFloat())
                    handleRailButtons()
                }
            }
        }
        if (v?.id != R.id.iv_side_rail && v?.id != R.id.iv_top_cover) {
            v?.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        }
    }

    override fun onDoubleClick(v: View?) {
        when (v?.id) {
            R.id.iv_front_up -> {
                frontDegree = MAX_FRONT_ROTATION
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                binding.ivSideRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_front_down -> {
                frontDegree = 0
                binding.view1.animate().setDuration(ANIM_DURATION).rotation(frontDegree.toFloat())
                binding.ivSideRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .rotation(frontDegree.toFloat())
                handleFrontButtons()
            }

            R.id.iv_end_up -> {
                backDegree = MAX_BACK_ROTATION
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

            R.id.iv_rail_up -> {
                railDown = 0
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .translationY(railDown.toFloat())
                handleRailButtons()
            }

            R.id.iv_rail_down -> {
                railDown = MAX_RAIL_DOWN
                binding.ivSideFrontRail.animate().setDuration(ANIM_DURATION)
                    .translationY(railDown.toFloat())
                handleRailButtons()
            }
        }
    }

    override fun onHoldStart(v: View?) {
        if (v?.id == R.id.iv_front_up) {
            binding.tvFrontDegree.visibility = View.VISIBLE
            binding.tvFrontDegree.animate().setDuration(ANIM_DURATION).alpha(1f)
        }
    }

    override fun onHoldEnd(v: View?) {
        if (v?.id == R.id.iv_front_up) {
            binding.tvFrontDegree.animate().setDuration(ANIM_DURATION).alpha(0f)
        }
        super.onHoldEnd(v)
    }

    private fun handleRailButtons() {
        binding.ivRailUp.isEnabled = railDown > 0
        binding.ivRailDown.isEnabled = railDown < MAX_RAIL_DOWN
    }

    private fun handleBackButtons() {
        binding.ivEndDown.isEnabled = backDegree > 0
        binding.ivEndUp.isEnabled = backDegree < MAX_BACK_ROTATION
    }

    private fun handleFrontButtons() {
        binding.tvFrontDegree.text = frontDegree.toString()
        binding.ivFrontDown.isEnabled = frontDegree > 0
        binding.ivFrontUp.isEnabled = frontDegree < MAX_FRONT_ROTATION
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
                R.id.view1, R.id.view4, R.id.iv_top_cover,
                R.id.iv_side_rail, R.id.iv_side_front_rail -> it.pivotX = it.width.toFloat()

                R.id.view3 -> view.pivotX = 0f
            }
            it.pivotY = it.height / 2f
        }
    }

    companion object {
        const val MAX_FRONT_ROTATION = 70
        const val MAX_BACK_ROTATION = 35
        const val MAX_RAIL_DOWN = 80
        const val ANIM_DURATION = 1500L
        const val HOLD_ANIM_DURATION = 150L
    }
}