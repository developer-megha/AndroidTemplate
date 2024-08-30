package com.android.template.others

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ImageHelper {

    private var sizeString = ""
    var authority = "com.android.template.fileprovider"

    /** create image temporary file and return the file */
    fun createImageFile(context : Context) : File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        return image
    }

    /** Convert the image URI to File and return that file */
    fun uriToFile(context : Context, uri : Uri) : File? {
        context.contentResolver.openInputStream(uri)?.let { inputStream ->
            val tempFile : File = createImageFile(context)
            val fileOutputStream = FileOutputStream(tempFile)

            inputStream.copyTo(fileOutputStream)
            inputStream.close()
            fileOutputStream.close()

            return tempFile
        }
        return null
    }

    /** Compress the image to the provided Target MB */
    fun compressImage(filePath: String, targetMB: Double) : Double{

        var image: Bitmap = BitmapFactory.decodeFile(filePath)

        val exif = ExifInterface(filePath)
        val exifOrientation : Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
        )

        val exifDegree: Int = exifOrientationToDegree(exifOrientation)

        image = rotateImage(image, exifDegree.toFloat())

        try {
            val fileSizeInMB = getFileSizeInMB(filePath)

            sizeString += "Before Size: ${String.format("%.2f", getFileSizeInMB(filePath))}"

            var quality = 100
            if(fileSizeInMB > targetMB){
                quality = ((targetMB/fileSizeInMB) * 100).toInt()
                val fileOutputStream = FileOutputStream(filePath)
                image.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
                fileOutputStream.close()

                //imageSize = getFileSizeInMB(filePath)
                sizeString += " After Size: ${String.format("%.2f", getFileSizeInMB(filePath))}"
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }

        return getFileSizeInMB(filePath)
    }

    /**
     * ExifInterface is used in writing the image information when the picture is taken.
     * It is also helpful to handle orientation while saving the image to phone storage.
     * */
    private fun exifOrientationToDegree(exifOrientation: Int): Int{
        return when(exifOrientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
    }

    /** rotate the image if needed */
    private fun rotateImage(source: Bitmap, angle : Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)

        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    /** Return the image size  in MB */
    private fun getFileSizeInMB(filePath: String): Double{
        val file = File(filePath)
        val length = file.length()
        val fileSizeInKB = (length/1024).toString().toDouble()

        return (fileSizeInKB/1024).toString().toDouble()
    }

}