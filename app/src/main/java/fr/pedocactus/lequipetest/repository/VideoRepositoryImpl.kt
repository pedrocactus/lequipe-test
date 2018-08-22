package fr.pedocactus.lequipetest.repository

import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.network.VideoNetworkRepo
import io.reactivex.Single

object VideoRepositoryImpl : VideoRepository {
    var videoNetworkRepo: VideoNetworkRepo = VideoNetworkRepo()


    override fun fetchNewVideos(): Single<ArrayList<VideoInfo>> {
        return videoNetworkRepo.fetchNewVideos()
    }

    override fun fetchTopVideos(): Single<ArrayList<VideoInfo>> {
        return videoNetworkRepo.fetchTopVideos()
    }

}
