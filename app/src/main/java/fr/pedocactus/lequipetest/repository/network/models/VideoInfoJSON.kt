package fr.pedocactus.lequipetest.repository.network.models

import com.google.gson.annotations.SerializedName
import fr.pedocactus.lequipetest.domain.VideoInfo


data class VideoInfoJSON(@SerializedName("titre") val title: String, @SerializedName("sport") val sport: SportJSON, @SerializedName("image") val image: ImageInfoJSON) {

    fun translateTo(): VideoInfo {
        return VideoInfo(title, image.url,sport.name)
    }
}