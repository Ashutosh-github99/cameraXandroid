package com.Example.exploringsduicapability.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Example.exploringsduicapability.util.pesmission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class MainViewModel:ViewModel() {

    private val _bitmap = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitMaps = _bitmap.asStateFlow()
    var recording : Recording?=null

    fun onTakePhoto(bitmap: Bitmap){
        _bitmap.value += bitmap
    }

    fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken :(Bitmap) -> Unit,
        applicationContext:Context
    ){

        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : ImageCapture.OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    onPhotoTaken(image.toBitmap())
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("cameraxfailure","failed",exception)
                }
            }
        )
    }


    @SuppressLint("MissingPermission")
    fun videoRecord(controller: LifecycleCameraController, context: Context){

        if(recording!=null){
            recording?.stop()
            recording = null
            return
        }
        if (!pesmission.hasPerm.value){
            return
        }
        val outputFile = File(context.filesDir,"my_recording.mp4")
        recording = controller.startRecording(
            FileOutputOptions.Builder(outputFile).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(context)
        ){ event->
            when(event){
                is  VideoRecordEvent.Finalize ->{
                    if (event.hasError()){
                        recording?.close()
                        recording =null
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }
    }

}





















