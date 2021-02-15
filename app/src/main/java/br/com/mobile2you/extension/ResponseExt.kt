package br.com.mobile2you.extension

import retrofit2.Response
import java.lang.Exception

fun <T> Response<T>.bodyOrThrow(): T {
    return body() ?: throw Exception("Empty body")
}