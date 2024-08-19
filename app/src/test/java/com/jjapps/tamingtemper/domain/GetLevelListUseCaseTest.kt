package com.jjapps.tamingtemper.domain

import android.graphics.Bitmap
import com.jjapps.tamingtemper.data.local.model.LevelDataResponse
import com.jjapps.tamingtemper.domain.abstraction.Repository
import com.jjapps.tamingtemper.domain.model.Level
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class GetLevelListUseCaseTest {

    @Mock
    lateinit var repository: Repository

    lateinit var getLevelListUseCase: GetLevelListUseCase

    lateinit var generateThumbnailUseCase: GenerateThumbnailUseCase

    val mockBitmap: Bitmap = mock(Bitmap::class.java)

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        getLevelListUseCase = GetLevelListUseCase(repository)

        generateThumbnailUseCase = GenerateThumbnailUseCase(repository)
    }

    @Test
    fun `Get valid level list from json file` () = runBlocking {

        Mockito.`when`(repository.getAllLevelsWithActivities())
            .thenReturn(listOf(Level(
                description = "",
                level = "",
                state = "",
                title = "Test Title",
                activities = emptyList()
            )))

        val result = getLevelListUseCase.execute()
        Mockito.verify(repository).getAllLevelsWithActivities()

        Mockito.verifyNoMoreInteractions(repository)

        assertEquals(result[0].title, "Test Title")

    }

    @Test
    fun `Get valid generated bitmap` () = runBlocking {

        Mockito.`when`(repository.generatePdfThumbnailUseCase("pdf_url"))
            .thenReturn(mockBitmap)

        val result = repository.generatePdfThumbnailUseCase("pdf_url")

        assertEquals(mockBitmap, result)

    }
}