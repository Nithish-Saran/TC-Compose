package com.apps.tc.tccompose2025.models

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.remember
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject
import kotlin.random.Random

@Serializable
data class VirtualPoojaData(
    val gods: List<God>,
    val bgs: List<Bg>,
    val flowers: List<Flower>,
    val songs: List<Song>
) {
    companion object {
        fun serialize(json: JSONObject): VirtualPoojaData? = try {
            Json.decodeFromString<VirtualPoojaData>(json.toString())
        } catch (e: Exception) {
            null
        }
    }
}

fun assetPath(fileName: String): String =
    "file:///android_asset/virtualpooja/$fileName.png"

@Serializable
data class Flower(
    val name: String,
    val desc: String,
    val img: String
) {
    val flowerImage get() = assetPath(img)
}

@Serializable
data class Song(
    val god: String,
    val img: String,
    val title: String,
    val url: String
) {
    val godImage get() = assetPath(img)
}

@Serializable
data class Bg(
    val name: String,
    val desc: String,
    val img: String
) {
    val bgImage get() = assetPath(img)
}

@Serializable
data class God(
    val name: String,
    val desc: String,
    val img: String,
    val decor: String
) {
    val godImage get() = assetPath(img)
    val decorImage get() = assetPath(decor)
}

/**
 * Represents the overall UI state for the Pooja screen.
 * This class shows all the data needed in the UI and manage its elements.
 */
data class PoojaUiState(
    val poojaData: VirtualPoojaData? = null,

    // Currently selected items for display
    val currentGodImage: String = "", // Will be initialized from poojaData
    val currentDecorImage: String = "", // Will be initialized from poojaData
    val currentBgImage: String = "", // Will be initialized from poojaData
    val currentFlowerImage: String = "", // Will be initialized on flower selection

    // Toggle states for interactive items
    val isLampOn: Boolean = false,
    val isIncenseBurning: Boolean = false,
    val isBellRinging: Boolean = false,
    val isCoconutBroken: Boolean = false,

    // Dialog visibility states
    val showGodDialog: Boolean = false,
    val showBgDialog: Boolean = false,
    val showMusicDialog: Boolean = false,
    val showFlowerDialog: Boolean = false,

    // Animation-related states (Animatable objects are not Parcelable, so they're managed here)
    // These lists hold the Animatable objects themselves, allowing the ViewModel to control them.
    val liftOffsets: List<Animatable<Float,  AnimationVector1D>> = List(3) { Animatable(0f) },
    val defaultPos: List<Animatable<Float,  AnimationVector1D>> = listOf(
        Animatable(90f),    // plate
        Animatable(63f),    // bell
        Animatable(118f)    // incense
    ),

    val bellShakeOffset: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val orbitingInProgress: Boolean = false,

    // Flower falling animation states
    val flowerOffsets: List<Animatable<Float, *>> = List(25) { Animatable(80f) }, // Start above the screen
    val xOffsets: List<Int> = List(25) {
        Random.nextInt(-420, 420)
    },
    val flowerVisible: List<Boolean> = List(25) { false }, // Control visibility per flower
    val flowerRotations: List<Animatable<Float, *>> = List(25) { Animatable(Random.nextFloat() * 360f) }
    // Note: flowerJobs are managed internally by the ViewModel and not part of UiState,
) {
    // Helper properties to derive image paths based on state, keeping UI logic out of the Composable
    val lampImage: String
        get() = if (isLampOn) "things_lamp_anim.gif" else "things_lamp_off.png"

    val bellImage: String
        get() = if (isBellRinging) "things_bell_anim.gif" else "things_bell.png"

    val incenseImage: String
        get() = if (isIncenseBurning) "things_incense_anim.gif" else "things_incense.png"

    val coconutImage: String
        get() = if (isCoconutBroken) "things_coconut_anim.gif" else "things_coconut.png"

}