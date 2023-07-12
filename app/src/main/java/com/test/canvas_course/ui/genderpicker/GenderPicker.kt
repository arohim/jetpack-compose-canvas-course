package com.test.canvas_course.ui.genderpicker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.test.canvas_course.R

@Composable
fun GenderPicker(
    modifier: Modifier = Modifier.fillMaxSize(),
    maleGradient: List<Color> = listOf(Color(0xFF6D6DFF), Color.Blue),
    feMaleGradient: List<Color> = listOf(Color(0xFFEA76FF), Color.Magenta),
    distanceBetweenGender: Dp = 50.dp,
    pathScaleFactor: Float = 7f,
    onGenderSelected: (Gender) -> Unit
) {
    var selectedGender by remember {
        mutableStateOf<Gender>(Gender.Female)
    }
    var center by remember {
        mutableStateOf(Offset.Unspecified)
    }
    val malePathString = stringResource(id = R.string.male_path)
    val feMalePathString = stringResource(id = R.string.female_path)

    val malePath = remember {
        PathParser().parsePathString(malePathString).toPath()
    }
    val feMalePath = remember {
        PathParser().parsePathString(feMalePathString).toPath()
    }

    val malePathBounds = remember {
        malePath.getBounds()
    }
    val feMalePathBounds = remember {
        feMalePath.getBounds()
    }

    var maleTranslationOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var feMaleTranslationOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    var currentClickOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val maleSelectionRadius = animateFloatAsState(
        targetValue = if (selectedGender is Gender.Male) 80f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )
    val feMaleSelectionRadius = animateFloatAsState(
        targetValue = if (selectedGender is Gender.Female) 80f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    Canvas(modifier = modifier
        .pointerInput(Unit) {
            detectTapGestures {
                val transformedMaleRect = Rect(
                    maleTranslationOffset,
                    size = malePathBounds.size * pathScaleFactor
                )
                val transformedFeMaleRect = Rect(
                    feMaleTranslationOffset,
                    size = feMalePathBounds.size * pathScaleFactor
                )
                if (selectedGender !is Gender.Male
                    && transformedMaleRect.contains(it)
                ) {
                    currentClickOffset = it
                    selectedGender = Gender.Male
                    onGenderSelected(Gender.Male)
                } else if (
                    selectedGender !is Gender.Female
                    && transformedFeMaleRect.contains(it)
                ) {
                    currentClickOffset = it
                    selectedGender = Gender.Female
                    onGenderSelected(Gender.Female)
                }
            }
        }
    ) {
        center = this.center

        maleTranslationOffset = Offset(
            x = center.x - malePathBounds.width * pathScaleFactor - distanceBetweenGender.toPx() / 2,
            y = center.y - pathScaleFactor * malePathBounds.height / 2f
        )
        feMaleTranslationOffset = Offset(
            x = center.x + distanceBetweenGender.toPx() / 2,
            y = center.y - pathScaleFactor * feMalePathBounds.height / 2f
        )
        val untransformedMaleClickOffset = if (currentClickOffset == Offset.Zero) {
            malePathBounds.center
        } else {
            (currentClickOffset - maleTranslationOffset) / pathScaleFactor
        }
        val untransformedFeMaleClickOffset = if (currentClickOffset == Offset.Zero) {
            feMalePathBounds.center
        } else {
            (currentClickOffset - feMaleTranslationOffset) / pathScaleFactor
        }

        translate(
            left = maleTranslationOffset.x,
            top = maleTranslationOffset.y
        ) {
            scale(
                pathScaleFactor,
                pivot = malePathBounds.topLeft
            ) {
                drawPath(
                    path = malePath,
                    color = Color.LightGray
                )
                clipPath(path = malePath) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = maleGradient,
                            center = untransformedMaleClickOffset,
                            radius = maleSelectionRadius.value + 1f
                        ),
                        radius = maleSelectionRadius.value,
                        center = untransformedMaleClickOffset
                    )
                }
            }
        }
        translate(
            left = feMaleTranslationOffset.x,
            top = feMaleTranslationOffset.y
        ) {
            scale(
                pathScaleFactor,
                pivot = feMalePathBounds.topLeft
            ) {
                drawPath(
                    path = feMalePath,
                    color = Color.LightGray
                )

                clipPath(path = feMalePath) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = feMaleGradient,
                            center = untransformedFeMaleClickOffset,
                            radius = feMaleSelectionRadius.value + 1f
                        ),
                        radius = feMaleSelectionRadius.value,
                        center = untransformedFeMaleClickOffset
                    )
                }
            }
        }
    }

}