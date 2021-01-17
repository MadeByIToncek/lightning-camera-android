package io.github.bgavyus.lightningcamera.extensions

import android.media.MediaCodec
import android.media.MediaFormat
import android.os.Handler
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow

fun MediaCodec.encoderEvents(handler: Handler) = callbackFlow<EncoderEvent> {
    val callback = object : MediaCodec.Callback() {
        override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) =
            sendBlocking(EncoderEvent.FormatChanged(format))

        override fun onOutputBufferAvailable(
            codec: MediaCodec,
            index: Int,
            info: MediaCodec.BufferInfo,
        ) = sendBlocking(EncoderEvent.BufferAvailable(index, info))

        override fun onError(codec: MediaCodec, error: MediaCodec.CodecException) = cancel(error)
        override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {}
    }

    setCallback(callback, handler)
    awaitClose { setCallback(null) }
}

sealed class EncoderEvent {
    data class FormatChanged(val format: MediaFormat) : EncoderEvent()
    data class BufferAvailable(val index: Int, val info: MediaCodec.BufferInfo) : EncoderEvent()
}