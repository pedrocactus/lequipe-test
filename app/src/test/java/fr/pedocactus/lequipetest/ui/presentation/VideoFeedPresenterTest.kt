package fr.pedocactus.lequipetest.ui.presentation

import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.verify
import fr.pedocactus.lequipetest.domain.VideoInfo
import fr.pedocactus.lequipetest.repository.VideoRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import rule.RxImmediateSchedulerRule
import java.io.IOException


class VideoFeedPresenterTest {


    lateinit var lastVideoPresenter: VideoFeedPresenter

    @Mock
    lateinit var view: VideoFeedPresenterContract.View

    @Mock
    lateinit var repository: VideoRepository

    @Captor
    lateinit var captor: ArgumentCaptor<List<VideoPresentationModel>>


    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        lastVideoPresenter = VideoFeedPresenter(repository, view,VideoFeedPresenter.VideoPresenterType.NEW_VIDEOS)
    }

    @Test
    fun fetchLastVideosSucceeded() {

        val video1 = VideoInfo("deux étoiles", "url1", "foot")
        val video2 = VideoInfo("swell", "url2", "surf")


        val videos = arrayListOf(video1, video2)
        given(repository.fetchNewVideos()).willReturn(Single.just(videos))

        //WHEN
        lastVideoPresenter.fetchVideos(true)

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
        given(repository.fetchNewVideos()).willReturn(Single.error(IOException()))

        //WHEN
        lastVideoPresenter.fetchVideos(true)

        //THEN
        verify(view).hideProgressView()
        verify(view).showNetworkError()
    }

    @Test
    fun fetchLastVideosGenericError() {
        given(repository.fetchNewVideos()).willReturn(Single.error(Exception()))

        //WHEN
        lastVideoPresenter.fetchVideos(true)

        //THEN
        verify(view).hideProgressView()
        verify(view).showGenericError()

    }


}