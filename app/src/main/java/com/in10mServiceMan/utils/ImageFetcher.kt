package com.in10mServiceMan.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.in10mServiceMan.utils.ImageRotation.rotateImageIfRequired
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rohit on 05/10/17.
 */
/**
 * Created by Rohit on 11/07/17.
 */
class ImageFetcher {

    /**
     * Used to save the path of images as an array
     */
    private val imageItemUploads = ArrayList<String>()

    /**
     * Used to save the path of current image
     */
    private var mCurrentPhotoPath: String? = null

    /**
     * Font to be shown in dialogs
     */
    var typeface: Typeface? = null

    var boldTypeface: Typeface? = null

    /**
     * The resource ID of the theme against which to inflate image chooser dialog
     * Default value is zero
     */
    private var themeResId = 0

    /**
     * Request code for choosing from gallery
     */
    val PICK_IMAGE = 101

    /**
     * Request code for taking photo using camera
     */
    val TAKE_PHOTO = 102

    private var RESULT_OK = 0

    private var REQUEST_CODE = 111

    private var onImageAddedCallback: OnImageAddedCallback? = null

    /**
     * @param bm
     * *
     * @return returns resized bitmap for upload
     */
    private fun getScaledBitmap(bm: Bitmap): Bitmap {
        val nh = (bm.height * (512.0 / bm.width)).toInt()
        val scaled = Bitmap.createScaledBitmap(bm, 512, nh, true)
        return scaled
    }


    /**
     * Used to apply Font [typeface] to Image Chooser Dialog
     */

//    private inner class CustomSpannableString(source: CharSequence) : SpannableString(source) {
//
//        init {
//            if (typeface != null) {
//                super.setSpan(CustomSpan(typeface!!),
//                        0, source.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            }
//        }
//    }

//    private inner class CustomSpannableStringBold(source: CharSequence) : SpannableString(source) {
//
//        init {
//            if (boldTypeface != null) {
//                super.setSpan(CustomSpan(boldTypeface!!),
//                        0, source.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            }
//        }
//    }


    /**
     * Used to apply Font [typeface] to Image Chooser Dialog
     */

//    private inner class CustomSpan(private val typeface: Typeface) : TypefaceSpan("") {
//
//        override fun updateDrawState(ds: TextPaint) {
//            applyTypeFace(ds, typeface)
//        }
//
//        override fun updateMeasureState(paint: TextPaint) {
//            applyTypeFace(paint, typeface)
//        }
//
//        private fun applyTypeFace(paint: Paint, tf: Typeface) {
//            paint.typeface = tf
//        }
//    }


    fun setThemeResId(themeResId: Int) {
        this.themeResId = themeResId
    }


    /**
     * @param mActivity when coupled with activity
     */

    fun showSelectImageDialog(mActivity: Activity, onImageAddedCallback: OnImageAddedCallback, REQUEST_CODE: Int = this.REQUEST_CODE) {

        RESULT_OK = -1

        this.REQUEST_CODE = REQUEST_CODE

        this.onImageAddedCallback = onImageAddedCallback


        val items = arrayOf<CharSequence>(("Take Photo"), ("Choose from Library"), ("Cancel"))

        val builder = AlertDialog.Builder(mActivity, themeResId)
        builder.setTitle(("Add Photo!"))
        builder.setItems(items) { dialog, item ->
            if (items[item].toString().equals("Take Photo", ignoreCase = true)) {
                dispatchTakePictureIntent(mActivity)
            } else if (items[item].toString().equals("Choose from Library", ignoreCase = true)) {
                val galleryIntent = Intent()
                // Show only images, no videos or anything else
                galleryIntent.type = "image/*"
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                // Always show the chooser (if there are multiple options available)
                mActivity.startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE)
            } else if (items[item].toString().equals("Cancel", ignoreCase = true)) {
                dialog.dismiss()
            }
        }
        builder.show()
    }


    /**
     * @param mFragment when coupled with Fragment
     */

    fun showSelectImageDialog(mFragment: Fragment, onImageAddedCallback: OnImageAddedCallback,
                              REQUEST_CODE: Int = this.REQUEST_CODE) {

        RESULT_OK = -1
        this.onImageAddedCallback = onImageAddedCallback
        this.REQUEST_CODE = REQUEST_CODE

        val items = arrayOf<CharSequence>(("Take Photo"),
                ("Choose from Library"), ("Cancel"))

        val builder = AlertDialog.Builder(mFragment.activity as Activity, themeResId)
        builder.setTitle(("Add Photo!"))
        builder.setItems(items) { dialog, item ->
            if (items[item].toString().equals("Take Photo", ignoreCase = true)) {
                dispatchTakePictureIntent(mFragment)
            } else if (items[item].toString().equals("Choose from Library", ignoreCase = true)) {
                val galleryIntent = Intent()
                // Show only images, no videos or anything else
                galleryIntent.type = "image/*"
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                // Always show the chooser (if there are multiple options available)
                mFragment.startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE)
            } else if (items[item].toString().equals("Cancel", ignoreCase = true)) {
                dialog.dismiss()
            }
        }
        builder.show()
    }


    /**
     * Take photo using camera

     * @param mActivity when coupled with activity
     */

    private fun dispatchTakePictureIntent(mActivity: Activity) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mActivity.packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                print(ex.message)
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile))
                } else {
                    val uri = FileProvider.getUriForFile(mActivity,
                            in10mApplication.instance?.getPackageName() + ".provider", photoFile)

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                }
                mActivity.startActivityForResult(takePictureIntent, TAKE_PHOTO)
            }
        }
    }

    /**
     * Take photo using camera

     * @param mFragment when coupled with Fragment
     */

    private fun dispatchTakePictureIntent(mFragment: Fragment) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (mFragment.activity?.packageManager?.let { takePictureIntent.resolveActivity(it) } != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                val uri = FileProvider.getUriForFile(mFragment.activity as Activity,
                        in10mApplication.instance?.getPackageName() + ".provider",
                        photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

                // pre 24 code
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photoFile))
                mFragment.startActivityForResult(takePictureIntent, TAKE_PHOTO)
            }
        }
    }

    /**
     * @return File for saving image
     * *
     * @throws IOException
     */


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_" + Calendar.getInstance().timeInMillis

        val storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d("Wagon", "failed to create directory")
            }
        }

        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun compressBitmapToSent(bitmap: Bitmap?): String {

        var file: File? = null

        if (bitmap != null) {
            //Write file
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",Locale.US).format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_" + Calendar.getInstance().timeInMillis
            var stream: FileOutputStream? = null
            val storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES)

            if (!storageDir.exists())
                storageDir.mkdir()

            try {
                file = File.createTempFile(
                        imageFileName, /* prefix */
                        ".jpg", /* suffix */
                        storageDir      /* directory */
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                stream = FileOutputStream(file!!)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            if (stream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            }

            //Cleanup
            try {
                stream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }


        }

        if (file == null)
            return ""

        return file.absolutePath
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {

            val uri = data.data
            mCurrentPhotoPath = uri?.let { FileUtils.getPath(in10mApplication.instance!!, it) }

            val bitmap2 = decodeSampledBitmapFromFile(mCurrentPhotoPath!!, 400, 400)
          //  val bitmap=rotateImageIfRequired(bitmap2,uri)
            imageItemUploads.add(compressBitmapToSent(bitmap2))
            onImageAddedCallback?.onImageAdded(imageItemUploads[imageItemUploads.size - 1], REQUEST_CODE)

            /*Observable.fromCallable {

            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ orderItem ->
                        // set values to UI
                    }, { e ->
                        // handle exception if any
                    }, {
                        Log.i("eeeIMGLIB",mCurrentPhotoPath)

                    })*/


           /* Observable.fromCallable {
                val uri = data.data
                mCurrentPhotoPath = FileUtils.getPath(in10mApplication?.instance!!, uri)

                val bitmap2 = decodeSampledBitmapFromFile(mCurrentPhotoPath!!, 400, 400)
                val bitmap=rotateImageIfRequired(bitmap2,uri)
                return@fromCallable imageItemUploads.add(compressBitmapToSent(bitmap))
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (it!!) onImageAddedCallback?.onImageAdded(imageItemUploads[imageItemUploads.size - 1], REQUEST_CODE)

            }, {})*/

        }

        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            try {


                val bm = decodeSampledBitmapFromFile(mCurrentPhotoPath!!, 400, 400)

                //  PicassoCache.getPicassoInstance(RegisterActivity.this).load(new File(mCurrentPhotoPath)).into(profileIV);

                //   ((BitmapDrawable) profileIV.getDrawable()).getBitmap().recycle();

                val uri  = Uri.fromFile(File(mCurrentPhotoPath))
                val rImage=rotateImageIfRequired(bm,uri)

                imageItemUploads.add(compressBitmapToSent(rImage))

                onImageAddedCallback?.onImageAdded(imageItemUploads[imageItemUploads.size - 1], REQUEST_CODE)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    fun getCurrentPhotoPath(): String {

        if (imageItemUploads.size > 0)
            return imageItemUploads[imageItemUploads.size - 1]
        else
            throw RuntimeException("No Image Taken")
    }

    fun getImageItemUploads(): ArrayList<String> {
        return imageItemUploads
    }

    object FileUtils {

        @SuppressLint("NewApi")
        fun getPath(context: Context, uri: Uri): String? {

            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {

                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getDataColumn(context, contentUri!!, selection, selectionArgs)
                }// MediaProvider
                // DownloadsProvider
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.lastPathSegment

                return getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }// File
            // MediaStore (and general)

            return null
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.

         * @param context       The context.
         * *
         * @param uri           The Uri to query.
         * *
         * @param selection     (Optional) Filter used in the query.
         * *
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * *
         * @return The value of the _data column, which is typically a file path.
         */
        fun getDataColumn(context: Context, uri: Uri, selection: String?,
                          selectionArgs: Array<String>?): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                if (cursor != null)
                    cursor.close()
            }
            return null
        }


        /**
         * @param uri The Uri to check.
         * *
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * *
         * @return Whether the Uri authority is DownloadsProvider.
         */
        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * *
         * @return Whether the Uri authority is MediaProvider.
         */
        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * *
         * @return Whether the Uri authority is Google Photos.
         */
        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }

    }

    fun decodeSampledBitmapFromFile(photoPath: String,
                                    reqWidth: Int, reqHeight: Int): Bitmap {

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(photoPath, options)
    }

    fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    interface OnImageAddedCallback {

        fun onImageAdded(path: String, requestCode: Int)

    }
}