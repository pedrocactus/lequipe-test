package fr.pedocactus.lequipetest.repository.network.models

import com.google.gson.annotations.SerializedName


data class VideoInfoJSON(@SerializedName("titre") val title: String?,
                         @SerializedName("sport") val sport: SportJSON?,
                         @SerializedName("image") val image: ImageInfoJSON?)
