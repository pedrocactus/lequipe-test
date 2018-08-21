package fr.pedocactus.lequipetest.ui.presentation


class VideosPresenterContract {
    interface Presenter{
        fun fetchVideos()
        fun stop()
    }
    interface View {
        fun showVideos(value: List<VideoPresentationModel>)

        fun showGenericError()

        fun showNetworkError()

    }
}