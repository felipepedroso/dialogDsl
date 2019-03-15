package com.cookpad.dialogDsl

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class DialogButtonInitializer {
    @StringRes
    var textResourceId: Int? = null

    var text: CharSequence? = null

    var onClickListener: () -> Unit = { }
}

class DialogInitializer {
    @StringRes
    var messageResourceId: Int? = null

    var messageText: CharSequence? = null

    @StringRes
    var titleResourceId: Int? = null

    var titleText: CharSequence? = null

    var isCancelable: Boolean = true

    var customView: View? = null

    internal var positiveButtonInitializer: DialogButtonInitializer? = null

    fun positiveButton(initializeAction: DialogButtonInitializer.() -> Unit) {
        positiveButtonInitializer = DialogButtonInitializer().also(initializeAction)
    }

    internal var negativeButtonInitializer: DialogButtonInitializer? = null

    fun negativeButton(initializeAction: DialogButtonInitializer.() -> Unit) {
        negativeButtonInitializer = DialogButtonInitializer().also(initializeAction)
    }

    var onDismissListener: () -> Unit = { }

    var onCancelListener: () -> Unit = { }
}

fun Context.buildDialog(
    initializeAction: DialogInitializer.() -> Unit
): AlertDialog {
    val initializer = DialogInitializer()
    initializeAction(initializer)

    return AlertDialog.Builder(this)
        .initialize(initializer)
        .create()
}

fun Fragment.buildDialog(initializeAction: DialogInitializer.() -> Unit) =
    this.context?.buildDialog(initializeAction)

fun Context.showDialog(initializeAction: DialogInitializer.() -> Unit) =
    buildDialog(initializeAction).show()

fun Fragment.showDialog(initializeAction: DialogInitializer.() -> Unit) =
    context?.showDialog(initializeAction)

private fun AlertDialog.Builder.initialize(
    initializer: DialogInitializer
): AlertDialog.Builder {
    with(initializer) {
        assertProperInitialization(this)

        messageText?.let {
            setMessage(it)
        }

        messageResourceId?.let {
            setMessage(it) // Using resources should always prevail.
        }

        titleText?.let {
            setTitle(it)
        }

        titleResourceId?.let {
            setTitle(it) // Using resources should always prevail.
        }

        setCancelable(isCancelable)

        customView?.let {
            setView(it)
        }

        positiveButtonInitializer?.let {
            initializePositiveButton(it)
        }

        negativeButtonInitializer?.let {
            initializeNegativeButton(it)
        }

        setOnDismissListener { onDismissListener() }

        setOnCancelListener { onCancelListener() }
    }

    return this
}

fun AlertDialog.Builder.initializeNegativeButton(buttonInitializer: DialogButtonInitializer) {
    val builder = this

    with(buttonInitializer) {
        text?.let {
            builder.setNegativeButton(it) { dialog, _ ->
                dialog.dismiss()
                onClickListener()
            }
        }

        textResourceId?.let {
            builder.setNegativeButton(it) { dialog, _ ->
                dialog.dismiss()
                onClickListener()
            }
        }
    }
}

private fun AlertDialog.Builder.initializePositiveButton(
    buttonInitializer: DialogButtonInitializer
) {
    val builder = this

    with(buttonInitializer) {
        text?.let {
            builder.setPositiveButton(it) { _, _ -> onClickListener() }
        }

        textResourceId?.let {
            builder.setPositiveButton(it) { _, _ -> onClickListener() }
        }
    }
}

private fun assertProperInitialization(initializer: DialogInitializer) {
    with(initializer) {
        if (messageResourceId == null && messageText == null && customView == null) {
            throw MissingMessageDuringDialogInitialization()
        }

        if (positiveButtonInitializer?.textResourceId == null && positiveButtonInitializer?.text == null && customView == null) {
            throw MissingPositiveButtonDuringDialogInitialization()
        }
    }
}

private const val MISSING_MESSAGE_ERROR: String =
    "Please ensure that your dialog initialization" +
            " is setting the dialog content using \'messageText\', \'messageResourceId\' or \'customView\'"

private const val MISSING_POSITIVE_BUTTON_INITIALIZATION_ERROR: String =
    "Please ensure that your dialog initialization" +
            " is setting the positive button text using \'messageText\' or \'messageResourceId\'" +
            "the \'positiveButton\' tag."

class MissingMessageDuringDialogInitialization : Throwable(MISSING_MESSAGE_ERROR)

class MissingPositiveButtonDuringDialogInitialization :
    Throwable(MISSING_POSITIVE_BUTTON_INITIALIZATION_ERROR)


