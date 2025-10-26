package com.modernicons.icontheme

import com.intellij.ide.IconProvider
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.modernicons.icontheme.service.IconMappingService
import javax.swing.Icon

class ModernIconProvider : IconProvider(), DumbAware {
    
    companion object {
        private val logger = Logger.getInstance(ModernIconProvider::class.java)
    }
    
    private val iconService = service<IconMappingService>()
    
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        return try {
            val result = when (element) {
                is PsiFile -> {
                    logger.info("Getting icon for file: ${element.virtualFile?.name}")
                    getFileIcon(element)
                }
                is PsiDirectory -> {
                    logger.info("Getting icon for directory: ${element.virtualFile?.name}")
                    getDirectoryIcon(element)
                }
                else -> null
            }
            
            // For testing: if no custom icon found, try to load a default one
            if (result == null && element is PsiFile) {
                try {
                    val testIcon = IconLoader.getIcon("/icons/code.svg", javaClass.classLoader)
                    val scaledIcon = com.intellij.util.IconUtil.scale(testIcon, null, 16f / testIcon.iconWidth.toFloat())
                    logger.info("Using default test icon (16x16) for: ${element.virtualFile?.name}")
                    return scaledIcon
                } catch (e: Exception) {
                    logger.debug("Could not load default test icon")
                }
            }
            
            result
        } catch (e: Exception) {
            logger.error("Error getting icon for element: ${element.javaClass.simpleName}", e)
            null
        }
    }
    
    private fun getFileIcon(file: PsiFile): Icon? {
        val virtualFile = file.virtualFile ?: return null
        
        // Get file name and extension
        val fileName = virtualFile.name
        val extension = virtualFile.extension
        
        // Try to get custom icon from our theme
        val icon = iconService.getIconForFile(fileName, extension)
        if (icon != null) {
            logger.debug("Custom icon found for file: $fileName")
        }
        return icon
    }
    
    private fun getDirectoryIcon(directory: PsiDirectory): Icon? {
        val virtualFile = directory.virtualFile
        val folderName = virtualFile.name
        
        // Special handling for common folder names
        val icon = iconService.getIconForFolder(folderName)
        if (icon != null) {
            logger.debug("Custom icon found for folder: $folderName")
        }
        return icon
    }
}
