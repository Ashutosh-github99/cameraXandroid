package com.Example.exploringsduicapability.presentation.components

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@Composable
fun CameraPreview(
    controller:LifecycleCameraController,
    modifier:Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(factory = {
        PreviewView(it).apply {
            this.controller =controller
            controller.bindToLifecycle(lifecycleOwner)
        }
    },
        modifier = modifier
    )
}
























