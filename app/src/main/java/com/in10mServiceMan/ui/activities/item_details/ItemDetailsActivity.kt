package com.in10mServiceMan.ui.activities.item_details

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat

import com.in10mServiceMan.R
import com.in10mServiceMan.db.Dummy
import com.in10mServiceMan.ui.activities.home.HomeActivity
import com.in10mServiceMan.utils.ImageFetcher
import kotlinx.android.synthetic.main.activity_item_details.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class ItemDetailsActivity : AppCompatActivity(), ImageFetcher.OnImageAddedCallback {

    var permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var permsRequestCode = 1

    override fun onImageAdded(path: String, requestCode: Int) {
        valueImagesAdapter.imageList.add(path)
        valueImagesAdapter.notifyDataSetChanged()
    }

    val imageFetcher = ImageFetcher()
    lateinit var valueImagesAdapter: ValueImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permsRequestCode)
        }
        initToolbar()

        initRV()

        confirmCL.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        cameraIV.setOnClickListener {

            getValueImage()
        }

        imageFetcher.typeface = ResourcesCompat.getFont(this, R.font.diavlo_light)
        imageFetcher.boldTypeface = ResourcesCompat.getFont(this, R.font.diavlo_bold)
    }


    private fun getValueImage() {
        imageFetcher.showSelectImageDialog(this, this)
    }

    private fun initRV() {

        itemSizeRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        itemSizeRV.adapter = ItemAdapter()

        handleOptionsRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        handleOptionsRV.adapter = ItemAdapter(Dummy.getHandleOptionsList(), -1)

        imageRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        valueImagesAdapter = ValueImagesAdapter(this)
        imageRV.adapter = valueImagesAdapter
    }

    private fun initToolbar() {
        setSupportActionBar(appBar as Toolbar)
        titleTV.text = getString(R.string.item_details)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        imageFetcher.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }
}
