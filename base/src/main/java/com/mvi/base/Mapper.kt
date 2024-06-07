package com.mvi.base

interface Mapper<I, O> {
    fun from(i: I?): O
}