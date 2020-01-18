package io.github.bgavyus.splash

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore

class MediaStoreFile(contentResolver: ContentResolver, mode: String, mimeType: String,
					 parent: Uri, name: String) {
	private val mContentResolver = contentResolver
	private val mUri = contentResolver.insert(parent, ContentValues().apply {
		put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
		put(MediaStore.MediaColumns.DISPLAY_NAME, name)
	})!!
	private val mFile = contentResolver.openFileDescriptor(mUri, mode)!!

	val fileDescriptor
		get() = mFile.fileDescriptor!!

	fun close() {
		mFile.checkError()
		mFile.close()
	}

	fun delete() {
		mContentResolver.delete(mUri, null, null)
	}
}
