package hu.suppoze.mitigyunkma.ui.calculate

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class AnimationHelperRecyclerView : RecyclerView {

    private lateinit var listener: () -> Unit

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onAnimationEnd() {
        super.onAnimationEnd()
        listener()
    }

    fun setAnimationEndListener(listener : () -> Unit) {
        this.listener = listener
    }
}