package fr.pedocactus.lequipetest.repository.network.models

import com.google.gson.annotations.SerializedName

data class StreamsWrapperJSON(@SerializedName("onglets") val streams : List<StreamJSON>)
