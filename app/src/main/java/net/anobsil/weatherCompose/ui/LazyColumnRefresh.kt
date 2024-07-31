    package net.anobsil.weatherCompose.ui

    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.wrapContentHeight
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.LazyListState
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.lazy.rememberLazyListState
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier

    @Composable
    fun <T> LazyColumnRefresh(
        items: List<T>,
        content: @Composable (T) -> Unit,
        lazyListState: LazyListState = rememberLazyListState(),
    ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.wrapContentHeight().fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(items) { item ->
                    content(item)
                }
            }

    }