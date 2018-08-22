package fr.pedocactus.lequipetest.repository.network

import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.network.models.StreamsWrapperJSON
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Class managing networking
 */
class VideoNetworkRepo {
    private var videosApi: VideosApi
    var videoFeedTranslator : VideoFeedTranslator

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        videosApi = Retrofit.Builder().addCallAdapterFactory(
                RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .client(client)
                .baseUrl("https://bitbucket.org")
                .build().create(VideosApi::class.java)

        videoFeedTranslator = VideoFeedTranslator()
    }


    fun fetchNewVideos(): Single<ArrayList<VideoInfo>> {
        return fetchVideos().map { t: StreamsWrapperJSON -> videoFeedTranslator.translateTo(t.streams.first { streamJSON -> streamJSON.flux.stat.page == "home_videos_dernieres" }) }
    }

    fun fetchTopVideos(): Single<ArrayList<VideoInfo>> {
        return fetchVideos().map { t: StreamsWrapperJSON -> videoFeedTranslator.translateTo(t.streams.first { streamJSON -> streamJSON.flux.stat.page == "home_videos_top" }) }
    }

    private fun fetchVideos(): Single<StreamsWrapperJSON> {
        return videosApi.fetchVideoStreams()
    }

}
