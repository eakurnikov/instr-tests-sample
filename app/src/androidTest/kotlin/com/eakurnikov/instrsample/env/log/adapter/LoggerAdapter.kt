package com.eakurnikov.instrsample.env.log.adapter

interface LoggerAdapter {
    fun i(tag: String?, text: String)
    fun d(tag: String?, text: String)
    fun w(tag: String?, text: String)
    fun e(tag: String?, text: String)
}
