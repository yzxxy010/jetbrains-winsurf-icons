package com.winsurf.icontheme.settings

import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurableBase
import com.intellij.openapi.options.ConfigurableUi
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import com.winsurf.icontheme.service.IconMappingService
import javax.swing.*
import java.awt.BorderLayout
import java.awt.FlowLayout

class WinsurfIconSettings : Configurable {
    private var settingsPanel: WinsurfIconSettingsPanel? = null
    
    override fun getDisplayName(): String = "Winsurf Icons"
    
    override fun createComponent(): JComponent {
        settingsPanel = WinsurfIconSettingsPanel()
        return settingsPanel!!.createPanel()
    }
    
    override fun isModified(): Boolean {
        return settingsPanel?.isModified() ?: false
    }
    
    override fun apply() {
        settingsPanel?.apply()
        // Reload icon theme after settings change
        service<IconMappingService>().reloadTheme()
    }
    
    override fun reset() {
        settingsPanel?.reset()
    }
    
    override fun disposeUIResources() {
        settingsPanel = null
    }
}

class WinsurfIconSettingsPanel {
    private lateinit var enabledCheckBox: JBCheckBox
    private lateinit var showFolderIconsCheckBox: JBCheckBox
    private lateinit var showFileIconsCheckBox: JBCheckBox
    private lateinit var useDefaultIconsCheckBox: JBCheckBox
    
    fun createPanel(): JPanel {
        enabledCheckBox = JBCheckBox("Enable Winsurf Icons", true)
        showFolderIconsCheckBox = JBCheckBox("Show custom folder icons", true)
        showFileIconsCheckBox = JBCheckBox("Show custom file icons", true)
        useDefaultIconsCheckBox = JBCheckBox("Use default icons for unknown file types", true)
        
        val mainPanel = JPanel(BorderLayout())
        
        // Header
        val headerPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        headerPanel.add(JBLabel("Customize your IDE appearance with Winsurf icons"))
        
        // Settings
        val settingsPanel = FormBuilder.createFormBuilder()
            .addComponent(enabledCheckBox)
            .addComponentFillVertically(JPanel(), 0)
            .addSeparator()
            .addComponent(showFolderIconsCheckBox)
            .addComponent(showFileIconsCheckBox)
            .addComponent(useDefaultIconsCheckBox)
            .addComponentFillVertically(JPanel(), 0)
            .panel
        
        mainPanel.add(headerPanel, BorderLayout.NORTH)
        mainPanel.add(settingsPanel, BorderLayout.CENTER)
        
        // Info panel
        val infoPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        infoPanel.border = JBUI.Borders.emptyTop(10)
        infoPanel.add(JBLabel("Note: Restart IDE to fully apply icon changes"))
        mainPanel.add(infoPanel, BorderLayout.SOUTH)
        
        // Add listeners
        enabledCheckBox.addActionListener {
            val enabled = enabledCheckBox.isSelected
            showFolderIconsCheckBox.isEnabled = enabled
            showFileIconsCheckBox.isEnabled = enabled
            useDefaultIconsCheckBox.isEnabled = enabled
        }
        
        return mainPanel
    }
    
    fun isModified(): Boolean {
        // In a real implementation, you would compare with stored settings
        return false
    }
    
    fun apply() {
        // In a real implementation, you would save settings to persistent storage
        // For now, just clear the icon cache
        service<IconMappingService>().clearCache()
    }
    
    fun reset() {
        enabledCheckBox.isSelected = true
        showFolderIconsCheckBox.isSelected = true
        showFileIconsCheckBox.isSelected = true
        useDefaultIconsCheckBox.isSelected = true
    }
}
