package com.eakurnikov.instrsample.actions.swipe

import android.view.View
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralLocation

enum class ElementTranslation : CoordinatesProvider {

    BORDER_UP {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.BOTTOM_CENTER
        override val dx: Float = 0f
        override val dy: Float = -EDGE_FUZZ_FACTOR
    },
    BORDER_RIGHT {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.CENTER_LEFT
        override val dx: Float = EDGE_FUZZ_FACTOR
        override val dy: Float = 0f
    },
    BORDER_DOWN {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.TOP_CENTER
        override val dx: Float = 0f
        override val dy: Float = EDGE_FUZZ_FACTOR
    },
    BORDER_LEFT {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.CENTER_RIGHT
        override val dx: Float = -EDGE_FUZZ_FACTOR
        override val dy: Float = 0f
    },
    CENTER_UP {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.CENTER
        override val dx: Float = 0f
        override val dy: Float = -EDGE_FUZZ_FACTOR
    },
    CENTER_RIGHT {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.CENTER
        override val dx: Float = EDGE_FUZZ_FACTOR
        override val dy: Float = 0f
    },
    CENTER_DOWN {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.CENTER
        override val dx: Float = 0f
        override val dy: Float = EDGE_FUZZ_FACTOR
    },
    CENTER_LEFT {
        override val coordinatesProvider: CoordinatesProvider = GeneralLocation.CENTER
        override val dx: Float = -EDGE_FUZZ_FACTOR
        override val dy: Float = 0f
    };

    /**
     * The distance of a swipe's start position from the view's edge, in terms of the view's length.
     * We do not start the swipe exactly on the view's edge, but somewhat more inward, since swiping
     * from the exact edge may behave in an unexpected way (e.g. may open a navigation drawer).
     */
    protected val EDGE_FUZZ_FACTOR: Float = 0.083f

    protected abstract val coordinatesProvider: CoordinatesProvider
    protected abstract val dx: Float
    protected abstract val dy: Float

    override fun calculateCoordinates(view: View): FloatArray =
        coordinatesProvider.calculateCoordinates(view).also {
            it[0] += dx * view.width
            it[1] += dy * view.height
        }
}
