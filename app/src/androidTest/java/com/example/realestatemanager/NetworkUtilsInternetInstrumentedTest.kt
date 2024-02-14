package com.example.realestatemanager

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.realestatemanager.data.local.service.Utils
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworkUtilsInternetInstrumentedTest {

    //Test if internet connection is available with a simulated context from actual running device
    @Test
    fun testIsInternetAvailable() {
        // Get the simulated context
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        val isConnected: Boolean = Utils.isInternetAvailable(context)

        assertTrue(isConnected)
    }
}