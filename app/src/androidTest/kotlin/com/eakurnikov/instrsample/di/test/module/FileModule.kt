package com.eakurnikov.instrsample.di.test.module

import android.app.Instrumentation
import com.eakurnikov.instrsample.di.test.component.FileComponent
import com.eakurnikov.instrsample.env.file.CustomResourceFilesProvider
import com.eakurnikov.instrsample.env.file.CustomResourcesRootDirsProvider
import com.kaspersky.kaspresso.files.dirs.DefaultDirsProvider
import com.kaspersky.kaspresso.files.dirs.DirsProvider
import com.kaspersky.kaspresso.files.resources.ResourceFileNamesProvider
import com.kaspersky.kaspresso.files.resources.ResourcesDirNameProvider
import com.kaspersky.kaspresso.files.resources.ResourcesDirsProvider
import com.kaspersky.kaspresso.files.resources.impl.DefaultResourceFileNamesProvider
import com.kaspersky.kaspresso.files.resources.impl.DefaultResourcesDirNameProvider
import com.kaspersky.kaspresso.files.resources.impl.DefaultResourcesDirsProvider

class FileModule {

    fun createComponent(instrumentation: Instrumentation): FileComponent {
        val dirsProvider: DirsProvider = DefaultDirsProvider(instrumentation)

        val resourcesDirNameProvider: ResourcesDirNameProvider =
            DefaultResourcesDirNameProvider()

        val resourcesDirsProvider: ResourcesDirsProvider =
            DefaultResourcesDirsProvider(dirsProvider, resourcesDirNameProvider)

        val resourceFileNamesProvider: ResourceFileNamesProvider =
            DefaultResourceFileNamesProvider(addTimestamps = false)

        val resourcesRootDirsProvider = CustomResourcesRootDirsProvider()

        val resourceFilesProvider = CustomResourceFilesProvider(
            dirsProvider = dirsProvider,
            resourcesRootDirsProvider = resourcesRootDirsProvider,
            resourcesFileNamesProvider = resourceFileNamesProvider,
            resourcesDirsProvider = resourcesDirsProvider
        )

        return FileComponent(
            dirsProvider = dirsProvider,
            resourcesDirNameProvider = resourcesDirNameProvider,
            resourcesRootDirsProvider = resourcesRootDirsProvider,
            resourcesDirsProvider = resourcesDirsProvider,
            resourceFileNamesProvider = resourceFileNamesProvider,
            resourceFilesProvider = resourceFilesProvider
        )
    }
}
