package com.hevadevelop.newsapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hevadevelop.newsapp.R
import com.hevadevelop.newsapp.ui.theme.Typography
import kotlin.math.ceil

@Composable
fun WaveLayout() {
    val brush = Brush.horizontalGradient(listOf(Color(0xFFEB4E4E), Color(0xFFCF4578)))
    Column(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout {
            val (wave, backgroundColor, image, text) = createRefs()
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(backgroundColor) {
                        top.linkTo(parent.top)
                    },
                onDraw = {
                    drawRect(brush = brush)
                }
            )
            Text(
                text = "News App",
                color = Color.White,
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(text) {
                        end.linkTo(image.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = Wavy(period = 330.dp, amplitude = 35.dp)
                    )
                    .constrainAs(wave) {
                        bottom.linkTo(parent.bottom)
                    }
            )
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.undraw_undraw_notebook_ask4_duaf),
                contentDescription = "",
                modifier = Modifier
                    .width(160.dp)
                    .constrainAs(image) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

class Wavy(
    private val period: Dp,
    private val amplitude: Dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            val wavyPath = Path().apply {
                val halfPeriod = with(density) { period.toPx() } / 2
                val amplitude = with(density) { amplitude.toPx() }
                moveTo(x = -halfPeriod / 2, y = amplitude)
                repeat(ceil(size.width / halfPeriod + 1).toInt()) { i ->
                    relativeQuadraticBezierTo(
                        dx1 = halfPeriod / 2,
                        dy1 = 2 * amplitude * (if (i % 2 == 0) 1 else -1),
                        dx2 = halfPeriod,
                        dy2 = 0f,
                    )
                }
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
            }
            val boundsPath = Path().apply {
                addRect(Rect(offset = Offset.Zero, size = size))
            }
            op(wavyPath, boundsPath, PathOperation.Intersect)

        })
    }
}