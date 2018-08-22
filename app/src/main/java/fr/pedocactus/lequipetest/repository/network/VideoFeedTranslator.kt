package fr.pedocactus.lequipetest.repository.network

import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.network.models.StreamJSON
import fr.pedocactus.lequipetest.repository.network.models.VideoInfoJSON

/**
 * Translation utility class from deserialized network classes to data models
 */
class VideoFeedTranslator {

    fun translateTo(t: StreamJSON): ArrayList<VideoInfo> {
        var videoInfoList = arrayListOf<VideoInfo>()
        for (video in t.flux.videos) {
            var videoInfo = translateTo(video.videoInfo)
            if (videoInfo != null) videoInfoList.add(videoInfo)
        }
        return videoInfoList
    }

    fun translateTo(videoInfoJSON: VideoInfoJSON): VideoInfo? {
        return if (videoInfoJSON.title != null && videoInfoJSON.title.isNotEmpty()) VideoInfo(videoInfoJSON.title, videoInfoJSON.image?.url?:"", videoInfoJSON.sport?.name?:"") else null
    }
}