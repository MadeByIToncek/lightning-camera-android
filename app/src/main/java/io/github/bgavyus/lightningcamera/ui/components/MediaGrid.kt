package io.github.bgavyus.lightningcamera.ui.components

import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import io.github.bgavyus.lightningcamera.storage.MediaMetadata
import io.github.bgavyus.lightningcamera.storage.MediaMetadataProvider

@Composable
fun MediaGrid(
    metadataList: List<MediaMetadata>,
    metadataProvider: MediaMetadataProvider,
    uriOpener: UriHandler,
) {
    LazyVerticalGrid(GridCells.Adaptive(minSize = 120.dp)) {
        items(metadataList) { metadata ->
            MediaCell(metadata, metadataProvider, uriOpener)
        }
    }
}
