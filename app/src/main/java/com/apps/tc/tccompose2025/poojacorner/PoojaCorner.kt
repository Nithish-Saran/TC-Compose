package com.apps.tc.tccompose2025.poojacorner

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.dialog.PoojaDialog
import com.apps.tc.tccompose2025.models.assetPath
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PoojaCorner(app: App) {
    val poojaViewModel: PoojaViewModel = viewModel()
    val poojaState by poojaViewModel.poojaState.collectAsState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = poojaState) {
            is PoojaState.ShowPoojaItems -> {
                PoojaView(app, poojaViewModel)
            }

            is PoojaState.Loading -> {}
            is PoojaState.Error -> {}
        }
    }
    LaunchedEffect(Unit) {
        scope.launch { poojaViewModel.fetchPoojaData(app) }
    }
}

@SuppressLint("UnrememberedMutableState") // This annotation can be removed as we're using StateFlow now
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PoojaView(
    app: App,
    poojaViewModel: PoojaViewModel = viewModel()
) {
    val poojaDataState by poojaViewModel.poojaState.collectAsState()

    // Observe the overall UI state
    val uiState by poojaViewModel.uiState.collectAsState()

    // Get a CoroutineScope tied to the Composable's lifecycle
    val scope = rememberCoroutineScope()

    // Infinite rotation for decor image
    val infiniteRotation = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 100000,
                easing = LinearEasing
            )
        )
    )

    // Handle different data loading states
    when (val state = poojaDataState) {
        is PoojaState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                androidx.compose.material3.CircularProgressIndicator()
            }
        }

        is PoojaState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                androidx.compose.material3.Text(text = "Error: ${state.message}", color = Color.Red)
            }
        }

        is PoojaState.ShowPoojaItems -> {
            val poojaData = state.poojaData

            // Dialogs: controlled by uiState and emit events back to ViewModel
            when {
                uiState.showGodDialog -> {
                    PoojaDialog(
                        title = "கடவுளை",
                        god = poojaData.gods,
                        onDismiss = { poojaViewModel.showGodDialog(false) },
                        onReturn = { index -> poojaViewModel.onGodSelected(index) }
                    )
                }

                uiState.showBgDialog -> {
                    PoojaDialog(
                        title = "அலங்காரத்தை",
                        bgs = poojaData.bgs,
                        onDismiss = { poojaViewModel.showBgDialog(false) },
                        onReturn = { index -> poojaViewModel.onBgSelected(index) }
                    )
                }

                uiState.showMusicDialog -> {
                    PoojaDialog(
                        title = "பாடலை",
                        song = poojaData.songs,
                        onDismiss = { poojaViewModel.showMusicDialog(false) },
                        onReturn = { index -> poojaViewModel.onMusicSelected(index) }
                    )
                }

                uiState.showFlowerDialog -> {
                    PoojaDialog(
                        title = "மலரை",
                        flower = poojaData.flowers,
                        onDismiss = { poojaViewModel.showFlowerDialog(false) },
                        onReturn = { index -> poojaViewModel.onFlowerSelected(index, scope) }
                    )
                }
            }

            // Background image
            AsyncImage(
                model = uiState.currentBgImage,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    // Decor image with infinite rotation
                    AsyncImage(
                        model = uiState.currentDecorImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(256.dp)
                            .graphicsLayer { rotationZ = infiniteRotation.value },
                    )
                    // God image
                    AsyncImage(
                        model = uiState.currentGodImage,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .padding(bottom = 128.dp)
                            .fillMaxSize()
                            .aspectRatio(2f / 3f)
                    )

                    // Falling Flowers
                    uiState.flowerOffsets.forEachIndexed { index, offsetY ->
                        if (uiState.flowerVisible[index]) {
                            AsyncImage(
                                model = uiState.currentFlowerImage,
                                contentDescription = "Falling Flower",
                                modifier = Modifier
                                    .size(38.dp)
                                    .offset {
                                        val swayX = (sin(offsetY.value / 100) * 40).toInt()
                                        IntOffset(
                                            x = uiState.xOffsets[index] + swayX,
                                            y = offsetY.value.toInt()
                                        )
                                    }
                                    .graphicsLayer {
                                        rotationZ = uiState.flowerRotations[index].value % 360
                                    }
                                    .align(Alignment.TopCenter)
                            )
                        }
                    }

                    // Things (Plate, Bell, Incense)
                    arrayOf(
                        "things_plate.png",
                        uiState.bellImage, // Uses derived property from UiState
                        uiState.incenseImage // Uses derived property from UiState
                    ).forEachIndexed { i, img ->
                        Things(
                            app = app,
                            img = img,
                            angle = uiState.defaultPos[i].value,
                            liftOffset = uiState.liftOffsets[i].value,
                            rotation = uiState.bellShakeOffset.value
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                // Control buttons
                FlowRow(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    maxItemsInEachRow = 5,
                    itemVerticalAlignment = Alignment.CenterVertically
                ) {
                    arrayOf(
                        "btn_god", "btn_music", "btn_bg", "btn_lamp", "btn_flower",
                        "btn_plate", "btn_incense", "btn_bell", "btn_coconut"
                    ).forEachIndexed { i, img ->
                        AsyncImage(
                            model = assetPath(img),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(6.dp)
                                .size(56.dp)
                                .clickable {
                                    when (i) {
                                        0 -> poojaViewModel.showGodDialog(true)
                                        1 -> poojaViewModel.showMusicDialog(true)
                                        2 -> poojaViewModel.showBgDialog(true)
                                        3 -> poojaViewModel.toggleLamp()
                                        4 -> poojaViewModel.showFlowerDialog(true)
                                        5 -> poojaViewModel.orbitAnimation(0, scope) // Plate
                                        6 -> {
                                            poojaViewModel.let {
                                                it.toggleIncense()
                                                it.orbitAnimation(2, scope)
                                            }
                                        }
                                        7 -> {
                                            poojaViewModel.let {
                                                it.orbitAnimation(1, scope)
                                                it.ringBell()
                                            }
                                        }
                                        8 -> poojaViewModel.breakCoconut()
                                    }
                                },
                            contentScale = ContentScale.FillBounds,
                            alignment = Alignment.Center
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Things(
    app: App,
    img: String,
    angle: Float,
    liftOffset: Float,
    rotation: Float,
) {
    val imageLoader = remember {
        ImageLoader.Builder(app).components {
            add(ImageDecoderDecoder.Factory()) // For API 28+
            add(GifDecoder.Factory())          // For API <28
        }.build()
    }

    val radius = with(LocalDensity.current) { 150.dp.toPx() }
    val radian = Math.toRadians(angle.toDouble())

    Box(
        modifier = Modifier
            .padding(top = 340.dp)
            .size(100.dp)
            .graphicsLayer {
                translationX = cos(radian).toFloat() * radius
                translationY = sin(radian).toFloat() * radius + liftOffset
                rotationZ = rotation
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = "file:///android_asset/virtualpooja/$img",
            imageLoader = imageLoader,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

suspend fun liftAndRotate(
    index: Int,
    defaultPos: List<Animatable<Float, AnimationVector1D>>,
    liftOffsets: List<Animatable<Float, AnimationVector1D>>,
    shakeOffset: Animatable<Float, AnimationVector1D>,
) {
    val lift = liftOffsets[index]
    val angle = defaultPos[index]
    val origin = angle.value % 360f

    lift.animateTo(
        targetValue = -280f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    coroutineScope {
        var shakeJob: Job
        var rotateJob: Job

        if (index == 2) {
            //bellPlayer.isLooping = true
            //bellPlayer.start()

            // Shake and rotate at the same time
            shakeJob = launch {
                while (isActive) {
                    shakeOffset.animateTo(10f, tween(80))
                    shakeOffset.animateTo(-10f, tween(80))
                }
            }

            rotateJob = launch {
                angle.animateTo(
                    targetValue = angle.value + 1080f,
                    animationSpec = tween(
                        durationMillis = 8000,
                        easing = LinearEasing
                    )
                )
            }

            rotateJob.join() // Wait until rotation finishes

            shakeJob.cancelAndJoin()
            shakeOffset.snapTo(0f)

            //bellPlayer.stop()
            //bellPlayer.release()
        } else {
            // For other items: just rotate
            angle.animateTo(
                targetValue = angle.value + 1080f, animationSpec = tween(
                    durationMillis = 8000, easing = LinearEasing
                )
            )
        }
    }
    angle.snapTo(origin)

    // back to default pos
    lift.animateTo(
        targetValue = 0f,
        animationSpec = tween(
            durationMillis = 500, easing = FastOutSlowInEasing
        )
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PoojaCornerPreview() {
    ComposeTamilCalendar2025Theme {
        PoojaCorner(App())
    }
}