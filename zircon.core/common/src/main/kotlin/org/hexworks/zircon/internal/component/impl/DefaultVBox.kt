package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.TitleHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate

open class DefaultVBox(componentMetadata: ComponentMetadata,
                       initialTitle: String,
                       private val renderingStrategy: ComponentRenderingStrategy<VBox>)
    : VBox, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TitleHolder by TitleHolder.create(initialTitle) {

    init {
        render()
    }

    override fun addComponent(component: Component) {
        // move to proper place
        TODO()
    }

    override fun removeComponent(component: Component): Boolean {
        // reorder after removal
        TODO()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme ($colorTheme) to VBox (id=${id.abbreviate()}).")
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.primaryBackgroundColor)
                        .build())
                .build().also { css ->
                    componentStyleSet = css
                    render()
                    children.forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    final override fun render() {
        LOGGER.debug("VBox (id=${id.abbreviate()}, visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(VBox::class)
    }
}