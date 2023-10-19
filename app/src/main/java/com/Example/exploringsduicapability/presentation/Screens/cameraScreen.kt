package com.Example.exploringsduicapability.presentation.Screens


import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Example.exploringsduicapability.presentation.MainViewModel
import com.Example.exploringsduicapability.presentation.components.CameraPreview
import com.Example.exploringsduicapability.presentation.components.PhotoBottomsheetContent
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val controller = remember { LifecycleCameraController(context) }.apply {
        setEnabledUseCases(
            CameraController.IMAGE_CAPTURE or
        CameraController.VIDEO_CAPTURE
        )
    }
    val scaffoldState = rememberBottomSheetScaffoldState()

//    val viewModel = viewModel<MainViewModel>()
    val bitMaps by viewModel.bitMaps.collectAsState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 4.dp,
        sheetContent = {
            PhotoBottomsheetContent(
                bitmaps = bitMaps,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = {
                    controller.cameraSelector = when (controller.cameraSelector) {
                        CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
                        else -> CameraSelector.DEFAULT_BACK_CAMERA
                    }
                    controller.imageCaptureFlashMode =1
                },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red)
            ) {
                Icon(
                    Icons.Default.Cameraswitch,
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }) {
                    Icon(imageVector = Icons.Default.Photo, contentDescription = "Open Gallery")
                }

                IconButton(onClick = {
                    viewModel.takePhoto(controller = controller, onPhotoTaken = viewModel::onTakePhoto, applicationContext = context)
                }) {
                    Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "take Photo")
                }
                IconButton(onClick = {
                    viewModel.videoRecord(controller = controller, context = context)
                }) {
                    Icon(imageVector = Icons.Default.VideoCameraBack, contentDescription = "take video")
                }


            }
        }
    }

}