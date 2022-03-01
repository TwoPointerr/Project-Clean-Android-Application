package com.harshad.projectclean

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.harshad.projectclean.databinding.ActivityCreatePostBinding


class CreatePost : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding

    val items = arrayOf("Option 1", "Option 2", "Option 3")
    lateinit var actv :AutoCompleteTextView
    lateinit var  adapterItems : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        actv = binding.dropMenu

        adapterItems = ArrayAdapter<String>(this,R.layout.list_item,items)
        actv.setAdapter(adapterItems)

        actv.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext, "Item: $item", Toast.LENGTH_SHORT).show()
        })



        ImagePicker.with(this).galleryMimeTypes(arrayOf("image/*"))
            .compress(1024)
            .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            ).start()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== ImagePicker.REQUEST_CODE) {


//            binding.ivGriImg.setImageBitmap()
            binding.ivGriImg.setImageURI(data?.data)

            Log.d("Asach","${data?.data}")
//            var s1= data?.data?.toFile()
//            Log.d("Bdach",s1.toString())

        }

    }
}