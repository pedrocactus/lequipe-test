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
import fr.pedocactus.lequipetest.repository.VideoRepositoryImpl
import fr.pedocactus.lequipetest.ui.presentation.VideoFeedPresenter
import fr.pedocactus.lequipetest.ui.presentation.VideoFeedPresenterContract
import fr.pedocactus.lequipetest.ui.presentation.VideoPresentationModel
import kotlinx.android.synthetic.main.video_fragment_layout.*


/**
 * Fragment displaying the video feed
 */
class VideoFeedFragment : Fragment(),
        VideoFeedPresenterContract.View,
        SwipeRefreshLayout.OnRefreshListener {


    lateinit var videoAdapter: VideoAdapter

    lateinit var videoFeedPresenter: VideoFeedPresenterContract.Presenter

    val DATA_CHILD = 0
    val GENERIC_ERROR_CHILD = 1
    val NETWORK_ERROR_CHILD = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        videoFeedPresenter = VideoFeedPresenter(VideoRepositoryImpl, this, VideoFeedPresenter.VideoPresenterType.valueOf(arguments?.getString(FEED_TYPE)
                ?: ""))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.video_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        //For first fragment in viewpager setUserVisibleHint gets called before onViewCreated
        if (userVisibleHint) {
            videoFeedPresenter.fetchVideos(true)
        }

    }

    private fun initView() {
        video_list.layoutManager = LinearLayoutManager(activity)
        videoAdapter = VideoAdapter(activity)
        video_list.adapter = videoAdapter
        video_list.setHasFixedSize(true)
        swipe_container.setOnRefreshListener(this)
        val itemDecor = DividerItemDecoration(activity, HORIZONTAL)
        video_list.addItemDecoration(itemDecor)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            videoFeedPresenter.fetchVideos(true)
        }
    }

    override fun onStart() {
        super.onStart()
        videoFeedPresenter?.fetchVideos(false)
    }

    override fun onStop() {
        videoFeedPresenter?.stop()
        super.onStop()
    }


    override fun onRefresh() {
        videoFeedPresenter?.fetchVideos(true)
    }

    override fun showVideos(value: List<VideoPresentationModel>) {
        viewFlipper.displayedChild = DATA_CHILD
        videoAdapter.populateData(value)
    }

    override fun showGenericError() {
        viewFlipper.displayedChild = GENERIC_ERROR_CHILD
    }

    override fun showNetworkError() {
        viewFlipper.displayedChild = NETWORK_ERROR_CHILD

    }

    override fun showProgressView() {
        swipe_container.isRefreshing = true
    }

    override fun hideProgressView() {
        swipe_container.isRefreshing = false
    }

    companion object {

        private val FEED_TYPE = "feed_type"

        fun newInstance(feedType: VideoFeedPresenter.VideoPresenterType): VideoFeedFragment {
            val fragment = VideoFeedFragment()
            val args = Bundle()
            args.putString(FEED_TYPE, feedType.name)
            fragment.arguments = args
            return fragment
        }
    }

}