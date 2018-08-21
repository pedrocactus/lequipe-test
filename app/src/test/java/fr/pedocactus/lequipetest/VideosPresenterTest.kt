package fr.pedocactus.lequipetest

import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.verify
import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.VideoRepository
import fr.pedocactus.lequipetest.ui.presentation.VideoPresentationModel
import fr.pedocactus.lequipetest.ui.presentation.VideosPresenter
import fr.pedocactus.lequipetest.ui.presentation.VideosPresenterContract
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.IOException

class VideosPresenterTest {


    lateinit var lastVideoPresenter: VideosPresenter

    @Mock
    lateinit var view: VideosPresenterContract.View

    @Captor
    lateinit var captor: ArgumentCaptor<List<VideoPresentationModel>>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        lastVideoPresenter = VideosPresenter(VideoRepository, view)
    }

    @Test
    fun fetchLastVideosSucceeded() {

        val video1 = VideoInfo("deux étoiles", "url1", "foot")
        val video2 = VideoInfo("swell", "url2", "surf")
        val videos = arrayListOf(video1, video2)

        //WHEN
        lastVideoPresenter.onSucceed(videos)

        //THEN
        verify(view).showVideos(capture(captor))
        assert(captor.value.size == 2)
        //testing transltation between data model and presentation model
        assert(captor.value.get(0).title.equals(video1.title))
        assert(captor.value.get(0).imageUrl.equals(video1.imageUrl))
        assert(captor.value.get(0).sport.equals(video1.sport))


        assert(captor.value.get(1).title.equals(video2.title))
        assert(captor.value.get(1).imageUrl.equals(video2.imageUrl))
        assert(captor.value.get(1).sport.equals(video2.sport))
    }

    @Test
    fun fetchLastVideosNoNetwork() {
        //WHEN
        lastVideoPresenter.onError(IOException())

        //THEN
        verify(view).showNetworkError()
    }

    @Test
    fun fetchLastVideosGenericError() {
        //WHEN
        lastVideoPresenter.onError(Exception())

        //THEN
        verify(view).showGenericError()
    }


}