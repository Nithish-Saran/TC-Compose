package com.apps.tc.tccompose2025.poojacorner

import android.media.MediaPlayer
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.models.PoojaButton
import com.apps.tc.tccompose2025.models.PoojaData
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun VirtualPooja(app: App) {
    val poojaViewModel: PoojaViewModel = viewModel()
    val poojaState by poojaViewModel.poojaState.collectAsState()
    val scope = rememberCoroutineScope()

    val coconutImage = remember { mutableIntStateOf(R.drawable.coconut) }
    val gifImage = remember { mutableStateOf("img_lamp.png") }
    val imageLoader = remember {
        ImageLoader.Builder(app)
            .components {
                add(ImageDecoderDecoder.Factory()) // For API 28+
                add(GifDecoder.Factory())          // For API <28
            }
            .build()
    }
    val coconutClickCount = remember { mutableIntStateOf(0) }
    val bellShakeOffset = remember { Animatable(0f) }

    val liftOffsets = remember { List(3) { Animatable(0f) } }
    val defaultPos = listOf(90f, 110f, 65f).map { remember { Animatable(it) } }

    val infiniteRotation = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(100000, easing = LinearEasing))
    )

    val showBottomSheet = remember { mutableStateOf(false) }
    val selectedGod = remember { mutableStateOf("") }

    // List of Y Animations for 10 flowers
    val flowerOffsets = remember {
        List(50) { Animatable(80f) } // Start above the screen
    }

    // List of random horizontal X offsets
    val xOffsets = remember {
        List(50) { Random.nextInt(-420, 420) } // Varying X positions
    }

    val flowerVisible = remember {
        List(50) { mutableStateOf(false) } // Control visibility per flower
    }

    val poojaButtons = listOf(
        PoojaButton(R.drawable.ic_god, "God"),
        PoojaButton(R.drawable.ic_music, "Music"),
        PoojaButton(R.drawable.ic_theme, "Theme"),
        PoojaButton(R.drawable.ic_lamp_off, "Lamp"),
        PoojaButton(R.drawable.ic_fower, "Flower"),
        PoojaButton(R.drawable.ic_plate, "Plate", orbitIndex = 0),
        PoojaButton(R.drawable.ic_incense, "Incense", orbitIndex = 1),
        PoojaButton(R.drawable.ic_bell, "Bell", orbitIndex = 2),
        PoojaButton(R.drawable.ic_coconut, "Coconut"),
    )

    LaunchedEffect(Unit) {
        scope.launch { poojaViewModel.fetchPoojaData(app) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = poojaState) {
            is PoojaState.Success -> {
                val god = state.poojaData[0]
                val theme = god.themes[0]

                AsyncImage(
                    model = theme.poojaBgImage,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Rotating background
                        AsyncImage(
                            model = theme.godBgImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(340.dp)
                                .graphicsLayer { rotationZ = infiniteRotation.value },
                            contentScale = ContentScale.FillBounds
                        )

                        // God image
                        AsyncImage(
                            model = selectedGod.value,
                            contentDescription = null,
                            modifier = Modifier
                                .size(500.dp)
                                .padding(top = 108.dp)
                        )

                        // Falling Flowers
                        flowerOffsets.forEachIndexed { index, offsetY ->
                            if (flowerVisible[index].value) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_flower),
                                    contentDescription = "Falling Flower",
                                    modifier = Modifier
                                        .size(38.dp)
                                        .offset {
                                            /*
                                            * offsetY.value is the current vertical Y-position of the flower (animated).
                                            * sin(offsetY.value / 100) causes a wave-like left-right oscillation.
                                            * It uses sine wave logic to simulate smooth natural motion.
                                            * offsetY.value / 100 controls the speed of swaying. Higher = slower sway.
                                            * * 40 controls the amplitude (how far it swings left and right).
                                            * xOffsets[index] is the initial horizontal starting point for that flower.
                                            * IntOffset(x, y) sets the flower’s exact position on screen.
                                            */
                                            val swayX =
                                                (sin(offsetY.value / 100) * 40).toInt() // swing in air
                                            IntOffset(
                                                x = xOffsets[index] + swayX,
                                                y = offsetY.value.toInt()
                                            )
                                        }
                                        .align(Alignment.TopCenter)
                                )
                            }
                        }

                        // Orbiting objects
                        arrayOf(
                            "img_pooja_plate", "img_incense", "img_pooja_bell"
                        ).forEachIndexed { i, img ->
                            OrbitingItem(
                                index = i,
                                imageAsset = img,
                                angle = defaultPos[i].value,
                                liftOffset = liftOffsets[i].value,
                                rotation = if (i == 2) bellShakeOffset.value else 0f,
                            )
                        }
                    }

                    // Lamp & Coconut
                    Row(
                        modifier = Modifier
                            .padding(start = 32.dp, top = 64.dp, bottom = 18.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(app)
                                .data("file:///android_asset/virtualpooja/${gifImage.value}")
                                .crossfade(true)
                                .build(),
                            imageLoader = imageLoader,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            alignment = Alignment.BottomCenter
                        )

                        Image(
                            painter = painterResource(coconutImage.intValue),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp),
                            alignment = Alignment.Center
                        )
                    }

                    // Buttons
                    FlowRow(
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        maxItemsInEachRow = 5,
                        itemVerticalAlignment = Alignment.CenterVertically
                    ) {
                        poojaButtons.forEach { btn ->
                            val isGodButton = btn.label == "God"
                            val isEnabled = isGodButton || selectedGod.value.isNotEmpty()
                            Image(
                                painter = painterResource(btn.icon),
                                contentDescription = btn.label,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(8.dp)
                                    .clickable(enabled = isEnabled) {
                                        scope.launch {
                                            when (btn.orbitIndex) {
                                                0, 1, 2 -> liftAnimation(
                                                    index = btn.orbitIndex,
                                                    defaultPos = defaultPos,
                                                    liftOffsets = liftOffsets
                                                )

//                                                2 -> handleBellAnimation(
//                                                    angle = defaultPos[2],
//                                                    shakeOffset = bellShakeOffset,
//                                                    app = app
//                                                )

                                                else -> {
                                                    when (btn.label) {
                                                        "Coconut" -> {
                                                            when (coconutClickCount.intValue) {
                                                                0 -> {
                                                                    coconutImage.intValue =
                                                                        R.drawable.coconut01
                                                                    poojaViewModel.toastMsg(app)
                                                                    poojaViewModel.vibrator(
                                                                        app,
                                                                        100
                                                                    )
                                                                }

                                                                1 -> {
                                                                    coconutImage.intValue =
                                                                        R.drawable.coconut02
                                                                    poojaViewModel.toastMsg(app)
                                                                    poojaViewModel.vibrator(
                                                                        app,
                                                                        200
                                                                    )
                                                                }

                                                                2 -> {
                                                                    coconutImage.intValue =
                                                                        R.drawable.coconut03
                                                                    poojaViewModel.vibrator(
                                                                        app,
                                                                        300
                                                                    )
                                                                }
                                                            }

                                                            // Increment click count up to 3 max
                                                            if (coconutClickCount.intValue < 2) {
                                                                coconutClickCount.intValue += 1
                                                            }
                                                        }

                                                        "God" -> {
                                                            showBottomSheet.value = true
                                                        }

                                                        "Music" -> {
                                                            //showBottomSheet.value = true
                                                        }

                                                        "Lamp" -> {
                                                            gifImage.value = "gif_lamp.gif"
                                                        }
                                                        "Flower" -> {
                                                            val delayRange =
                                                                (0..3000) // Maximum 3 seconds delay for any flower
                                                            // Start animations
                                                            flowerOffsets.forEachIndexed { index, anim ->
                                                                launch {
                                                                    val delayMillis =
                                                                        Random.nextInt(
                                                                            delayRange.first,
                                                                            delayRange.last
                                                                        )
                                                                    delay(delayMillis.toLong()) // Random human-like delay
                                                                    flowerVisible[index].value =
                                                                        true
                                                                    anim.snapTo(-100f)
                                                                    anim.animateTo(
                                                                        targetValue = 1300f,    //
                                                                        animationSpec = tween(
                                                                            durationMillis = 4000,
                                                                            easing = LinearEasing
                                                                        )
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    })
                        }
                    }
                }
                if (showBottomSheet.value) {
                    PoojaBottomSheet(
                        godData = state.poojaData,
                        onSelectedItem = { god ->
                            selectedGod.value = god

                            // 🔁 Reset all animation and state values
                            coconutClickCount.intValue = 0
                            coconutImage.intValue = R.drawable.coconut
                            gifImage.value = "img_lamp.png"
                            scope.launch {
                                liftOffsets.forEach { launch { it.snapTo(0f) } }
                                defaultPos[0].snapTo(90f)
                                defaultPos[1].snapTo(107f)
                                defaultPos[2].snapTo(70f)

                                flowerVisible.forEach { it.value = false }
                                flowerOffsets.forEach { launch { it.snapTo(0f) } }
                            }
                            // Optionally stop any media players or effects here
                        },
                        onDismiss = { showBottomSheet.value = false },
                        showSheet = showBottomSheet.value,
                    )
                }
            }

            is PoojaState.Loading -> { /* Add loader if needed */
            }

            is PoojaState.Error -> { /* Handle error state */
            }
        }
    }
}

@Composable
fun OrbitingItem(
    index: Int,
    imageAsset: String,
    angle: Float,
    liftOffset: Float,
    rotation: Float,
) {
    val radius = with(LocalDensity.current) { 150.dp.toPx() }
    val radian = Math.toRadians(angle.toDouble())

    Image(
        painter = rememberAsyncImagePainter("file:///android_asset/virtualpooja/$imageAsset.png"),
        contentDescription = "Orbit Item",
        modifier = Modifier
            .padding(top = 285.dp)
            .size(if (index == 0) 80.dp else 70.dp)
            .graphicsLayer {
                translationX = cos(radian).toFloat() * radius
                translationY = sin(radian).toFloat() * radius + liftOffset
                //rotationZ = rotation
            })
}

suspend fun liftAnimation(
    index: Int,
    defaultPos: List<Animatable<Float, AnimationVector1D>>,
    liftOffsets: List<Animatable<Float, AnimationVector1D>>
) {
    val lift = liftOffsets[index]
    val angle = defaultPos[index]
    val origin = angle.value % 360

    lift.animateTo(
        targetValue = -280f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    angle.animateTo(
        targetValue = angle.value + 1080f,
        animationSpec = tween(
            durationMillis = 8000,
            easing = LinearEasing
        )
    )
    angle.snapTo(origin)
    lift.animateTo(
        targetValue = 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
}

suspend fun handleBellAnimation(
    angle: Animatable<Float, AnimationVector1D>,
    shakeOffset: Animatable<Float, AnimationVector1D>,
    app: App
) {
    val bellPlayer = MediaPlayer.create(app, R.raw.bell_sound).apply { isLooping = true; start() }

    coroutineScope {
        val shake = launch {
            while (isActive) {
                shakeOffset.animateTo(10f, tween(50))
                shakeOffset.animateTo(-10f, tween(50))
            }
        }

        angle.animateTo(angle.value + 1080f, tween(8000, easing = LinearEasing))
        angle.snapTo(68f)

        shake.cancel()
        shakeOffset.snapTo(0f)
        bellPlayer.stop()
        bellPlayer.release()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoojaBottomSheet(
    godData: Array<PoojaData>,
    onSelectedItem: (String) -> Unit,
    onDismiss: () -> Unit,
    showSheet: Boolean
) {
    if (!showSheet) return
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        containerColor = Color.White,
        modifier = Modifier
            .padding(8.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 4,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            godData.forEach { god ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .clickable {
                            onSelectedItem(god.godImage)
                            onDismiss()
                        }
                ) {
                    AsyncImage(
                        model = god.godImage,
                        contentDescription = god.godName,
                        //contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(64.dp)
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = god.godName,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PoojaPreview() {
    ComposeTamilCalendar2025Theme {
        VirtualPooja(App())
    }
}