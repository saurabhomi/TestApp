package com.mvi.common

interface Mapper<I, O> {
    fun from(i: I): O
}