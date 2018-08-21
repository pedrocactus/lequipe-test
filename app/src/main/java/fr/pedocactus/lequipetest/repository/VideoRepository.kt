package fr.pedocactus.lequipetest.repository

import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.network.VideoNetworkRepo
import io.reactivex.Observable

object VideoRepository {
    var videoNetworkRepo: VideoNetworkRepo = VideoNetworkRepo()


    fun fetchNewVideos(): Observable<List<VideoInfo>> {
        return videoNetworkRepo.fetchNewVideos()
    }

    //TODO WIP
    fun fetchTopVideos(): Observable<List<VideoInfo>> {
        return videoNetworkRepo.fetchTopVideos()
    }

}
