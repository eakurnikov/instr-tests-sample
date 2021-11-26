package com.eakurnikov.instrsample.common

import java.util.regex.Pattern

val String.containsThisCaseInsensitivePattern: Pattern
    get() = Pattern.compile(
        String.format("^.*%s.*$", Pattern.quote(this)),
        Pattern.CASE_INSENSITIVE
    )

val String.isThisCaseInsensitivePattern: Pattern
    get() = Pattern.compile(
        Pattern.quote(this),
        Pattern.CASE_INSENSITIVE
    )
