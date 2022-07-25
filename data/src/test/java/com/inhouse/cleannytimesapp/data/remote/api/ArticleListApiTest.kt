package com.inhouse.cleannytimesapp.data.remote.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.inhouse.cleannytimesapp.data.model.ArticleListEntity
import com.inhouse.cleannytimesapp.data.util.MockResponseFileReader
import com.inhouse.cleannytimesapp.data.util.SUCCESS_RESPONSE_FILENAME
import com.inhouse.cleannytimesapp.library.test.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleListApiTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()
    private var mockWebServer = MockWebServer()
    private lateinit var articleListApi: ArticleListApi

    @Before
    fun setUp() {
        mockWebServer.start()
        articleListApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleListApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get articles response is instance of ArticleListEntity`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader(SUCCESS_RESPONSE_FILENAME).content)

        mockWebServer.enqueue(response)

        runBlocking(coroutineTestRule.testDispatcher) {
            val articleListEntity = articleListApi.getArticleList(7, "")
            articleListEntity shouldBeInstanceOf ArticleListEntity::class
        }
    }

    @Test
    fun `get articles response has certain fixed articles`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader(SUCCESS_RESPONSE_FILENAME).content)

        mockWebServer.enqueue(response)

        runBlocking(coroutineTestRule.testDispatcher) {
            val articleListEntity = articleListApi.getArticleList(7, "")
            articleListEntity?.numResults shouldBeEqualTo 20
        }
    }
}