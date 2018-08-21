package fr.pedocactus.lequipetest.repository.network.models

import com.google.gson.annotations.SerializedName


data class VideoInfoJSONWrapper(@SerializedName("objet") val videoInfo: VideoInfoJSON)

