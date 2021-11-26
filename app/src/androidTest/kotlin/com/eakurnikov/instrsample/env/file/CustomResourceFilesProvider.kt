package com.eakurnikov.instrsample.env.file

import com.kaspersky.kaspresso.files.dirs.DirsProvider
import com.kaspersky.kaspresso.files.extensions.FileExtension
import com.kaspersky.kaspresso.files.resources.ResourceFileNamesProvider
import com.kaspersky.kaspresso.files.resources.ResourceFilesProvider
import com.kaspersky.kaspresso.files.resources.ResourcesDirsProvider
import com.kaspersky.kaspresso.files.resources.impl.DefaultResourceFilesProvider
import java.io.File

class CustomResourceFilesProvider(
    private val dirsProvider: DirsProvider,
    private val resourcesRootDirsProvider: CustomResourcesRootDirsProvider,
    private val resourcesFileNamesProvider: ResourceFileNamesProvider,
    resourcesDirsProvider: ResourcesDirsProvider
) : ResourceFilesProvider by DefaultResourceFilesProvider(
    resourcesRootDirsProvider,
    resourcesDirsProvider,
    resourcesFileNamesProvider
), ReportFileProvider {

    override fun provideReportFile(tag: String, subDir: String?): File {
        val resFileName = resourcesFileNamesProvider.getFileName(tag, FileExtension.TXT.toString())
        return dirsProvider.provideNew(resourcesRootDirsProvider.reportFile).resolve(resFileName)
    }
}
