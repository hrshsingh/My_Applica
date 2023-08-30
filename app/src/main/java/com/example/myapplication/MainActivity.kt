package com.example.myapplication

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    private var torchState : Boolean = false
    private lateinit var cameraManager: CameraManager
    private var camId : String = "0"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        cameraManager = getSystemService(Context.CAMERA_SERVICE)as CameraManager
        camId = cameraManager.cameraIdList[0]

        Dexter.withContext(this).withPermission(android.Manifest.permission.CAMERA).withListener(object  : PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                tuenOnFlashlight()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(this@MainActivity,"please grant permission",Toast.LENGTH_SHORT).show()

            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
                TODO("Not yet implemented")
            }

        }).check()
    }

    private fun tuenOnFlashlight() {
        binding.flash.setOnClickListener {
            when(torchState){
                false->{
                    cameraManager.setTorchMode(camId,true)
                    binding.flash.textOn
                    torchState =true
                }
                true->{
                    cameraManager.setTorchMode(camId,false)
                    binding.flash.textOff
                    torchState = false
                }
            }
        }


    }
}