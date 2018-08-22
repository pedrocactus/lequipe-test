package fr.pedocactus.lequipetest.repository.network

import fr.pedocactus.lequipetest.repository.network.models.StreamsWrapperJSON
import io.reactivex.Single
import retrofit2.http.GET


interface VideosApi {


    @GET("/amandychev/lequipe-test/raw/4c23e6fbeb841a7501dfacea2f76e6b1443b201e/video-flux.json")
    fun fetchVideoStreams() : Single<StreamsWrapperJSON>
}