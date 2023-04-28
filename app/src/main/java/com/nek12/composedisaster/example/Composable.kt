package com.nek12.composedisaster.example

class DrawCommand {

    fun draw() = Unit
}

abstract class Composable(
    private vararg var params: Any?,
) {

    init {
        recompose(params)
    }

    private val drawCache: MutableList<DrawCommand> = mutableListOf()

    public fun paramsHaveChanged(vararg newParams: Any?) = params.contentEquals(newParams)

    fun recompose(vararg newParams: Any?) {
        if (paramsHaveChanged(newParams)) {
            drawCache.clear()
            newParams.forEach {
                if (it is Composable) {
                    it.recompose(newParams)
                }
            }
            drawCache.add(DrawCommand())
        }
    }

    fun draw() = drawCache.forEach { it.draw() }
}
