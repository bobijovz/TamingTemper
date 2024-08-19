package com.jjapps.tamingtemper.presentation.dashboard

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjapps.tamingtemper.domain.GenerateThumbnailUseCase
import com.jjapps.tamingtemper.domain.GetLevelListUseCase
import com.jjapps.tamingtemper.domain.model.Level
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor(
    private val getLevelListUseCase: GetLevelListUseCase,
    private val generateThumbnailUseCase: GenerateThumbnailUseCase
) : ViewModel(){
    private val dashboardMutableState = MutableStateFlow(DashboardViewState())
    val dashboardState : StateFlow<DashboardViewState>
        get() = dashboardMutableState


    fun getLevelList()
    {
        viewModelScope.launch {
            dashboardMutableState.emit(dashboardMutableState.value.copy(isLoading = true))
            val result = getLevelListUseCase.execute()
            val updatedLevels = result.map { level ->
                delay(100)
                level.copy(activities = level.activities.map { activity ->
                    val iconThumb = generateThumbnailUseCase.execute("https://${activity.icon}")
                    activity.copy(iconThumb = iconThumb)
                    //val iconLockedThumb = generateThumbnailUseCase.execute("https://${activity.lockedIcon}")
                    //activity.lockedIconThumb = iconLockedThumb
                })
            }
            dashboardMutableState.emit(DashboardViewState(isLoading = false, levels = updatedLevels))
        }
    }

}

data class DashboardViewState(val isLoading: Boolean = false, val levels: List<Level> = emptyList())