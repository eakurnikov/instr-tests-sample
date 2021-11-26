package com.eakurnikov.instrsample.env.log.adapter

interface LogTags {

    fun interface Source : LogTags {
        fun packTags(levelTag: String, textTag: String?): Array<String?>

        class Impl : Source {
            override fun packTags(levelTag: String, textTag: String?): Array<String?> =
                arrayOf(levelTag, textTag)
        }
    }

    fun interface Receiver : LogTags {
        fun Array<Any?>?.extractTags(): Pair<String, String?>

        class Impl : Receiver {
            override fun Array<Any?>?.extractTags(): Pair<String, String?> {
                requireNotNull(this)
                return Pair(get(0) as String, get(1) as String?)
            }
        }
    }
}
