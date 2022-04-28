package com.star_zero.dagashi.shared.platform

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class PlatformParcelize()

expect interface PlatformParcelable
