package com.example.vibzz

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class OnboardingActivity : AppCompatActivity() {
    private lateinit var btnSkip: Button
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button
    private lateinit var viewPager: ViewPager
    private lateinit var dotIndicator: LinearLayout
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSkip = findViewById(R.id.btnSkipOnboarding)
        btnBack = findViewById(R.id.btnBackOnboarding)
        btnNext = findViewById(R.id.btnNextOnboarding)
        viewPager = findViewById(R.id.viewPager)
        dotIndicator = findViewById(R.id.dotIndicator)

        firebaseAuth = Firebase.auth

        btnSkip.setOnClickListener {
            val intent = Intent(this@OnboardingActivity, GetStartedActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnBack.setOnClickListener {
            if (getItem(0) > 0) {
                viewPager.setCurrentItem(getItem(-1), true)
            }
        }

        btnNext.setOnClickListener {
            if (getItem(0) < 2) {
                viewPager.setCurrentItem(getItem(1), true)
            } else {
                val intent = Intent(this@OnboardingActivity, GetStartedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        setDotIndicator(0)
        viewPager.addOnPageChangeListener(viewPagerListener)

    }

    private val viewPagerListener : ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {
            setDotIndicator(position)

            if (position > 0) {
                btnBack.visibility = View.VISIBLE
            } else {
                btnBack.visibility = View.INVISIBLE
            }

            if (position == 2) {
                btnNext.text = getString(R.string.finish)
            } else {
                btnNext.text = getString(R.string.next)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
        }

    }

    private fun setDotIndicator(position: Int) {
        val dots = Array(3) {TextView(this)}
        dotIndicator.removeAllViews()

        for (i in dots.indices) {
            dots[i].apply {
                text = Html.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY)
                textSize = 35f
                setTextColor(resources.getColor(R.color.grey, applicationContext.theme))
            }
            dotIndicator.addView(dots[i])
        }
        dots[position].setTextColor(resources.getColor(R.color.blue, applicationContext.theme))
    }

    private fun getItem(i: Int) : Int {
        return viewPager.currentItem + i
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}