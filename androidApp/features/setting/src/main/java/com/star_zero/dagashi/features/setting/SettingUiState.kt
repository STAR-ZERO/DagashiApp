package com.star_zero.dagashi.features.setting

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.star_zero.dagashi.shared.model.DarkThemeType

@Immutable
data class SettingUiState constructor(
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    val isSupportDynamic: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    val isOpenLinkInApp: Boolean = false,
    val darkThemeType: DarkThemeType = DarkThemeType.DEVICE,
    val isDynamicThemeEnabled: Boolean = false,
)

data class DarkThemeOption(
    val type: DarkThemeType,
    @StringRes val name: Int,
)

internal val darkThemeOptions = listOf(
    DarkThemeOption(
        DarkThemeType.DEVICE,
        R.string.setting_dark_theme_option_device
    ),
    DarkThemeOption(
        DarkThemeType.ON,
        R.string.setting_dark_theme_option_on
    ),
    DarkThemeOption(
        DarkThemeType.OFF,
        R.string.setting_dark_theme_option_off
    )
)
