package com.apps.tc.tccompose2025.poojacorner

import android.media.MediaPlayer
import android.net.Uri
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.graphics.colorspace.Illuminant.C
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.Constant
import com.apps.tc.tccompose2025.R
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.showToast
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
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

    val coconutImage = remember { mutableStateOf("coconut.png") }
    val gifImage = remember { mutableStateOf("img_lamp.png") }
    val imageLoader = remember {
        ImageLoader.Builder(app).components {
            add(ImageDecoderDecoder.Factory()) // For API 28+
            add(GifDecoder.Factory())          // For API <28
        }.build()
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
    val mediaPlayer = remember { MediaPlayer() }
    val isPlaying = remember { mutableStateOf(false) }
    val orbitingInProgress = remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if (mediaPlayer.isPlaying) mediaPlayer.stop()
                }
                Lifecycle.Event.ON_DESTROY -> {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


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

    val flowerJobs = remember { MutableList<Job?>(50) { null } }

    fun stopFlower() {
        flowerJobs.forEachIndexed { index, job ->
            scope.launch {
                flowerVisible[index].value = false
                job?.cancel()
                flowerJobs[index] = null
                flowerOffsets[index].snapTo(80f)
            }
        }
    }

    fun playSong(app: App, url: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(app, url.toUri())
            mediaPlayer.prepare()
            mediaPlayer.start()
            isPlaying.value = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopSong() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            isPlaying.value = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = poojaState) {
            is PoojaState.ShowPoojaItems -> {
                val theme = state.themes
                val god = state.godData
                val flower = state.flowers
                val poojaBottomSheetItem = remember {
                    mutableStateOf<Pair<Int, List<Pair<String, String>>>?>(null)
                }

                fun orbitAnimation(index: Int) {
                    if (orbitingInProgress.value) return
                    orbitingInProgress.value = true

                    scope.launch {
                        liftAnimation(
                            index = index,
                            defaultPos = defaultPos,
                            liftOffsets = liftOffsets,
                            shakeOffset = bellShakeOffset,
                            app = app
                        )
                        orbitingInProgress.value = false
                    }
                }

                fun coconutBreak() {
                    when (coconutClickCount.intValue) {
                        0 -> {
                            coconutImage.value = "coconut01.png"
                            showToast(app, "Click 2 more times!")
                            poojaViewModel.vibrator(
                                app, 100
                            )
                        }

                        1 -> {
                            coconutImage.value = "coconut02.png"
                            showToast(app, "Click 1 more time!")
                            poojaViewModel.vibrator(
                                app, 200
                            )
                        }

                        2 -> {
                            coconutImage.value = "coconut03.png"
                            poojaViewModel.vibrator(
                                app, 300
                            )
                        }
                    }

                    // Increment click count up to 3 max
                    if (coconutClickCount.intValue < 2) {
                        coconutClickCount.intValue += 1
                    }
                }

                fun flowerFall() {

                    val delayRange = (0..3000) // Maximum 3 seconds delay for any flower
                    flowerOffsets.forEachIndexed { index, anim ->
                        val job = scope.launch {
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
                        flowerJobs[index]?.cancel()  // Cancel if already running
                        flowerJobs[index] = job
                    }
                    scope.launch {
                        delay(30_000L)  // 1 minute = 60,000 ms
                        stopFlower()
                    }
                }

                AsyncImage(
                    model = theme.poojaBgPath,
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
                        modifier = Modifier.padding(bottom = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Rotating background
                        AsyncImage(
                            model = theme.godBgPath,
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

                        // Falling Flowers
                        flowerOffsets.forEachIndexed { index, offsetY ->
                            if (flowerVisible[index].value) {
                                AsyncImage(
                                    model = flower.serviceIcon,
                                    contentDescription = "Falling Flower",
                                    modifier = Modifier
                                        .size(38.dp)
                                        .offset {
                                            /*
                                            * offsetY.value is the current vertical Y-position of the flower (animated).
                                            * sin(offsetY.value / 100) causes a wave-like left-right oscillation.
                                            * It uses sine wave logic to simulate smooth natural motion.
                                            * offsetY.value / 100 controls the speed of swaying. Higher = slower sway.
                                            * 40 controls the amplitude (how far it swings left and right).
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
                                        .align(Alignment.TopCenter))
                            }
                        }

                        // Orbiting objects
                        arrayOf(
                            "img_pooja_plate.png", "img_incense.png", "img_pooja_bell.png"
                        ).forEachIndexed { i, img ->
                            OrbitingItem(
                                index = i,
                                orbitingImg = img,
                                angle = defaultPos[i].value,
                                liftOffset = liftOffsets[i].value,
                                rotation = if (i == 2) bellShakeOffset.value else 0f,
                            )
                        }
                    }

                    // Lamp & Coconut
                    Row(
                        modifier = Modifier.padding(start = 32.dp, top = 64.dp, bottom = 18.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = assetPath(gifImage.value),
                            imageLoader = imageLoader,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            alignment = Alignment.BottomCenter
                        )

                        AsyncImage(
                            model = assetPath(coconutImage.value),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
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
                        arrayOf(
                            when(poojaViewModel.selectedGod) {
                                0 -> theme.vinayagarPath
                                1 -> theme.perumalPath
                                2 -> theme.muruganPath
                                3 -> theme.iyappanPath
                                4 -> theme.lakshmiPath
                                5 -> theme.durgaPath
                                6 -> theme.saraswathiPath
                                7 -> theme.saibabaPath
                                8 -> theme.sivanPath
                                else -> theme.hanumanPath
                            },
                            if (isPlaying.value) theme.stopPath else theme.musicPath,
                            theme.themePath,
                            theme.lampPath,
                            when(poojaViewModel.selectedFlower) {
                                0 -> theme.hybiscusPath
                                1 -> theme.samanthiPath
                                2 -> theme.rosePath
                                3 -> theme.sevvanthiPath
                                else -> theme.lotusPath
                            },
                            theme.platePath,
                            theme.incensePath,
                            theme.bellPath,
                            theme.coconutPath,
                        ).forEachIndexed { index, btn ->

                            AsyncImage(
                                model = btn,
                                contentDescription = "buttons",
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(8.dp)
                                    .clickable {
                                        when (index) {
                                            0 -> {  // god
                                                poojaBottomSheetItem.value =
                                                    Pair(index, state.poojaData.gods.map {
                                                        Pair(it.godImage, it.godName)
                                                    })
                                                showBottomSheet.value = true
                                            }

                                            1 -> {  // song
                                                if (isPlaying.value) {
                                                    stopSong()
                                                } else {
                                                    poojaBottomSheetItem.value =
                                                        Pair(index, god.songs.map {
                                                            Pair(it.serviceIcon, it.name)
                                                        })
                                                    showBottomSheet.value = true
                                                }
                                            }

                                            2 -> {  // theme
                                                poojaBottomSheetItem.value = Pair(
                                                    index, state.poojaData.themes.map {
                                                        Pair(it.poojaBgPath, "")
                                                    }
                                                )
                                                showBottomSheet.value = true
                                            }

                                            3 -> gifImage.value = "gif_lamp.gif"
                                            4 -> {
                                                poojaBottomSheetItem.value = Pair(
                                                    index, state.poojaData.flowers.map {
                                                        Pair(it.serviceIcon, it.name)
                                                    }
                                                )
                                                showBottomSheet.value = true
                                            }

                                            5 -> orbitAnimation(0)  // 0th index is plate
                                            6 -> orbitAnimation(1)  // 1 is incense
                                            7 -> orbitAnimation(2)  // 2 is bell
                                            8 -> coconutBreak()
                                        }
                                    })
                        }
                    }
                }

                if (showBottomSheet.value) {
                    poojaBottomSheetItem.value?.let {
                        PoojaBottomSheet(
                            poojaItems = it.second,
                            onSelectedItem = { index ->
                                log(index)
                                log(god.godId)
                                when (it.first) {
                                    0 -> {
                                        poojaViewModel.selectedGod = index
                                        poojaViewModel.updateTheme()
                                    }
                                    1 -> {
                                        val songUrl =
                                            "${Constant.ContentURL}calendar/tamil/pooja/pooja_${god.godId}_${index + 1}.mp3"
                                        playSong(app, songUrl)
                                    }
                                    2 -> {
                                        poojaViewModel.selectedTheme = index
                                        poojaViewModel.updateTheme()
                                    }
                                    4 -> {
                                        poojaViewModel.selectedFlower = index
                                        poojaViewModel.updateTheme()
                                        flowerFall()
                                    }
                                }
                            },
                            onDismiss = { showBottomSheet.value = false },
                            showSheet = showBottomSheet.value,
                        )
                    }
                }
            }

            is PoojaState.Loading -> { /* Add loader if needed */
            }

            is PoojaState.Error -> { /* Handle error state */
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch { poojaViewModel.fetchPoojaData(app) }
    }
    LaunchedEffect(poojaViewModel.selectedGod) {
        // Reset orbiting items' positions
        defaultPos[0].snapTo(90f)
        defaultPos[1].snapTo(110f)
        defaultPos[2].snapTo(65f)

        liftOffsets.forEach { it.snapTo(0f) }

        // Reset coconut
        coconutClickCount.intValue = 0
        coconutImage.value = "coconut.png"

        // Reset gif lamp
        gifImage.value = "img_lamp.png"

        stopFlower()
        stopSong()
    }
}

@Composable
fun OrbitingItem(
    index: Int,
    orbitingImg: String,
    angle: Float,
    liftOffset: Float,
    rotation: Float,
) {
    val radius = with(LocalDensity.current) { 150.dp.toPx() }
    val radian = Math.toRadians(angle.toDouble())

    Image(
        painter = rememberAsyncImagePainter(assetPath(orbitingImg)),
        contentDescription = "Orbit Item",
        modifier = Modifier
            .padding(top = 285.dp)
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
    liftOffsets: List<Animatable<Float, AnimationVector1D>>,
    shakeOffset: Animatable<Float, AnimationVector1D>,
    app: App
) {
    val lift = liftOffsets[index]
    val angle = defaultPos[index]
    val origin = angle.value % 360f

    val bellPlayer = if (index == 2) MediaPlayer.create(app, R.raw.bell_sound) else null

    lift.animateTo(
        targetValue = -280f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    coroutineScope {
        var shakeJob: Job? = null
        var rotateJob: Job? = null

        if (index == 2 && bellPlayer != null) {
            bellPlayer.isLooping = true
            bellPlayer.start()

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

            bellPlayer.stop()
            bellPlayer.release()
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

    lift.animateTo(
        targetValue = 0f, animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoojaBottomSheet(
    poojaItems: List<Pair<String, String>>,
    onSelectedItem: (Int) -> Unit,
    onDismiss: () -> Unit,
    showSheet: Boolean,
) {
    if (!showSheet) return
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        containerColor = Color.White,
        modifier = Modifier.padding(8.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            poojaItems.forEachIndexed { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(0.225f)
                        .clickable {
                            onSelectedItem(index)
                            onDismiss()
                        }) {
                    AsyncImage(
                        model = item.first,
                        contentDescription = "god",
                        //contentScale = ContentScale.Fit,
                        modifier = Modifier.size(64.dp)
                    )
                    if (item.second.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = item.second,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
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