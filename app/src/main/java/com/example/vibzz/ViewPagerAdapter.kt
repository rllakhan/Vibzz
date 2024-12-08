package com.example.vibzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(private val context: Context): PagerAdapter() {

    private val img = arrayOf(
        R.drawable.img_create_profile,
        R.drawable.img_share_photos,
        R.drawable.img_connect_with_friends
    )

    private val title = arrayOf(
        R.string.title_profile,
        R.string.title_photos,
        R.string.title_friends
    )

    private val desc = arrayOf(
        R.string.desc_profile,
        R.string.desc_photos,
        R.string.desc_friends
    )
    override fun getCount(): Int {
        return title.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_screen, container, false)

        val ivSlideImage: ImageView = view.findViewById(R.id.ivSlideImage)
        val tvSlideTitle: TextView = view.findViewById(R.id.tvSlideTitle)
        val tvSlidDesc: TextView = view.findViewById(R.id.tvSlidDescription)

        ivSlideImage.setImageResource(img[position])
        tvSlideTitle.text = context.getString(title[position])
        tvSlidDesc.text = context.getString(desc[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}