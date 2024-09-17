package se.evinja.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.evinja.scaffold.ui.theme.ScaffoldTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        content()
    }

    private fun content() {
        setContent {
            ScaffoldTheme {
                Scaffold()
            }
        }
    }

    @Composable
    fun Scaffold() {
        var presses by remember { mutableIntStateOf(0) }

        val scrollBehavior =
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            bottomBar = { ScaffoldBottomBar() },
            topBar = {
                ScaffoldTopBar(
                    "Top bar $presses",
                    scrollBehavior,
                    { finish() },
                    { presses++ })
            }) { innerPadding ->

            MessageList(innerPadding)
        }
    }

    @Composable
    fun MessageList(padding: PaddingValues) {
        LazyColumn(contentPadding = padding) {
            for (i in 0..100) {
                item { MessageRow("Message $i") }
            }
        }
    }

    @Composable
    fun MessageRow(message: String, modifier: Modifier = Modifier) {
        Row(Modifier.padding(12.dp)) { Text(message, modifier = modifier) }
    }

    @Composable
    fun ScaffoldTopBar(
        title: String,
        scrollBehavior: TopAppBarScrollBehavior,
        navigateBack: () -> Unit,
        more: () -> Unit
    ) {
        MediumTopAppBar(
            title = { Text(title) },
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            scrollBehavior = scrollBehavior,
            actions = {
                IconButton(onClick = { more.invoke() }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Menu"
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navigateBack.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
    }

    @Composable
    fun ScaffoldBottomBar() {
        BottomAppBar(
            actions = { IconButton(onClick = {}) { Icon(Icons.Filled.Check, "Check") } },
            floatingActionButton = { ActionButton() },
        )
    }

    @Composable
    fun ActionButton() {
        FloatingActionButton(
            onClick = {},
            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
        ) { Icon(Icons.Filled.KeyboardArrowUp, "Up") }
    }

    @Preview(showBackground = true)
    @Composable
    fun topBarPreview() {
        ScaffoldTheme {
            ScaffoldTopBar("Top bar", TopAppBarDefaults.pinnedScrollBehavior(), {}, {})
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MessageListPreview() {
        LazyColumn {
            for (i in 0..100) {
                item { MessageRow("Message $i") }
            }
        }
    }

}
