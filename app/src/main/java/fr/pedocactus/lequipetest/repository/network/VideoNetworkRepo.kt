package fr.pedocactus.lequipetest.repository.network

import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.network.models.StreamsWrapperJSON
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



class VideoNetworkRepo {
    private var videosApi: VideosApi

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
    }


    fun fetchNewVideos(): Observable<List<VideoInfo>> {
        return fetchVideos().map { t: StreamsWrapperJSON -> translateTo(t) }
    }

    fun fetchTopVideos(): Observable<List<VideoInfo>> {
        return fetchVideos().map { t: StreamsWrapperJSON -> translateTo(t) }
    }

    private fun fetchVideos(): Observable<StreamsWrapperJSON> {
        return videosApi.fetchVideoStreams().share()
    }

    //TODO WIP
    private fun translateTo(t: StreamsWrapperJSON): List<VideoInfo> {
        var videoInfoList = arrayListOf<VideoInfo>()
        for (stream in t.streams) {
            for (video in stream.flux.videos) {
                videoInfoList.add(video.videoInfo.translateTo())
            }
        }
        return videoInfoList
    }
}
