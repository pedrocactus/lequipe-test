package fr.pedocactus.lequipetest.repository.network.models

import com.google.gson.annotations.SerializedName


data class FluxJSON(@SerializedName("items") val videos : List<VideoInfoJSONWrapper>, val stat : StatJSON)