package br.com.mobile2you.data.mappers

interface Mapper<I, O> {

    fun map(input: I): O

}