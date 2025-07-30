package com.apps.tc.tccompose2025

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.apps.tc.tccompose2025.dialog.ListDialog
import com.apps.tc.tccompose2025.widgets.loadTodayData

@Composable
fun RasiSelectionDialog(
    app: App,
    onRasiSelected: (Int) -> Unit
) {
    val rasis = listOf(
        "மேஷம்", "ரிஷபம்", "மிதுனம்", "கடகம்",
        "சிம்மம்", "கன்னி", "துலாம்", "விருச்சிகம்",
        "தனுசு", "மகரம்", "கும்பம்", "மீனம்"
    )

    var showDialog by remember { mutableStateOf(true) }

    ListDialog(
        titles = rasis,
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onReturn = {
            showDialog = false
            onRasiSelected(it)
        }
    )
}