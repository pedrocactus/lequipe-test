package fr.pedocactus.lequipetest.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import fr.pedocactus.lequipetest.R
import fr.pedocactus.lequipetest.ui.presentation.VideoFeedPresenter
import kotlinx.android.synthetic.main.main_activity_layout.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        pager.adapter = mSectionsPagerAdapter

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(pager))

    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return if (position == 0)
                VideoFeedFragment.newInstance(VideoFeedPresenter.VideoPresenterType.NEW_VIDEOS) else
                VideoFeedFragment.newInstance(VideoFeedPresenter.VideoPresenterType.TOP_VIDEOS)
        }

        override fun getCount(): Int {
            return 2
        }
    }


}
