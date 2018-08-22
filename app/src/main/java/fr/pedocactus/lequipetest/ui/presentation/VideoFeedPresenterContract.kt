package fr.pedocactus.lequipetest.ui.presentation


class VideoFeedPresenterContract {
    interface Presenter {
        fun fetchVideos(resfresh: Boolean)
        fun stop()
    }

    interface View {
        fun showVideos(value: List<VideoPresentationModel>)

        fun showGenericError()

        fun showNetworkError()

        fun showProgressView()

        fun hideProgressView()


    }
}