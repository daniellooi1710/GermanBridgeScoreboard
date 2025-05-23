package com.germanbridgescoreboard.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LocalExtendedColorScheme = staticCompositionLocalOf<ExtendedColorScheme> {
    error("No ExtendedColorScheme provided")
}

@Immutable
data class ExtendedColorScheme(
    val orange: ColorFamily,
    val yellow: ColorFamily,
    val green: ColorFamily,
    val red: ColorFamily,
    val blue: ColorFamily,
    val purple: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
  orange = ColorFamily(
  orangeLight,
  onOrangeLight,
  orangeContainerLight,
  onOrangeContainerLight,
  ),
  yellow = ColorFamily(
  yellowLight,
  onYellowLight,
  yellowContainerLight,
  onYellowContainerLight,
  ),
  green = ColorFamily(
  greenLight,
  onGreenLight,
  greenContainerLight,
  onGreenContainerLight,
  ),
  red = ColorFamily(
  redLight,
  onRedLight,
  redContainerLight,
  onRedContainerLight,
  ),
  blue = ColorFamily(
  customColor1Light,
  onCustomColor1Light,
  customColor1ContainerLight,
  onCustomColor1ContainerLight,
  ),
  purple = ColorFamily(
  customColor2Light,
  onCustomColor2Light,
  customColor2ContainerLight,
  onCustomColor2ContainerLight,
  ),
)

val extendedDark = ExtendedColorScheme(
  orange = ColorFamily(
  orangeDark,
  onOrangeDark,
  orangeContainerDark,
  onOrangeContainerDark,
  ),
  yellow = ColorFamily(
  yellowDark,
  onYellowDark,
  yellowContainerDark,
  onYellowContainerDark,
  ),
  green = ColorFamily(
  greenDark,
  onGreenDark,
  greenContainerDark,
  onGreenContainerDark,
  ),
  red = ColorFamily(
  redDark,
  onRedDark,
  redContainerDark,
  onRedContainerDark,
  ),
  blue = ColorFamily(
  customColor1Dark,
  onCustomColor1Dark,
  customColor1ContainerDark,
  onCustomColor1ContainerDark,
  ),
  purple = ColorFamily(
  customColor2Dark,
  onCustomColor2Dark,
  customColor2ContainerDark,
  onCustomColor2ContainerDark,
  ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
  orange = ColorFamily(
  orangeLightMediumContrast,
  onOrangeLightMediumContrast,
  orangeContainerLightMediumContrast,
  onOrangeContainerLightMediumContrast,
  ),
  yellow = ColorFamily(
  yellowLightMediumContrast,
  onYellowLightMediumContrast,
  yellowContainerLightMediumContrast,
  onYellowContainerLightMediumContrast,
  ),
  green = ColorFamily(
  greenLightMediumContrast,
  onGreenLightMediumContrast,
  greenContainerLightMediumContrast,
  onGreenContainerLightMediumContrast,
  ),
  red = ColorFamily(
  redLightMediumContrast,
  onRedLightMediumContrast,
  redContainerLightMediumContrast,
  onRedContainerLightMediumContrast,
  ),
  blue = ColorFamily(
  customColor1LightMediumContrast,
  onCustomColor1LightMediumContrast,
  customColor1ContainerLightMediumContrast,
  onCustomColor1ContainerLightMediumContrast,
  ),
  purple = ColorFamily(
  customColor2LightMediumContrast,
  onCustomColor2LightMediumContrast,
  customColor2ContainerLightMediumContrast,
  onCustomColor2ContainerLightMediumContrast,
  ),
)

val extendedLightHighContrast = ExtendedColorScheme(
  orange = ColorFamily(
  orangeLightHighContrast,
  onOrangeLightHighContrast,
  orangeContainerLightHighContrast,
  onOrangeContainerLightHighContrast,
  ),
  yellow = ColorFamily(
  yellowLightHighContrast,
  onYellowLightHighContrast,
  yellowContainerLightHighContrast,
  onYellowContainerLightHighContrast,
  ),
  green = ColorFamily(
  greenLightHighContrast,
  onGreenLightHighContrast,
  greenContainerLightHighContrast,
  onGreenContainerLightHighContrast,
  ),
  red = ColorFamily(
  redLightHighContrast,
  onRedLightHighContrast,
  redContainerLightHighContrast,
  onRedContainerLightHighContrast,
  ),
  blue = ColorFamily(
  customColor1LightHighContrast,
  onCustomColor1LightHighContrast,
  customColor1ContainerLightHighContrast,
  onCustomColor1ContainerLightHighContrast,
  ),
  purple = ColorFamily(
  customColor2LightHighContrast,
  onCustomColor2LightHighContrast,
  customColor2ContainerLightHighContrast,
  onCustomColor2ContainerLightHighContrast,
  ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
  orange = ColorFamily(
  orangeDarkMediumContrast,
  onOrangeDarkMediumContrast,
  orangeContainerDarkMediumContrast,
  onOrangeContainerDarkMediumContrast,
  ),
  yellow = ColorFamily(
  yellowDarkMediumContrast,
  onYellowDarkMediumContrast,
  yellowContainerDarkMediumContrast,
  onYellowContainerDarkMediumContrast,
  ),
  green = ColorFamily(
  greenDarkMediumContrast,
  onGreenDarkMediumContrast,
  greenContainerDarkMediumContrast,
  onGreenContainerDarkMediumContrast,
  ),
  red = ColorFamily(
  redDarkMediumContrast,
  onRedDarkMediumContrast,
  redContainerDarkMediumContrast,
  onRedContainerDarkMediumContrast,
  ),
  blue = ColorFamily(
  customColor1DarkMediumContrast,
  onCustomColor1DarkMediumContrast,
  customColor1ContainerDarkMediumContrast,
  onCustomColor1ContainerDarkMediumContrast,
  ),
  purple = ColorFamily(
  customColor2DarkMediumContrast,
  onCustomColor2DarkMediumContrast,
  customColor2ContainerDarkMediumContrast,
  onCustomColor2ContainerDarkMediumContrast,
  ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
  orange = ColorFamily(
  orangeDarkHighContrast,
  onOrangeDarkHighContrast,
  orangeContainerDarkHighContrast,
  onOrangeContainerDarkHighContrast,
  ),
  yellow = ColorFamily(
  yellowDarkHighContrast,
  onYellowDarkHighContrast,
  yellowContainerDarkHighContrast,
  onYellowContainerDarkHighContrast,
  ),
  green = ColorFamily(
  greenDarkHighContrast,
  onGreenDarkHighContrast,
  greenContainerDarkHighContrast,
  onGreenContainerDarkHighContrast,
  ),
  red = ColorFamily(
  redDarkHighContrast,
  onRedDarkHighContrast,
  redContainerDarkHighContrast,
  onRedContainerDarkHighContrast,
  ),
  blue = ColorFamily(
  customColor1DarkHighContrast,
  onCustomColor1DarkHighContrast,
  customColor1ContainerDarkHighContrast,
  onCustomColor1ContainerDarkHighContrast,
  ),
  purple = ColorFamily(
  customColor2DarkHighContrast,
  onCustomColor2DarkHighContrast,
  customColor2ContainerDarkHighContrast,
  onCustomColor2ContainerDarkHighContrast,
  ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

val androidx.compose.material3.MaterialTheme.extendedColorScheme: ExtendedColorScheme
    @Composable
    get() = LocalExtendedColorScheme.current

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val context = LocalContext.current
    val isDynamic = dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        isDynamic -> {
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
    val extendedColorScheme = when {
        isDynamic -> if (darkTheme) extendedDark else extendedLight // Or make dynamic-compatible if needed
        darkTheme -> extendedDark
        else -> extendedLight
    }

    CompositionLocalProvider(LocalExtendedColorScheme provides extendedColorScheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}

