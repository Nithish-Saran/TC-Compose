package com.apps.tc.tccompose2025.coffeeMeetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.MeetupData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MeetupViewModel : ViewModel() {
    private val _meetupState = MutableStateFlow<MeetupState>(MeetupState.Loading)
    val meetupState: StateFlow<MeetupState> = _meetupState.asStateFlow()

    fun getVenueData(context: App) {
        _meetupState.value = MeetupState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val data = loadJsonFromAssets(context, "coffee_meet.json")
            JSONObject(data).let { MeetupData.serialize(it) }?.also {
                _meetupState.value = MeetupState.MeetingData(it)
            }?: run { _meetupState.value = MeetupState.Empty("No data found") }
        }
    }
}