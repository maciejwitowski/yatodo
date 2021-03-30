package main.java.com.example.server.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonParser {
    val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    inline fun <reified T> fromJson(json: String) =
        moshi.adapter(T::class.java).fromJson(json)
}