package com.bogdan.codeforceswatcher.features.news.cells

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Typeface
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.buttons.CommonButton
import com.bogdan.codeforceswatcher.components.compose.buttons.SmallButton
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun PostPinnedView() {
    val visibleState by remember { mutableStateOf(false) }

    if (visibleState) {
        VisibleView()
    } else {
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

            HiddenTitleView()
        }
    }
}

@Composable
private fun HiddenTitleView() = Column(
    modifier = Modifier
        .offset(y = (-4).dp)
) {
    GradientText(
        "Update 3.0: What’s New?",
        fontSize = 60f,
        lineHeight = 68f,
        origin = Offset(0f, 68f),
        shaderFrom = Offset(0f, 0f),
        shaderTo = Offset(800f, 0f),
        modifier = Modifier.height(20.dp)
    )

    Text(
        text = "Click here to see more",
        style = AlgoismeTheme.typography.hintSemiBold,
        color = AlgoismeTheme.colors.primary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun VisibleView() = Box(
    modifier = Modifier
        .height(230.dp)
        .clip(AlgoismeTheme.shapes.medium)
) {
    Image(
        painter = painterResource(R.drawable.ic_pinned_post_background),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )

    Box(modifier = Modifier.background(
        brush = Brush.radialGradient(
            0.42f to AlgoismeTheme.colors.black.copy(alpha = 0f),
            0.88f to AlgoismeTheme.colors.black.copy(alpha = 0.6f),
            center = Offset(x = 800f, y = 200f),
            radius = 600f
        )
    )) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_cross_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(96.dp))

            GradientText(
                "Update 3.0:",
                "What’s New?",
                fontSize = 100f,
                lineHeight = 110f,
                origin = Offset(0f, 0f),
                shaderFrom = Offset(0f, 40f),
                shaderTo = Offset(520f, 0f),
                modifier = Modifier.height(30.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Huge redesign, mentors & more!",
                    style = AlgoismeTheme.typography.primarySemiBold,
                    color = AlgoismeTheme.colors.white,
                    modifier = Modifier.width(180.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                CommonButton(
                    label = "See details",
                    modifier = Modifier.defaultMinSize(minWidth = 80.dp, minHeight = 32.dp),
                    textStyle = AlgoismeTheme.typography.buttonSemiBold.copy(fontSize = 13.sp),
                    backgroundColor = AlgoismeTheme.colors.primary,
                    labelColor = AlgoismeTheme.colors.secondary
                ) { }
            }
        }
    }
}

@Composable
private fun GradientText(
    vararg names: String,
    fontSize: Float,
    lineHeight: Float,
    origin: Offset,
    shaderFrom: Offset,
    shaderTo: Offset,
    modifier: Modifier = Modifier
) {

    val paint = Paint().asFrameworkPaint()

    val gradientShader: Shader = LinearGradientShader(
        from = shaderFrom,
        to = shaderTo,
        colors = listOf(AlgoismeTheme.colors.red, AlgoismeTheme.colors.yellow, AlgoismeTheme.colors.blue),
        colorStops = listOf(0f, 0.5f, 1f)
    )

    Canvas(modifier) {
        paint.apply {
            isAntiAlias = true
            textSize = fontSize
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            letterSpacing = -0.05f
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        }
        drawIntoCanvas { canvas ->
            names.forEachIndexed { index, name ->
                canvas.save()
                canvas.nativeCanvas.drawText(name, origin.x, origin.y + lineHeight * index, paint)
                canvas.restore()
                paint.shader = gradientShader
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                paint.maskFilter = null
                canvas.nativeCanvas.drawText(name, origin.x, origin.y + lineHeight * index, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
                canvas.nativeCanvas.drawText(name, origin.x, origin.y +lineHeight * index, paint)
            }
        }
        paint.reset()
    }
}