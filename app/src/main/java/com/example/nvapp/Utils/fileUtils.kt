package com.example.nvapp.Utils

import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import androidx.media3.common.MediaMetadata

fun extractThumbNail(assetFileDescriptor: AssetFileDescriptor, atTime: Int): Bitmap?{
    var bitmap : Bitmap? = null
    val retriever: MediaMetadataRetriever
    if (assetFileDescriptor != null && assetFileDescriptor.fileDescriptor.valid()) {
        retriever = MediaMetadataRetriever()
        assetFileDescriptor.apply {
            retriever.setDataSource(
                fileDescriptor,
                startOffset,
                length
            )
        }
        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            retriever.getScaledFrameAtTime(
                atTime.toLong(),
                MediaMetadataRetriever.OPTION_CLOSEST,
                1280,
                720
            )
        } else {
            retriever.getFrameAtTime(atTime.toLong())
        }
        assetFileDescriptor.close()
        retriever.release()
    }
    return bitmap
}