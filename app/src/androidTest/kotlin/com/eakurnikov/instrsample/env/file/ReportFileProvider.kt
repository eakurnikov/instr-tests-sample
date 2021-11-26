package com.eakurnikov.instrsample.env.file

import java.io.File

interface ReportFileProvider {
    fun provideReportFile(tag: String, subDir: String? = null): File
}
