package com.bogdan.codeforceswatcher.features.news.cells

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Canvas
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun PostPinnedView() {
    val visibleState by remember { mutableStateOf(false) }

    if (!visibleState) {
        HiddenView()
    }
}

@Composable
private fun HiddenView() = Box(
    modifier = Modifier
        .height(60.dp)
        .clip(AlgoismeTheme.shapes.medium)
) {
    Image(
        painter = painterResource(R.drawable.ic_pinned_post_background),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier.background(
            brush = Brush.linearGradient(
                0.26f to AlgoismeTheme.colors.black.copy(alpha = 0.65f),
                0.92f to AlgoismeTheme.colors.black.copy(alpha = 0.2f),
                start = Offset(0f, Float.POSITIVE_INFINITY),
                end = Offset(Float.POSITIVE_INFINITY, 0f)
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            HiddenTitleView(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun HiddenTitleView(modifier: Modifier) = Column(
    modifier = modifier
        .offset(y = (-4).dp)
) {
    GradientText("Update 3.0: What’s New?")

    Text(
        text = "Click here to see more",
        style = AlgoismeTheme.typography.hintSemiBold,
        color = AlgoismeTheme.colors.primary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun GradientText(name: String, modifier: Modifier = Modifier) {

    val paint = Paint().asFrameworkPaint()

    val gradientShader: Shader = LinearGradientShader(
        from = Offset(0f, 0f),
        to = Offset(240f, 0f),
        colors = listOf(AlgoismeTheme.colors.red, AlgoismeTheme.colors.yellow, AlgoismeTheme.colors.blue)
    )

    Canvas(modifier.height(20.dp)) {
        paint.apply {
            isAntiAlias = true
            textSize = 60f
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            letterSpacing = -0.05f
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        }
        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.nativeCanvas.drawText(name, 0f, 68f, paint)
            canvas.restore()
            paint.shader = gradientShader
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            paint.maskFilter = null
            canvas.nativeCanvas.drawText(name, 0f, 68f, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
            canvas.nativeCanvas.drawText(name, 0f, 68f, paint)
        }
        paint.reset()
    }
}