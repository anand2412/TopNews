package com.condenast.news

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "AndroidManifest.xml")
abstract class BaseTest {
    protected val context: Context = ApplicationProvider.getApplicationContext()
}