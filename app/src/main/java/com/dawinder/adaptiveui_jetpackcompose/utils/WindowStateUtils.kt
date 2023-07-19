package com.dawinder.adaptiveui_jetpackcompose.utils

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Sealed interface representing different device postures.
 */
sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(val hingePosition: Rect) : DevicePosture

    data class Separating(val hingePosition: Rect, var orientation: FoldingFeature.Orientation) :
        DevicePosture
}

/**
 * Checks if the given folding feature represents a book posture.
 *
 * @param foldFeature The folding feature to check.
 * @return `true` if the folding feature represents a book posture, `false` otherwise.
 */
@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

/**
 * Checks if the given folding feature represents a separating posture.
 *
 * @param foldFeature The folding feature to check.
 * @return `true` if the folding feature represents a separating posture, `false` otherwise.
 */
@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}