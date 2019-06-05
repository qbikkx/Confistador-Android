package dev.qbikkx.coreui.di


import javax.inject.Scope

/**
 * Custom [Scope] that defines FlowFragment-level lifetime for dependencies
 */
@Scope annotation class FlowScope

/**
 * Custom [Scope] that defines Fragment-level lifetime for dependencies
 */
@Scope annotation class ScreenScope