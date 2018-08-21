package fr.pedocactus.lequipetest.ui.presentation

import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.VideoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException


class VideosPresenter constructor(var videoRepository: VideoRepository, var videoView: VideosPresenterContract.View) : VideosPresenterContract.Presenter {

    lateinit var disposable: Disposable

    override fun fetchVideos() {
        disposable = videoRepository.fetchNewVideos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                    onNext = { onSucceed(it) },
                    onError =  { onError(it) },
                    onComplete = { } )
    }

    fun onSucceed(videos: List<VideoInfo>) {
        videoView.showVideos(buildViewModels(videos))
    }

    private fun buildViewModels(videos: List<VideoInfo>): List<VideoPresentationModel> {
        val viewModels = arrayListOf<VideoPresentationModel>()
        for (video in videos) {
            viewModels.add(VideoPresentationModel(video.title, video.imageUrl, video.sport))
        }
        return viewModels
    }

    fun onError(genericException: Throwable) {
        if (genericException is IOException)
            videoView.showNetworkError()
        else
            videoView.showGenericError()
    }

    override fun stop(){
        disposable.dispose()
    }
}