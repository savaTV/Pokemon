
package com.example.pokemon

import android.content.Context
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import coil.compose.rememberImagePainter

@Composable
fun LottieAnimation(
    assetName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lottieView by remember {
        mutableStateOf(LottieAnimationView(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        })
    }

    LaunchedEffect(assetName) {
        withContext(Dispatchers.Main) {
            try {
                LottieCompositionFactory.fromAsset(context, assetName)
                    .addListener(object : com.airbnb.lottie.OnCompositionLoadedListener {
                        override fun onCompositionLoaded(composition: com.airbnb.lottie.LottieComposition?) {
                            composition?.let {
                                lottieView.setComposition(it)
                                lottieView.playAnimation()
                            }
                        }
                    })
            } catch (e: Exception) {
                // Обработка ошибки загрузки
            }
        }
    }

    Box(modifier = modifier.size(100.dp)) {
        if (lottieView.composition == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            AndroidView(
                factory = {
                    lottieView
                },
                modifier = Modifier.matchParentSize()
            )
        }
    }
}