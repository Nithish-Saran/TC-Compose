package com.apps.tc.tccompose2025.poojacorner

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.models.PoojaUiState
import com.apps.tc.tccompose2025.models.VirtualPoojaData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.random.Random

class PoojaViewModel : ViewModel() {
    private val _poojaState = MutableStateFlow<PoojaState>(PoojaState.Loading)
    val poojaState: StateFlow<PoojaState> = _poojaState.asStateFlow()

    // StateFlow for the dynamic UI state (PoojaUiState)
    private val _uiState = MutableStateFlow(PoojaUiState())
    val uiState: StateFlow<PoojaUiState> = _uiState.asStateFlow()

    // Internal map to hold flower animation jobs for individual cancellation
    private val _flowerAnimationJobs = mutableMapOf<Int, Job>()

    fun fetchPoojaData(context: App) {
        _poojaState.value = PoojaState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val json = loadJsonFromAssets(context, "virtualpooja/pooja.json")
            val data = JSONObject(json).let { VirtualPoojaData.serialize(it) }
            withContext(Dispatchers.Main) {
                if (data != null) {
                    _poojaState.value = PoojaState.ShowPoojaItems(data)
                    _uiState.update {
                        it.copy(
                            poojaData = data,
                            currentGodImage = data.gods.firstOrNull()?.godImage ?: "",
                            currentDecorImage = data.gods.firstOrNull()?.decorImage ?: "",
                            currentBgImage = data.bgs.firstOrNull()?.bgImage ?: ""
                        )
                    }
                } else _poojaState.value = PoojaState.Error("Something went wrong")
            }
        }
    }

    fun onGodSelected(index: Int) {
        _uiState.update { currentState ->
            currentState.poojaData?.let { data ->
                currentState.copy(
                    currentGodImage = data.gods[index].godImage,
                    currentDecorImage = data.gods[index].decorImage,
                    showGodDialog = false
                )
            } ?: currentState
        }
    }

    fun onBgSelected(index: Int) {
        _uiState.update { currentState ->
            currentState.poojaData?.let { data ->
                currentState.copy(
                    currentBgImage = data.bgs[index].bgImage,
                    showBgDialog = false
                )
            } ?: currentState
        }
    }

    fun onMusicSelected(index: Int) {
        // TODO: Implement actual music playback logic here based on data.songs[index].url
        _uiState.update { it.copy(showMusicDialog = false) }
    }

    fun onFlowerSelected(index: Int, coroutineScope: CoroutineScope) {
        _uiState.update { currentState ->
            currentState.poojaData?.let { data ->
                currentState.copy(
                    currentFlowerImage = data.flowers[index].flowerImage,
                    showFlowerDialog = false
                )
            } ?: currentState
        }
        flowerFall(coroutineScope) // Trigger flower animation after selection
    }

    fun toggleLamp() {
        _uiState.update { it.copy(isLampOn = !it.isLampOn) }
    }

    fun toggleIncense() {
        _uiState.update { it.copy(isIncenseBurning = !it.isIncenseBurning) }
    }

    fun ringBell() {
        viewModelScope.launch {
            _uiState.update { it.copy(isBellRinging = true) }
            delay(500) // Simulate bell ringing animation duration
            _uiState.update { it.copy(isBellRinging = false) }
        }
    }

    fun breakCoconut() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCoconutBroken = true) }
            delay(1000) // Simulate coconut breaking animation duration
            _uiState.update { it.copy(isCoconutBroken = false) } // Reset after animation
        }
    }

    // Dialog control functions
    fun showGodDialog(show: Boolean) {
        _uiState.update { it.copy(showGodDialog = show) }
    }

    fun showBgDialog(show: Boolean) {
        _uiState.update { it.copy(showBgDialog = show) }
    }

    fun showMusicDialog(show: Boolean) {
        _uiState.update { it.copy(showMusicDialog = show) }
    }

    fun showFlowerDialog(show: Boolean) {
        _uiState.update { it.copy(showFlowerDialog = show) }
    }

    fun orbitAnimation(index: Int, coroutineScope: CoroutineScope) {
        val currentOrbitingInProgress = _uiState.value.orbitingInProgress
        if (currentOrbitingInProgress) return

        _uiState.update { it.copy(orbitingInProgress = true) }

        coroutineScope.launch {
            if (index == 2) delay(5000) // Delay for subsequent items if needed
            val uiStateValue = _uiState.value // Capture current state for animatables
            // Execute the lift and rotate animation
            // Assuming liftAndRotate is an extension function or a standalone suspend function
            liftAndRotate(
                index = index,
                defaultPos = uiStateValue.defaultPos,
                liftOffsets = uiStateValue.liftOffsets,
                shakeOffset = uiStateValue.bellShakeOffset
            )
            _uiState.update { it.copy(orbitingInProgress = false) }
        }
    }

    /**
     * Cancels all ongoing flower falling animations and resets their state.
     */
    fun stopFlowerAnimations() {
        _flowerAnimationJobs.values.forEach { it.cancel() } // Cancel all jobs
        _flowerAnimationJobs.clear() // Clear the map

        _uiState.update { currentState ->
            val resetFlowerVisible = List(25) { false }
            // Reset animatable positions to their initial state outside of the main UI update
            // as Animatable.snapTo is a suspend function.
            viewModelScope.launch {
                currentState.flowerOffsets.forEach { it.snapTo(-100f) }
                currentState.flowerRotations.forEach { it.snapTo(0f) }
            }
            currentState.copy(flowerVisible = resetFlowerVisible)
        }
    }

    fun flowerFall(coroutineScope: CoroutineScope) {
        stopFlowerAnimations()
        val delayRange = 0..3000

        // Update the UI state to reflect the start of the animations
        _uiState.update { currentState ->
            // Create a mutable copy for internal manipulation during the loop
            val updatedFlowerVisible = currentState.flowerVisible.toMutableList()

            // Launch a job for each flower
            for (index in 0 until 25) {
                // Cancel any potentially lingering job for this specific flower
                _flowerAnimationJobs[index]?.cancel()

                val job = coroutineScope.launch {
                    // Reset position to above screen before delay and animation
                    currentState.flowerOffsets[index].snapTo(-100f)

                    // Apply random delay for staggered falls
                    delay(Random.nextInt(delayRange.first, delayRange.last).toLong())

                    // Make the flower visible and update UI state (via the main _uiState update)
                    updatedFlowerVisible[index] = true
                    _uiState.update { it.copy(flowerVisible = updatedFlowerVisible.toList()) } // Push update

                    // Start infinite rotation in a separate child coroutine
                    val rotationAnim = currentState.flowerRotations[index]
                    val rotationJob = launch {
                        while (isActive) {
                            rotationAnim.animateTo(
                                targetValue = rotationAnim.value + 360f,
                                animationSpec = androidx.compose.animation.core.tween(
                                    durationMillis = 3000,
                                    easing = androidx.compose.animation.core.LinearEasing
                                )
                            )
                        }
                    }

                    // Animate the flower falling down the screen
                    currentState.flowerOffsets[index].animateTo(
                        targetValue = 1300f, // Target Y-coordinate off-screen bottom
                        animationSpec = androidx.compose.animation.core.tween(
                            durationMillis = 5000,
                            easing = androidx.compose.animation.core.LinearEasing
                        )
                    )
                    rotationJob.cancelAndJoin() // Stop spinning when it reaches the bottom
                    //updatedFlowerVisible[index] = false // Hide flower after it falls
                    _uiState.update { it.copy(flowerVisible = updatedFlowerVisible.toList()) } // Push update
                }
                _flowerAnimationJobs[index] = job // Store the job for potential cancellation
            }
            currentState // Return the (potentially) updated state with the new `flowerVisible` list
        }

        // Auto stop all flowers after a set duration (e.g., 30 seconds)
        coroutineScope.launch {
            delay(30_000L) // Wait for 30 seconds
            stopFlowerAnimations() // Stop all animations
        }
    }
}
