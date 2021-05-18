package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_camera_layout.*
import java.io.ByteArrayOutputStream

class CameraActivity: AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera_layout);
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.imgbtn_takePhoto->{
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1002);
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            val photo = data?.extras?.get("data")  as Bitmap;
            val stream = ByteArrayOutputStream();

            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            imgPhoto!!.setImageBitmap(photo);
        }
    }
}