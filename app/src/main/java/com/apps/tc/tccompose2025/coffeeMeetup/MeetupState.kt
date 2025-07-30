package com.apps.tc.tccompose2025.coffeeMeetup

import com.apps.tc.tccompose2025.models.MeetupData
import com.apps.tc.tccompose2025.models.Team
import com.apps.tc.tccompose2025.models.Venue

sealed class MeetupState {
    object Loading: MeetupState()
    data class MeetingData(val data: MeetupData): MeetupState()
    //data class TeamView(val teamData: List<Team>): MeetupState()
    data class Empty(val noData: String): MeetupState()
    object Error: MeetupState()
}