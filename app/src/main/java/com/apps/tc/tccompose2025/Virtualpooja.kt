package com.apps.tc.tccompose2025

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.apps.tc.tccompose2025.ui.theme.ComposeTamilCalendar2025Theme
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun VirtualPooja() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedGod by remember { mutableIntStateOf(R.drawable.vinayakar) }
    var coconutImage by remember { mutableIntStateOf(R.drawable.coconut) }
    var breakCount by remember { mutableIntStateOf(1) }
    var lamp by remember { mutableStateOf(false) }
    var godIcons by remember { mutableStateOf(false) }
    var rotatePlate by remember { mutableStateOf(false) }
    var angle by remember { mutableFloatStateOf(0f) }
    val radius = with(LocalDensity.current) { 200.dp.toPx() }

    // Image loader for GIF
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory())       // Enables GIF decoding
        }
        .build()

    val poojaIcons = arrayOf(
        R.drawable.ic_god,
        R.drawable.ic_music,
        R.drawable.ic_lamp_off,
        R.drawable.ic_fower,
        R.drawable.ic_bell,
        R.drawable.ic_incense,
        R.drawable.ic_plate,
        R.drawable.ic_coconut
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Image(
            painter = painterResource(R.drawable.img_bg_pooja),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.img_thoranam),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
            )
            Image(
                painter = painterResource(selectedGod),
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 46.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (rotatePlate) {
                    LaunchedEffect(rotatePlate) {
                        for (i in 0..1080) { // 3 full circles (360 * 3 = 1080 degrees)
                            angle = i.toFloat()
                            kotlinx.coroutines.delay(10) // Adjust speed
                        }
                        rotatePlate = false // Reset after the animation completes
                    }
                }

                val xOffset = radius * cos(Math.toRadians(angle.toDouble())).toFloat()
                val yOffset = radius * sin(Math.toRadians(angle.toDouble())).toFloat()

                Image(
                    painter = painterResource(R.drawable.img_pooja_plate),
                    contentDescription = "",
                    modifier = Modifier
                        .size(70.dp)
//                        .graphicsLayer(
//                            translationX = xOffset,
//                            translationY = yOffset
//                        )
                )

                Image(
                    painter = painterResource(R.drawable.img_incense),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp),
                    contentScale = ContentScale.FillBounds
                )

                AsyncImage(     //todo: GIF should be comes form API
                    model = ImageRequest.Builder(context)
                        .data(
                            if (lamp) R.drawable.gif_lamp else R.drawable.img_lamp)
                        .size(Size.ORIGINAL)
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = "lamp",
                    modifier = Modifier.size(60.dp)
                )

                Image(
                    painter = painterResource(R.drawable.img_pooja_bell),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                )
                Image(
                    painter = painterResource(coconutImage),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(top = 16.dp)
                )
            }

            // Icons
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                poojaIcons.forEachIndexed { index, i ->
                    if (index in 0..5) {
                        Image(
                            painter = painterResource(i),
                            contentDescription = "",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    when (index) {
                                        0 -> {
                                            showBottomSheet = true
                                            godIcons = true
                                        }
                                        1 -> {
                                            showBottomSheet = true
                                            godIcons = false
                                        }
                                        2 -> lamp = true
                                        3 -> {
                                            // todo: handle animations
                                        }
                                        4 -> {}
                                        5 -> {}
                                    }
                                }
                        )
                    }
                }
            }

            //Icons
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                poojaIcons.forEachIndexed { index, i ->
                    if (index in 6..7) {
                        Image(
                            painter = painterResource(i),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(
                                    top = 8.dp,
                                    start = if (index == 7) 12.dp else 0.dp
                                )
                                .size(50.dp)
                                .clickable {
                                    when (index) {
                                        6 -> {
                                            scope.launch {
                                                rotatePlate = true
                                            }
                                        }
                                        7 -> {
                                            when(breakCount) {
                                                1 -> {
                                                    coconutImage = R.drawable.coconut01
                                                    vibrator(context, 100)
                                                    Toast.makeText(context, "Click again", Toast.LENGTH_SHORT).show()
                                                }
                                                2 -> {
                                                    coconutImage = R.drawable.coconut02
                                                    vibrator(context, 200)
                                                    Toast.makeText(context, "Click again", Toast.LENGTH_SHORT).show()
                                                }
                                                3 -> {
                                                    coconutImage = R.drawable.coconut03
                                                    vibrator(context, 300)
                                                    Toast.makeText(context, "Click again", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                            breakCount++
                                        }
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
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
                    poojaIcons.forEach { i ->
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(i),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        selectedGod = i
                                        showBottomSheet = false
                                        godIcons = false
                                    }
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 4.dp),
                                text = "",      // todo
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
                else {
                    //todo: handle the icons for musics from API
                }
            }
        }
    }
    LaunchedEffect(selectedGod) {
        lamp = false
        breakCount = 1
        coconutImage = R.drawable.coconut
    }
}

private fun vibrator(context: Context, duration: Long) {
    val vibrator = context.getSystemService(Vibrator::class.java)
    vibrator.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            it.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        }
        else it.vibrate(duration)
    }
}


@Preview(showBackground = true, showSystemUi = true,)
@Composable
fun PoojaPreview() {
    ComposeTamilCalendar2025Theme {
        VirtualPooja()
    }
}