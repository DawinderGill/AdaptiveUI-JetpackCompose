@file:OptIn(ExperimentalWindowApi::class)

package com.dawinder.adaptiveui_jetpackcompose.utils

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.window.core.ExperimentalWindowApi
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Returns a Flow of [DevicePosture] objects representing the device's posture based on the window layout information.
 *
 * @param context The application context.
 * @param lifecycle The lifecycle object used to bound the emission of DevicePosture objects to the lifecycle events.
 * @return A Flow emitting DevicePosture objects representing the device's posture.
 */
fun WindowInfoTracker.getDevicePostureFlow(
    context: Context,
    lifecycle: Lifecycle
): Flow<DevicePosture> {
    return windowLayoutInfo(context)
        .flowWithLifecycle(lifecycle)
        .map { layoutInfo ->
            val foldingFeature =
                layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()
            when {
                isBookPosture(foldingFeature) ->
                    DevicePosture.BookPosture(foldingFeature.bounds)

                isSeparating(foldingFeature) ->
                    DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                else -> DevicePosture.NormalPosture
            }
        }
}