package com.modernicons.icontheme.service

import com.google.gson.Gson
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.scale.ScaleContext
import com.intellij.util.IconUtil
import com.modernicons.icontheme.model.IconTheme
import java.io.InputStreamReader
import javax.swing.Icon

@Service
class IconMappingService {
    private val logger = Logger.getInstance(IconMappingService::class.java)
    private val gson = Gson()
    private var iconTheme: IconTheme? = null
    private val iconCache = mutableMapOf<String, Icon?>()
    
    init {
        loadIconTheme()
    }
    
    private fun loadIconTheme() {
        try {
            val resource = javaClass.getResourceAsStream("/icons/icon-theme.json")
            if (resource != null) {
                InputStreamReader(resource).use { reader ->
                    iconTheme = gson.fromJson(reader, IconTheme::class.java)
                    logger.info("Modern Icon Theme loaded successfully")
                    logger.info("Total icon definitions: ${iconTheme?.iconDefinitions?.size ?: 0}")
                    logger.info("File extensions: ${iconTheme?.fileExtensions?.size ?: 0}")
                    logger.info("Folder names: ${iconTheme?.folderNames?.size ?: 0}")
                }
            } else {
                logger.warn("Icon theme configuration not found at /icons/icon-theme.json")
            }
        } catch (e: Exception) {
            logger.error("Failed to load icon theme", e)
        }
    }
    
    fun getIconForFile(fileName: String, extension: String?): Icon? {
        val theme = iconTheme ?: return null
        
        // Check cache first
        val cacheKey = "$fileName.$extension"
        if (iconCache.containsKey(cacheKey)) {
            return iconCache[cacheKey]
        }
        
        // Try to find icon by exact file name
        theme.fileNames?.get(fileName)?.let { iconId ->
            val icon = loadIcon(iconId)
            iconCache[cacheKey] = icon
            return icon
        }
        
        // Try to find icon by extension
        if (extension != null) {
            theme.fileExtensions?.get(extension.lowercase())?.let { iconId ->
                val icon = loadIcon(iconId)
                iconCache[cacheKey] = icon
                return icon
            }
        }
        
        // Return default file icon
        theme.defaultFileIcon?.let { iconId ->
            val icon = loadIcon(iconId)
            iconCache[cacheKey] = icon
            return icon
        }
        
        return null
    }
    
    fun getIconForFolder(folderName: String): Icon? {
        val theme = iconTheme ?: return null
        
        // Check cache first
        val cacheKey = "folder:$folderName"
        if (iconCache.containsKey(cacheKey)) {
            return iconCache[cacheKey]
        }
        
        // Try to find icon by folder name (case-insensitive)
        val lowerFolderName = folderName.lowercase()
        
        // First try exact match (lowercase)
        theme.folderNames?.get(lowerFolderName)?.let { iconId ->
            logger.info("Found folder icon for: $folderName -> $iconId")
            val icon = loadIcon(iconId)
            iconCache[cacheKey] = icon
            return icon
        }
        
        // Try common variations (e.g., "Test" -> "test", "Tests" -> "tests")
        val variations = listOf(
            lowerFolderName,
            lowerFolderName.removeSuffix("s"),  // Try singular form
            lowerFolderName + "s"  // Try plural form
        ).distinct()
        
        for (variation in variations) {
            theme.folderNames?.get(variation)?.let { iconId ->
                logger.info("Found folder icon for variation: $folderName -> $variation -> $iconId")
                val icon = loadIcon(iconId)
                iconCache[cacheKey] = icon
                return icon
            }
        }
        
        // Return default folder icon
        theme.defaultFolderIcon?.let { iconId ->
            val icon = loadIcon(iconId)
            iconCache[cacheKey] = icon
            return icon
        }
        
        return null
    }
    
    private fun loadIcon(iconId: String): Icon? {
        val theme = iconTheme ?: return null
        val iconDef = theme.iconDefinitions[iconId] ?: return null
        
        // Convert relative path to resource path
        val iconPath = iconDef.iconPath
            .replace("./icons/", "/icons/")
            .replace("\\", "/")
        
        return try {
            // Try to load icon using IconLoader
            val url = javaClass.getResource(iconPath)
            if (url != null) {
                logger.info("Loading icon from: $iconPath")
                // Load icon and scale it to 16x16 (standard IDE icon size)
                val originalIcon = IconLoader.getIcon(iconPath, javaClass.classLoader)
                
                // Scale down to 16x16 for file icons (standard IDE size)
                val scaledIcon = IconUtil.scale(originalIcon, null, 16f / originalIcon.iconWidth.toFloat())
                
                logger.info("Icon loaded and scaled: $iconId (${scaledIcon.iconWidth}x${scaledIcon.iconHeight})")
                scaledIcon
            } else {
                logger.warn("Icon resource not found: $iconPath for iconId: $iconId")
                null
            }
        } catch (e: Exception) {
            logger.error("Failed to load icon: $iconPath for iconId: $iconId", e)
            null
        }
    }
    
    fun clearCache() {
        iconCache.clear()
    }
    
    fun reloadTheme() {
        clearCache()
        loadIconTheme()
    }
}
