package fr.pedocactus.lequipetest.ui

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.pedocactus.lequipetest.R
import fr.pedocactus.lequipetest.repository.VideoRepository
import fr.pedocactus.lequipetest.ui.presentation.VideoPresentationModel
import fr.pedocactus.lequipetest.ui.presentation.VideosPresenter
import fr.pedocactus.lequipetest.ui.presentation.VideosPresenterContract
import kotlinx.android.synthetic.main.video_fragment_layout.*



/**
 * A placeholder fragment containing a simple view.
 */
class NewVideosFragment : Fragment(), VideosPresenterContract.View, SwipeRefreshLayout.OnRefreshListener {

    lateinit var videoAdapter:VideoAdapter

    //@Inject
    lateinit var videosPresenter : VideosPresenterContract.Presenter

    override fun onRefresh() {
        videosPresenter.fetchVideos()
    }

    override fun showVideos(value: List<VideoPresentationModel>) {
        swipe_container.isRefreshing = false
        videoAdapter.populateData(value)
    }

    override fun showGenericError() {
    }

    override fun showNetworkError() {
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //DaggerVideosComponent.builder().provideVideoModule(VideosModule(this)).build().inject(this)
        //DaggerVideosComponent.builder().videosModule(VideosModule(this)).build().inject(this)

        videosPresenter = VideosPresenter(VideoRepository, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.video_fragment_layout, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        video_list.layoutManager = LinearLayoutManager(activity)
        videoAdapter = VideoAdapter(activity)
        video_list.adapter = videoAdapter
        video_list.setHasFixedSize(true)
        swipe_container.setOnRefreshListener(this)
        val itemDecor = DividerItemDecoration(activity, HORIZONTAL)
        video_list.addItemDecoration(itemDecor)

        swipe_container.isRefreshing = true
        videosPresenter.fetchVideos()
    }


    override fun onStop() {
        videosPresenter.stop()
        super.onStop()
    }


    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): NewVideosFragment {
            val fragment = NewVideosFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}