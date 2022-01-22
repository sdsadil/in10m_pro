package com.in10mServiceMan.ui.activities.select_language

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.in10mServiceMan.R
import com.in10mServiceMan.db.Dummy
import com.in10mServiceMan.db.LanguageItem
import com.in10mServiceMan.ui.activities.intro.IntroActivity
import com.in10mServiceMan.ui.activities.profile.ProfileActivity
import com.in10mServiceMan.ui.activities.services.ServicesActivity
import com.in10mServiceMan.ui.activities.sub_services.SubServicesActivity
import com.in10mServiceMan.ui.base.In10mBaseActivity
import kotlinx.android.synthetic.main.activity_select_language.*

class SelectLanguage : In10mBaseActivity(),LanguagesAdapter.SelectedLanguageCallback {
    override fun ChoosenLanguage(item: LanguageItem) {
        selectedLanguage=item
    }

    var selectedLanguage=Dummy.getLanguagesList().get(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        setLanguageList()
        proceedImageView.setOnClickListener{

           // Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show()
            // save selected language in db
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
    }
    private fun setLanguageList() {
        val ListRV=findViewById<RecyclerView>(R.id.languagesRV)
        ListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ListRV.adapter = LanguagesAdapter(this,this)
    }
}
