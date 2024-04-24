package com.br.design_system.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = ColorApp.primaryDark,
    onPrimary = ColorApp.textOnPrimary,
    secondary = ColorApp.primaryDark,
    onSecondary = ColorApp.textOnPrimary,
    error = ColorApp.error,
    onError = ColorApp.onError,
    background = ColorApp.backgroundDark,
    onBackground = ColorApp.textOnBackgroundDark,
    surface = ColorApp.surfaceDark,
    onSurface = ColorApp.textOnBackgroundDark,
    outline = ColorApp.outlineDark
)

val LightColorScheme = lightColorScheme(
    primary = ColorApp.primary,
    onPrimary = ColorApp.textOnPrimary,
    secondary = ColorApp.primary,
    onSecondary = ColorApp.textOnPrimary,
    error = ColorApp.error,
    onError = ColorApp.onError,
    background = ColorApp.background,
    onBackground = ColorApp.textOnBackground,
    surface = ColorApp.surface,
    onSurface = ColorApp.textOnBackground,
    outline = ColorApp.outline
)

@Composable
fun MlChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}