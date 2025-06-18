package com.apps.tc.tccompose2025.poojacorner

import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.Coil.imageLoader
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.models.PoojaButton
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

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
    val defaultPos = listOf(90f, 113f, 60f).map { remember { Animatable(it) } }

    val infiniteRotation = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(100000, easing = LinearEasing))
    )

    val poojaButtons = listOf(
        PoojaButton(R.drawable.ic_god, "God"),
        PoojaButton(R.drawable.ic_music, "Music"),
        PoojaButton(R.drawable.ic_music, "Theme"),
        PoojaButton(R.drawable.ic_lamp_off, "Lamp"),
        PoojaButton(R.drawable.ic_fower, "Flower"),
        PoojaButton(R.drawable.ic_plate, "Plate", orbitIndex = 0),
        PoojaButton(R.drawable.ic_incense, "Incense", orbitIndex = 1),
        PoojaButton(R.drawable.ic_bell, "Bell", orbitIndex = 2),
        PoojaButton(R.drawable.ic_coconut, "Coconut")
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
                            model = god.godImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(500.dp)
                                .padding(top = 108.dp)
                        )

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
                            .padding(top = 68.dp, bottom = 18.dp),
                        horizontalArrangement = Arrangement.Center
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
                            alignment = Alignment.BottomCenter
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
                            Image(
                                painter = painterResource(btn.icon),
                                contentDescription = btn.label,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(8.dp)
                                    .clickable {
                                        scope.launch {
                                            when (btn.orbitIndex) {
                                                0, 1 -> liftAnimation(
                                                    index = btn.orbitIndex,
                                                    defaultPos = defaultPos,
                                                    liftOffsets = liftOffsets
                                                )

                                                2 -> handleBellAnimation(
                                                    angle = defaultPos[2],
                                                    shakeOffset = bellShakeOffset,
                                                    app = app
                                                )

                                                else -> {
                                                    when(btn.label) {
                                                        "Coconut" -> {
                                                            when (coconutClickCount.intValue) {
                                                                0 -> {
                                                                    coconutImage.intValue = R.drawable.coconut01
                                                                    poojaViewModel.toastMsg(app)
                                                                    poojaViewModel.vibrator(app, 100)
                                                                }
                                                                1 -> {
                                                                    coconutImage.intValue = R.drawable.coconut02
                                                                    poojaViewModel.toastMsg(app)
                                                                    poojaViewModel.vibrator(app, 200)
                                                                }
                                                                2 -> {
                                                                    coconutImage.intValue = R.drawable.coconut03
                                                                }
                                                            }

                                                            // Increment click count up to 3 max
                                                            if (coconutClickCount.intValue < 2) {
                                                                coconutClickCount.intValue += 1
                                                            }
                                                        }
                                                        "God" -> {}
                                                        "Music" -> {}
                                                        "Lamp" -> {
                                                            gifImage.value = "gif_lamp.gif"
                                                        }
                                                        "Flower" -> {}
                                                    }
                                                }
                                            }
                                        }
                                    })
                        }
                    }
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
    val radius = with(LocalDensity.current) { 120.dp.toPx() }
    val radian = Math.toRadians(angle.toDouble())

    Image(
        painter = rememberAsyncImagePainter("file:///android_asset/virtualpooja/$imageAsset.png"),
        contentDescription = "Orbit Item",
        modifier = Modifier
            .padding(top = 345.dp)
            .size(if (index == 0) 80.dp else 70.dp)
            .graphicsLayer {
                translationX = cos(radian).toFloat() * radius
                translationY = sin(radian).toFloat() * radius + liftOffset
                rotationZ = rotation
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

    lift.animateTo(-280f, tween(500, easing = FastOutSlowInEasing))
    angle.animateTo(angle.value + 1080f, tween(8000, easing = LinearEasing))
    angle.snapTo(origin)
    lift.animateTo(0f, tween(500, easing = FastOutSlowInEasing))
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

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PoojaBottomSheet(godData:Array<PoojaData>, onReturn:() -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { isShown = false },
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 6,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            //todo: Instead of poojaIcons the icons should comes from API
            //todo: The functionalities should handle here
            if (godIcons) {
                godData.forEach { i ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = "file:///android_asset/virtualpooja/${i.imageUrl}",
                            contentDescription = "Article Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .weight(0.5f)
                                .height(100.dp)
                                .clickable {
                                    selectedGod = i
                                    showBottomSheet = false
                                    godIcons = false
                                }
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp),
                            text = i.godName,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}*/


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PoojaPreview() {
    ComposeTamilCalendar2025Theme {
        VirtualPooja(App())
    }
}