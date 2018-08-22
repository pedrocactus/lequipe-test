package fr.pedocactus.lequipetest.repository

import fr.pedocactus.lequipetest.domain.VideoInfo
import io.reactivex.Single


interface VideoRepository {
    fun fetchTopVideos(): Single<ArrayList<VideoInfo>>
    fun fetchNewVideos(): Single<ArrayList<VideoInfo>>
}