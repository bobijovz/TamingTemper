package com.jjapps.tamingtemper.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjapps.tamingtemper.data.local.model.LevelData
import com.jjapps.tamingtemper.domain.GetLevelList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor(private val getLevelList: GetLevelList) : ViewModel(){
    private val dashboardMutableState = MutableStateFlow<List<LevelData.Level?>>(emptyList())
    val dashboardState : StateFlow<List<LevelData.Level?>>
        get() = dashboardMutableState


    fun getLevelList()
    {
        viewModelScope.launch {
            var result = getLevelList.execute()
            dashboardMutableState.emit(result)
        }
    }
}