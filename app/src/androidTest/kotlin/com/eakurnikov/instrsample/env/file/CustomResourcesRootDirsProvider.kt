package com.eakurnikov.instrsample.env.file

import com.kaspersky.kaspresso.files.resources.ResourcesRootDirsProvider
import com.kaspersky.kaspresso.files.resources.impl.DefaultResourcesRootDirsProvider
import java.io.File

class CustomResourcesRootDirsProvider :
    ResourcesRootDirsProvider by DefaultResourcesRootDirsProvider() {
    val reportFile = File("report")
}
