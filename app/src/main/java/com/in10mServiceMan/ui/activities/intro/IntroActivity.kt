package com.in10mServiceMan.ui.activities.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.in10mServiceMan.R
import android.os.Build
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Html
import android.widget.TextView
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.enter_mobile_no.EnterPhoneNumberActivity
import com.in10mServiceMan.ui.activities.sign_in.LoginActivity
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.intro_first_screen.*


class IntroActivity : In10mBaseActivity() {


    //private val dots: Array<TextView>? = null
//     var viewPager: ViewPager? = findViewById(R.id.view_pager)
    //val numbers: IntArray = intArrayOf();
    var number = IntArray(3)
    //    val viewPager:ViewPager = intro_view_pager;
    val layouts: IntArray = intArrayOf(R.layout.intro_first_screen, R.layout.intro_second_screen, R.layout.intro_third_screen)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        addBottomDots(0)

        val myViewPagerAdapter = MyViewPagerAdapter()
        intro_view_pager.adapter = myViewPagerAdapter
        // val numbers: IntArray = intArrayOf(R.layout.intro_first_screen,R.layout.intro_second_screen,R.layout.intro_third_screen);

        btn_next.setOnClickListener {
            localStorage(this@IntroActivity).introDone = true
            startActivity(Intent(this, LoginActivity::class.java))//EnterPhoneNumberActivity
        }
        button.setOnClickListener {
            localStorage(this@IntroActivity).introDone = true
            startActivity(Intent(this, LoginActivity::class.java))//EnterPhoneNumberActivity
            finish()
        }



        intro_view_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


            }

            override fun onPageSelected(position: Int) {
                addBottomDots(position)
                if (position == 0) {
                    button_close.setOnClickListener {
                        finish()
                    }
                }
                if (position == 2) {
                    btn_next.visibility = View.INVISIBLE
                } else
                    btn_next.visibility = View.INVISIBLE

            }
        })
    }
    //  viewpager change listener


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

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(layouts.size)


        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        layoutDots.removeAllViews()
        for (i in 0 until dots.size) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35.0f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            layoutDots.addView(dots[i])
        }

        if (dots.isNotEmpty())
            dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    fun showBtn(view: View) {
        view.visibility = if (view.visibility == View.INVISIBLE) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    fun hideBtn(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }
}

/**
 * View pager adapter

public class MyViewPagerAdapter : PagerAdapter()
{


override fun getCount(): Int {

return num
//TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

override fun isViewFromObject(view: View, `object`: Any): Boolean {
return view == `object`
// TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

override fun instantiateItem(container: ViewGroup, position: Int): Any {

// var layoutInflater: LayoutInflater
var view:View

//layoutInflater =
//view = LayoutInflater.from(con)(layout[position],container,false);

val view = LayoutInflater.from().inflate(intArrayOf()[position],container,false)
view.tvErrorTitle.text = "test"

container.addView(view)
return view;
// return super.instantiateItem(container, position)
}

}*/
