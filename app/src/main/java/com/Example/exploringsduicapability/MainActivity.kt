package com.Example.exploringsduicapability

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.Example.exploringsduicapability.presentation.MainViewModel
import com.Example.exploringsduicapability.presentation.Screens.CameraScreen
import com.Example.exploringsduicapability.ui.theme.ExploringSDUICapabilityTheme
import com.Example.exploringsduicapability.util.pesmission.hasPerm
import java.io.File


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermission()){
            ActivityCompat.requestPermissions(
                this, cameraX_permission,0
            )
        }
        val viewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContent {
            ExploringSDUICapabilityTheme {
                CameraScreen(viewModel)
            }
        }
    }
    var recording : Recording?=null
    private fun hasPermission():Boolean{

        val permission =  cameraX_permission.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
//        viewModel.permission.value = permission
        hasPerm.value =permission
        return permission
    }

    companion object{
        private val cameraX_permission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }

    @SuppressLint("MissingPermission")
    fun videoRecord(controller: LifecycleCameraController){

        if(recording!=null){
            recording?.stop()
            recording = null
            return
        }
        if (!hasPermission()){
            return
        }
        val outputFile = File(filesDir,"my_recording.mp4")
        recording = controller.startRecording(
            FileOutputOptions.Builder(outputFile).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(applicationContext)
        ){ event->
            when(event){
              is  VideoRecordEvent.Finalize ->{
                    if (event.hasError()){
                        recording?.close()
                        recording =null
                        Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }
    }
}


