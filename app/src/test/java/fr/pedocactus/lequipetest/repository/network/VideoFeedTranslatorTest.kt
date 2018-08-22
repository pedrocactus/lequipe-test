package fr.pedocactus.lequipetest.repository.network

import fr.pedocactus.lequipetest.repository.network.models.*
import org.junit.Test

class VideoFeedTranslatorTest{


    val translator = VideoFeedTranslator()


    @Test
    fun translating_FullDatas(){
        //TODO WIP
    }

    /**
     * Testing if null titled videos is removed from list during translation
     */
    @Test
    fun translating_VideoTitleMissing(){
        //GIVEN
        var videos : ArrayList<VideoInfoJSONWrapper> = arrayListOf()
        var fullVideoInfo = VideoInfoJSON("title", SportJSON("sport1"), ImageInfoJSON("url1"))
        var missingTitleVideoInfoJSON = VideoInfoJSON(null, SportJSON("sport2"),ImageInfoJSON("url2"))
        videos.add(VideoInfoJSONWrapper(fullVideoInfo))
        videos.add(VideoInfoJSONWrapper(missingTitleVideoInfoJSON))

        var streamJSON = StreamJSON(FluxJSON(videos, StatJSON("")))


        //WHEN
        val videoResult = translator.translateTo(streamJSON)

        //THEN
        assert(videoResult.size == 1)
        assert(videoResult[0].title == "title")
        assert(videoResult[0].sport == "sport1")
        assert(videoResult[0].imageUrl == "url1")

    }

}