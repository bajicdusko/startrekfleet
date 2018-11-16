package com.bajicdusko.startrekfleet

import android.content.Intent
import android.view.View
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.*
import com.bajicdusko.startrekfleet.base.BaseViewHolder
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.bajicdusko.startrekfleet.R.id.activity_main_shipclasses as shipClassesList


/**
 * Created by Dusko Bajic on 11.08.18Ωz.
 * GitHub @bajicdusko
 */

@RunWith(AndroidJUnit4::class)
open class MainActivityUITest {

  @JvmField
  @Rule
  val mainActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

  @Test
  fun mainActivity_shouldShowShipClassesList_whenTheMainScreenIsOpened() {

    verifyThat(shipClassesList).isVisible()

    onView(withId(R.id.activity_main_shipclasses))
        .perform(RecyclerViewActions.scrollToPosition<BaseViewHolder>(22))

    verifyThat("Galaxy").isVisible()
  }

  @Test
  fun mainActivityUiAutomator_shouldShowShipClasses_whenTheMainScreenIsOpened(){

    /**
     * Getting the instrumentation instance
     */
    val instrumentation = InstrumentationRegistry.getInstrumentation()

    /**
     * Instantiating a [UiDevice]
     */
    val uiDevice = UiDevice.getInstance(instrumentation)

    /**
     * Getting the target context. targetContext is the context of the application.
     * [instrumentation.context] is a test context.
     */
    val appContext = instrumentation.targetContext

    /**
     * Setting up the intent in order to start the application.
     */
    val intent = appContext.packageManager.getLaunchIntentForPackage(appContext.packageName)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

    /**
     * Starting the launch activity.
     */
    appContext.startActivity(intent)

    /**
     * Waiting for the object with application package to appear on the screen.
     */
    uiDevice.wait(Until.findObject(By.pkg(appContext.packageName)), 5000)

    /**
     * Defining a [UiObject] match (does not have to exist on the screen, it's just a definition)
     */
    val auroraUiObject = uiDevice.findObject(UiSelector().text("Akira"))

    /**
     * Waiting for 5s the defined object to appear on the screen. If it does not appear on the screen,
     * the method returns false.
     */
    val akiraExistsOnScreen = auroraUiObject.waitForExists(5000)

    /**
     * Asserting that the object appeared on the screen.
     */
    Assert.assertEquals(true, akiraExistsOnScreen)
  }
}

fun verifyThat(viewId: Int): ViewInteraction = onView(withId(viewId))
fun verifyThat(viewText: String): ViewInteraction = onView(withText(viewText))
fun ViewInteraction.isVisible() = this.check(ViewAssertions.matches(isDisplayed()))

fun viewById(id: (Int)): Matcher<View> = withId(id)