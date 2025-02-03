package com.dljonesapps.fetchtest.ui.model

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val resId: Int) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> {
                val context = LocalContext.current
                try {
                    context.getString(resId)
                } catch (e: Resources.NotFoundException) {
                    ""
                }
            }
        }
    }

    fun asString(context: Context?): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> try {
                context?.getString(resId) ?: ""
            } catch (e: Resources.NotFoundException) {
                ""
            }
        }
    }
}
