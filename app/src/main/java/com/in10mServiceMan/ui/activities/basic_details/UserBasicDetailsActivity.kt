package com.in10mServiceMan.ui.activities.basic_details

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.in10mServiceMan.R
import com.in10mServiceMan.R.id.view_pager_user_details
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_user_basic_details.*

class UserBasicDetailsActivity : AppCompatActivity() {

    var number = IntArray(3)
    val layouts: IntArray = intArrayOf(R.layout.user_basic_details_screen,R.layout.your_service_and_experiences_screen,R.layout.upload_profile_screen);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_basic_details)

        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }


        var myViewPagerAdapter = MyViewPagerAdapter()
        view_pager_user_details.adapter = myViewPagerAdapter
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(layouts[position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}
