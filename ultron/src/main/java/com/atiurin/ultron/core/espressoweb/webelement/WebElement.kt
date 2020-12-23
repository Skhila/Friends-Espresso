package com.atiurin.ultron.core.espressoweb.webelement

import android.view.View
import androidx.test.espresso.web.assertion.WebAssertion
import androidx.test.espresso.web.assertion.WebViewAssertions.*
import androidx.test.espresso.web.matcher.DomMatchers.elementById
import androidx.test.espresso.web.model.Atoms
import androidx.test.espresso.web.model.ElementReference
import androidx.test.espresso.web.model.Evaluation
import androidx.test.espresso.web.model.WindowReference
import androidx.test.espresso.web.sugar.Web
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms
import androidx.test.espresso.web.webdriver.DriverAtoms.findElement
import androidx.test.espresso.web.webdriver.Locator
import com.atiurin.ultron.core.config.UltronConfig
import com.atiurin.ultron.core.espressoweb.DocumentParserAtom
import com.atiurin.ultron.core.espressoweb.WebInteractionOperationIterationResult
import com.atiurin.ultron.core.espressoweb.WebLifecycle
import com.atiurin.ultron.core.espressoweb.WebOperationResult
import com.atiurin.ultron.core.espressoweb.action.EspressoWebActionType
import com.atiurin.ultron.core.espressoweb.action.WebInteractionAction
import com.atiurin.ultron.core.espressoweb.action.WebInteractionActionExecutor
import com.atiurin.ultron.core.espressoweb.assertion.EspressoWebAssertionType
import com.atiurin.ultron.core.espressoweb.assertion.WebInteractionAssertion
import com.atiurin.ultron.core.espressoweb.assertion.WebInteractionAssertionExecutor
import com.atiurin.ultron.custom.espresso.matcher.ElementWithAttributeMatcher.Companion.withAttribute
import com.atiurin.ultron.exceptions.UltronException
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsString

open class WebElement(
    open val locator: Locator,
    open val value: String,
    open val webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
    open val elementReference: ElementReference? = null,
    open val windowReference: WindowReference? = null
) {

    internal val webViewInteraction: Web.WebInteraction<Void>
    get() {
        val w = if (windowReference != null) onWebView(webViewMatcher).inWindow(windowReference)
        else onWebView(webViewMatcher)
        return w
    }



    internal val webInteractionBlock = {
        if (elementReference == null) {
            webViewInteraction.withElement(findElement(locator, value))
        } else webViewInteraction.withElement(elementReference)
    }

    /** Clears content from an editable element. */
    fun clearElement(
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit =
            (UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit)
    ) = apply {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = { webInteractionBlock().perform(DriverAtoms.clearElement()) },
                    name = "ClearElement with ${locator.type}, value = '$value'",
                    type = EspressoWebActionType.GET_TEXT,
                    description = "ClearElement with ${locator.type}, value = '$value' with timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }

    /** Returns the visible text beneath a given DOM element. */
    fun getText(
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<String>>) -> Unit =
            (UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<String>>) -> Unit)
    ): String {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = { webInteractionBlock().perform(DriverAtoms.getText()) },
                    name = "GetText of ${locator.type}, value = '$value'",
                    type = EspressoWebActionType.GET_TEXT,
                    description = "GetText of ${locator.type}, value = '$value' with timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
        return (result.operationIterationResult as WebInteractionOperationIterationResult<String>).webInteraction?.get()
            ?: throw UltronException("Couldn't getText of locator '${locator.type}' and value '$value'")
    }

    /** Simulates the javascript events to click on a particular element. */
    fun webClick(
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit =
            (UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit)
    ) = apply {
        WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = { webInteractionBlock().perform(DriverAtoms.webClick()) },
                    name = "WebClick on ${locator.type}, value = '$value'",
                    type = EspressoWebActionType.WEB_CLICK,
                    description = "WebClick on ${locator.type}, value = '$value' with timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }

    /** Simulates javascript key events sent to a certain element. */
    fun webKeys(
        text: String,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit
    ) = apply {
        WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = { webInteractionBlock().perform(DriverAtoms.webKeys(text)) },
                    name = "WebKeys text '$text' on ${locator.type}, value = '$value'",
                    type = EspressoWebActionType.WEB_CLICK,
                    description = "WebKeys text '$text' on ${locator.type}, value = '$value' with timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }

    /** Simulates javascript clear and key events sent to a certain element. */
    fun setText(
        text: String,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit
    ) = apply {
        WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = {
                        webInteractionBlock()
                            .perform(DriverAtoms.clearElement())
                            .perform(DriverAtoms.webKeys(text))
                    },
                    name = "WebKeys text '$text' on ${locator.type}, value = '$value'",
                    type = EspressoWebActionType.WEB_CLICK,
                    description = "WebKeys text '$text' on ${locator.type}, value = '$value' with timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }

    /** Returns {@code true} if the desired element is in view after scrolling. */
    fun webScrollIntoView(
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<Boolean>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<Boolean>>) -> Unit
    ): Boolean {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = { webInteractionBlock().perform(DriverAtoms.webScrollIntoView()) },
                    name = "EspressoWeb webScrollIntoView",
                    type = EspressoWebActionType.WEB_KEYS,
                    description = "DriverAtoms webScrollIntoView timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
        return (result.operationIterationResult as WebInteractionOperationIterationResult<Boolean>).webInteraction?.get()
            ?: false
    }

    /** Finds the currently active element in the document. */
    fun selectActiveElement(
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<ElementReference>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<ElementReference>>) -> Unit
    ): ElementReference {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = { webInteractionBlock().perform(DriverAtoms.selectActiveElement()) },
                    name = "EspressoWeb selectActiveElement",
                    type = EspressoWebActionType.WEB_KEYS,
                    description = "DriverAtoms selectActiveElement timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
        return (result.operationIterationResult as WebInteractionOperationIterationResult<ElementReference>).webInteraction?.get()
            ?: throw UltronException("Couldn't select ElementReference to activeElement with locator '${locator.type}' and value '$value'")
    }

    /** Selects a subframe of the currently selected window by it's index. */
    fun selectFrameByIndex(
        index: Int,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit
    ): WindowReference {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = {
                        webInteractionBlock().perform(
                            DriverAtoms.selectFrameByIndex(
                                index
                            )
                        )
                    },
                    name = "EspressoWeb selectFrameByIndex",
                    type = EspressoWebActionType.WEB_KEYS,
                    description = "DriverAtoms selectFrameByIndex '$index' timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
        return (result.operationIterationResult as WebInteractionOperationIterationResult<WindowReference>).webInteraction?.get()
            ?: throw UltronException("Couldn't select WindowReference by index '$index' with locator '${locator.type}' and value '$value'")
    }

    /** Selects a subframe of the given window by it's index. */
    fun selectFrameByIndex(
        index: Int,
        root: WindowReference,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit
    ): WindowReference {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = {
                        webInteractionBlock().perform(
                            DriverAtoms.selectFrameByIndex(
                                index,
                                root
                            )
                        )
                    },
                    name = "EspressoWeb selectFrameByIndex with root",
                    type = EspressoWebActionType.WEB_KEYS,
                    description = "DriverAtoms selectFrameByIndex '$index' with root '$root' timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
        return (result.operationIterationResult as WebInteractionOperationIterationResult<WindowReference>).webInteraction?.get()
            ?: throw UltronException("Couldn't select WindowReference by index '$index' with root with locator '${locator.type}' and value '$value'")
    }

    /** Selects a subframe of the current window by it's name or id. */
    fun selectFrameByIdOrName(
        idOrName: String,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit
    ): WindowReference {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = {
                        webInteractionBlock().perform(
                            DriverAtoms.selectFrameByIdOrName(
                                idOrName
                            )
                        )
                    },
                    name = "EspressoWeb selectFrameByIdOrName '$idOrName'",
                    type = EspressoWebActionType.WEB_KEYS,
                    description = "DriverAtoms selectFrameByIdOrName '$idOrName' timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
        return (result.operationIterationResult as WebInteractionOperationIterationResult<WindowReference>).webInteraction?.get()
            ?: throw UltronException("Couldn't select WindowReference by idOrName '$idOrName' with locator '${locator.type}' and value '$value'")
    }

    /** Selects a subframe of the given window by it's name or id. */
    fun selectFrameByIdOrName(
        idOrName: String,
        root: WindowReference,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit =
            UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<WindowReference>>) -> Unit
    ): WindowReference {
        val result = WebLifecycle.execute(
            WebInteractionActionExecutor(
                WebInteractionAction(
                    webInteractionBlock = {
                        webInteractionBlock().perform(
                            DriverAtoms.selectFrameByIdOrName(
                                idOrName,
                                root
                            )
                        )
                    },
                    name = "EspressoWeb selectFrameByIdOrName with root",
                    type = EspressoWebActionType.WEB_KEYS,
                    description = "DriverAtoms selectFrameByIdOrName '$idOrName' with root '$root' timeout $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
        return (result.operationIterationResult as WebInteractionOperationIterationResult<WindowReference>).webInteraction?.get()
            ?: throw UltronException("Couldn't select WindowReference by idOrName '$idOrName' with root with locator '${locator.type}' and value '$value'")
    }

    /** Asserts that DOM element contains visible text beneath it self. */
    fun containsText(
        text: String,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAssertion<String>>) -> Unit =
            UltronConfig.Espresso.WebInteractionAssertionConfig.resultHandler as (WebOperationResult<WebInteractionAssertion<String>>) -> Unit
    ) = apply {
        WebLifecycle.execute(
            WebInteractionAssertionExecutor(
                WebInteractionAssertion(
                    assertionBlock = {
                        webInteractionBlock().check(
                            webMatches(DriverAtoms.getText(), containsString(text))
                        )
                    },
                    name = "Contains text '$text' on ${locator.type}, value = '$value'",
                    type = EspressoWebAssertionType.CONTAINS_TEXT,
                    description = "WebAssertion containsText '$text' during $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }

    /** Asserts that DOM element has visible text beneath it self. */
    fun hasText(
        text: String,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAssertion<String>>) -> Unit =
            UltronConfig.Espresso.WebInteractionAssertionConfig.resultHandler as (WebOperationResult<WebInteractionAssertion<String>>) -> Unit
    ) = apply {
        WebLifecycle.execute(
            WebInteractionAssertionExecutor(
                WebInteractionAssertion(
                    assertionBlock = {
                        webInteractionBlock().check(
                            webMatches(DriverAtoms.getText(), `is`(text))
                        )
                    },
                    name = "HasText '$text' on ${locator.type}, value = '$value'",
                    type = EspressoWebAssertionType.CONTAINS_TEXT,
                    description = "WebAssertion hasText '$text' during $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }

    /** Asserts that element exists in webView */
    fun exist(
        timeoutMs: Long = UltronConfig.Espresso.ASSERTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAssertion<Void>>) -> Unit =
            UltronConfig.Espresso.WebInteractionAssertionConfig.resultHandler as (WebOperationResult<WebInteractionAssertion<Void>>) -> Unit
    ) = apply {
        WebLifecycle.execute(
            WebInteractionAssertionExecutor(
                WebInteractionAssertion(
                    assertionBlock = {
                        webInteractionBlock()
                    },
                    name = "Assert element with ${locator.type}, value = '$value' exists",
                    type = EspressoWebAssertionType.CONTAINS_TEXT,
                    description = "Assert element exist during $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }



    /** use any webAssertion to assert it safely */
    fun <T> assertThat(
        webAssertion: WebAssertion<T>,
        timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
        resultHandler: (WebOperationResult<WebInteractionAssertion<T>>) -> Unit =
            UltronConfig.Espresso.WebInteractionAssertionConfig.resultHandler as (WebOperationResult<WebInteractionAssertion<T>>) -> Unit
    ) = apply {
        WebLifecycle.execute(
            WebInteractionAssertionExecutor(
                WebInteractionAssertion(
                    assertionBlock = {
                        webInteractionBlock().check(webAssertion)
                    },
                    name = "Assert element with ${locator.type}, value = '$value' exists",
                    type = EspressoWebAssertionType.CONTAINS_TEXT,
                    description = "Assert element exist during $timeoutMs ms",
                    timeoutMs = timeoutMs
                )
            ), resultHandler
        )
    }

    /** Transfors any action or assertion to Boolean value */
    fun isSuccess(
        block: WebElement.() -> Unit
    ): Boolean {
        var success = true
        try {
            block()
        } catch (th: Throwable) {
            success = false
        }
        return success
    }

    /** Removes the Element and Window references from this interaction */
    fun reset() = apply { webViewInteraction.reset() }

    companion object {
        fun className(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(Locator.CLASS_NAME, value, webViewMatcher, elementReference, windowReference)
        }

        fun cssSelector(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(
                Locator.CSS_SELECTOR,
                value,
                webViewMatcher,
                elementReference,
                windowReference
            )
        }

        fun id(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElementWithId {
            return WebElementWithId( value, webViewMatcher, elementReference, windowReference)
        }

        fun linkText(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(Locator.LINK_TEXT, value, webViewMatcher, elementReference, windowReference)
        }

        fun name(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(Locator.NAME, value, webViewMatcher, elementReference, windowReference)
        }

        fun partialLinkText(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(
                Locator.PARTIAL_LINK_TEXT,
                value,
                webViewMatcher,
                elementReference,
                windowReference
            )
        }

        fun tagName(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(Locator.TAG_NAME, value, webViewMatcher, elementReference, windowReference)
        }

        fun xpath(
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(Locator.XPATH, value, webViewMatcher, elementReference, windowReference)
        }

        fun element(
            locator: Locator,
            value: String,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            elementReference: ElementReference? = null,
            windowReference: WindowReference? = null
        ): WebElement {
            return WebElement(locator, value, webViewMatcher, elementReference, windowReference)
        }

        /**
         * Evaluate JS on webView
         */
        fun evalJS(
            script: String,
            timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            windowReference: WindowReference? = null,
            resultHandler: (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit =
                UltronConfig.Espresso.WebInteractionActionConfig.resultHandler as (WebOperationResult<WebInteractionAction<Evaluation>>) -> Unit
        ) {
            val webViewInteraction =
                if (windowReference != null) onWebView(webViewMatcher).inWindow(windowReference)
                else onWebView(webViewMatcher)
            WebLifecycle.execute(
                WebInteractionActionExecutor(
                    WebInteractionAction(
                        webInteractionBlock = { webViewInteraction.perform(Atoms.script(script)) },
                        name = "EspressoWeb Evaluate JS script",
                        type = EspressoWebActionType.SCRIPT,
                        description = "Evaluate JS '$script' during $timeoutMs ms",
                        timeoutMs = timeoutMs
                    )
                ), resultHandler
            )
        }

        /** use any webAssertion to assert it safely */
        fun <T> assertThat(
            webAssertion: WebAssertion<T>,
            timeoutMs: Long = UltronConfig.Espresso.ACTION_TIMEOUT,
            webViewMatcher: Matcher<View> = UltronConfig.Espresso.webViewMatcher,
            windowReference: WindowReference? = null,
            resultHandler: (WebOperationResult<WebInteractionAssertion<T>>) -> Unit =
                UltronConfig.Espresso.WebInteractionAssertionConfig.resultHandler as (WebOperationResult<WebInteractionAssertion<T>>) -> Unit
        ) = apply {
            val webViewInteraction =
                if (windowReference != null) onWebView(webViewMatcher).inWindow(windowReference)
                else onWebView(webViewMatcher)
            WebLifecycle.execute(
                WebInteractionAssertionExecutor(
                    WebInteractionAssertion(
                        assertionBlock = {
                            webViewInteraction.check(webAssertion)
                        },
                        name = "WebView AssertThat $webViewMatcher",
                        type = EspressoWebAssertionType.CONTAINS_TEXT,
                        description = "Assert webView matches custom condition during $timeoutMs ms",
                        timeoutMs = timeoutMs
                    )
                ), resultHandler
            )
        }

    }
}



