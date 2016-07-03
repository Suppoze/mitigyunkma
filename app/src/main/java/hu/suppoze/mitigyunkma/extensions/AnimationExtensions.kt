package hu.suppoze.mitigyunkma.extensions

import android.animation.Animator
import android.animation.ValueAnimator

fun ValueAnimator.onFinishedAnimation(onFinished: (Animator?) -> Unit) {
    this.addListener(object: Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) { }

        override fun onAnimationEnd(p0: Animator?) {
            onFinished(p0)
        }

        override fun onAnimationCancel(p0: Animator?) { }

        override fun onAnimationStart(p0: Animator?) { }
    })
}