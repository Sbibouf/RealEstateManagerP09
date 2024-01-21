package com.example.realestatemanager.data.local.service

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.realestatemanager.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComposeFileProvider: FileProvider(
    R.xml.path_provider) {
        companion object {
            fun getImageUri(context: Context): Uri {
                val directory = File(context.cacheDir, "images")
                directory.mkdirs()
                val storageDir: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                if (storageDir != null && !storageDir.exists()) {
                    storageDir.mkdirs()
                }
                val file = File.createTempFile(
                    "selected_image_",
                    ".jpg",
                    storageDir,
                )
                val authority = context.packageName + ".fileprovider"
                return getUriForFile(
                    context,
                    authority,
                    file,
                )
            }
        }

}