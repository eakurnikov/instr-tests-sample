package com.eakurnikov.instrsample.di.test.component

import com.eakurnikov.instrsample.env.file.CustomResourceFilesProvider
import com.eakurnikov.instrsample.env.kaspresso.KaspressoBuildStage
import com.kaspersky.kaspresso.files.dirs.DirsProvider
import com.kaspersky.kaspresso.files.resources.ResourceFileNamesProvider
import com.kaspersky.kaspresso.files.resources.ResourcesDirNameProvider
import com.kaspersky.kaspresso.files.resources.ResourcesDirsProvider
import com.kaspersky.kaspresso.files.resources.ResourcesRootDirsProvider
import com.kaspersky.kaspresso.kaspresso.Kaspresso

class FileComponent(
    val dirsProvider: DirsProvider,
    val resourcesDirNameProvider: ResourcesDirNameProvider,
    val resourcesRootDirsProvider: ResourcesRootDirsProvider,
    val resourcesDirsProvider: ResourcesDirsProvider,
    val resourceFileNamesProvider: ResourceFileNamesProvider,
    val resourceFilesProvider: CustomResourceFilesProvider
) : TestComponent {

    override fun inject(builder: Kaspresso.Builder, buildStage: KaspressoBuildStage) {
        if (buildStage == KaspressoBuildStage.PRE) {
            builder.dirsProvider = dirsProvider
            builder.resourcesRootDirsProvider = resourcesRootDirsProvider
            builder.resourcesDirNameProvider = resourcesDirNameProvider
            builder.resourcesDirsProvider = resourcesDirsProvider
            builder.resourceFileNamesProvider = resourceFileNamesProvider
            builder.resourceFilesProvider = resourceFilesProvider
        }
    }
}
