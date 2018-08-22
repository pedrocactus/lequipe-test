package fr.pedocactus.lequipetest.ui.presentation

import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.VideoRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException


class VideoFeedPresenter constructor(var videoRepository: VideoRepository, var videoView: VideoFeedPresenterContract.View, var videoPresenterType: VideoPresenterType) : VideoFeedPresenterContract.Presenter {


    var disposable : Disposable? = null

    var videoInfos: ArrayList<VideoInfo>? = null

    enum class VideoPresenterType {
        TOP_VIDEOS, NEW_VIDEOS
    }

    override fun fetchVideos(refresh: Boolean) {
        if (refresh) {
            videoView.showProgressView()
            disposable = getVideoFeed().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                            onSuccess = { onSucceed(it) },
                            onError = { onError(it) })

        } else if (videoInfos != null) {
            videoView.hideProgressView()
            videoView.showVideos(buildViewModels(videoInfos))

        }
    }

    fun onSucceed(videos: ArrayList<VideoInfo>?) {
        videoInfos = videos
        videoView.hideProgressView()
        videoView.showVideos(buildViewModels(videos))
    }

    fun buildViewModels(videos: List<VideoInfo>?): List<VideoPresentationModel> {
        val viewModels = arrayListOf<VideoPresentationModel>()
        if (videos != null) {
            for (video in videos) {
                viewModels.add(VideoPresentationModel(video.title, video.imageUrl, video.sport))
            }
        }
        return viewModels
    }

    fun onError(genericException: Throwable) {
        videoView.hideProgressView()
        if (genericException is IOException)
            videoView.showNetworkError()
        else
            videoView.showGenericError()
    }

    override fun stop() {
        disposable?.dispose()

    }

    fun getVideoFeed(): Single<ArrayList<VideoInfo>> {
        return when {
            videoPresenterType == VideoPresenterType.TOP_VIDEOS -> videoRepository.fetchTopVideos()
            videoPresenterType == VideoPresenterType.NEW_VIDEOS -> videoRepository.fetchNewVideos()
            else -> throw IllegalStateException()
        }
    }
}