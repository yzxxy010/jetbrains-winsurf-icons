package com.winsurf.icontheme.model

import com.google.gson.annotations.SerializedName

data class IconTheme(
    @SerializedName("iconDefinitions")
    val iconDefinitions: Map<String, IconDefinition>,
    
    @SerializedName("file")
    val defaultFileIcon: String? = null,
    
    @SerializedName("folder")
    val defaultFolderIcon: String? = null,
    
    @SerializedName("fileExtensions")
    val fileExtensions: Map<String, String>? = null,
    
    @SerializedName("fileNames")
    val fileNames: Map<String, String>? = null,
    
    @SerializedName("folderNames")
    val folderNames: Map<String, String>? = null,
    
    @SerializedName("languageIds")
    val languageIds: Map<String, String>? = null
)

data class IconDefinition(
    @SerializedName("iconPath")
    val iconPath: String
)
