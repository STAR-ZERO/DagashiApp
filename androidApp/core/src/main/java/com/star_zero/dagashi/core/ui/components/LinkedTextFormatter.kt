package com.star_zero.dagashi.core.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

// Ref: https://github.com/android/compose-samples/blob/e5e45ee2adfa9b050309aae1d25aad76b5452aca/Jetchat/app/src/main/java/com/example/compose/jetchat/conversation/MessageFormatter.kt
val symbolPattern by lazy {
    Regex("""https?://[^\s\t\n]+""")
}

fun formatLinkedText(text: String, linkColor: Color): AnnotatedString {
    val tokens = symbolPattern.findAll(text)

    return buildAnnotatedString {
        var cursorPosition = 0

        for (token in tokens) {
            append(text.slice(cursorPosition until token.range.first))

            val annotatedString = AnnotatedString(
                text = token.value,
                spanStyle = SpanStyle(
                    color = linkColor
                )
            )

            append(annotatedString)

            addStringAnnotation(
                tag = "",
                start = token.range.first,
                end = token.range.last,
                annotation = token.value
            )

            cursorPosition = token.range.last + 1
        }

        if (!tokens.none()) {
            append(text.slice(cursorPosition..text.lastIndex))
        } else {
            append(text)
        }
    }
}
