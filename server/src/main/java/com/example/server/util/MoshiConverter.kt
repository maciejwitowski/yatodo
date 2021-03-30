package com.ryanharter.ktor.moshi

import com.squareup.moshi.Moshi
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import okio.buffer
import okio.source

// TODO Not used for now. Remove?
class MoshiConverter(private val moshi: Moshi = Moshi.Builder().build()) : ContentConverter {
    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        val channel = request.value as? ByteReadChannel ?: return null
        val source = channel.toInputStream().source().buffer()
        val type = request.type
        return moshi.adapter(type.javaObjectType).fromJson(source)
    }

    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? {
        return TextContent(
            moshi.adapter(value.javaClass).toJson(value),
            contentType.withCharset(context.call.suitableCharset())
        )
    }
}

/**
 * Registers the supplied Moshi instance as a content converter for `application/json`
 * data.
 */
fun ContentNegotiation.Configuration.moshi(moshi: Moshi = Moshi.Builder().build()) {
    val converter = MoshiConverter(moshi)
    register(ContentType.Application.Json, converter)
}

/**
 * Creates a new Moshi instance and registers it as a content converter for
 * `application/json` data.  The supplied block is used to configure the builder.
 */
fun ContentNegotiation.Configuration.moshi(block: Moshi.Builder.() -> Unit) {
    val builder = Moshi.Builder()
    builder.apply(block)
    val converter = MoshiConverter(builder.build())
    register(ContentType.Application.Json, converter)
}
