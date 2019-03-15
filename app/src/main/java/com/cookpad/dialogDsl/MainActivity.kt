package com.cookpad.dialogDsl

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MESSAGE_CLICKED_ON_POSITIVE_BUTTON = "The user clicked on the positive button!"
        private const val MESSAGE_CLICKED_ON_NEGATIVE_BUTTON = "The user clicked on the negative button!"
        private const val MESSAGE_DIALOG_DISMISSED = "The dialog was dismissed!"
        private const val MESSAGE_DIALOG_CANCELED = "The dialog was canceled!"
        private const val MESSAGE_CUSTOM_VIEW_INTERACTION = "The user interacted with custom view!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setContentView(R.layout.activity_main)

        buttonAlertDialog.setOnClickListener {
            displayAlertDialog()
        }

        buttonAlertDialogWithTitle.setOnClickListener {
            displayAlertDialogWithTitle()
        }

        buttonAlertDialogWithPositiveButtonCallback.setOnClickListener {
            displayAlertDialogWithPositiveButtonCallback()
        }

        buttonAlertDialogWithPositiveAndNegativeButtonsCallbacks.setOnClickListener {
            displayAlertDialogWithAlertDialogWithPositiveAndNegativeButtonsCallbacks()
        }

        buttonAlertDialogWithDismissCallback.setOnClickListener {
            displayAlertDialogWithDismissCallback()
        }

        buttonAlertDialogWithCancelCallback.setOnClickListener {
            displayAlertDialogWithCancelCallback()
        }

        buttonAlertDialogWithCustomView.setOnClickListener {
            displayAlertDialogWithCustomView()
        }

        buttonAlertDialogWithoutStringResources.setOnClickListener {
            displayAlertDialogWithoutStringResources()
        }

        buttonNonCancelableAlertDialog.setOnClickListener {
            displayNonCancelableDialog()
        }
    }

    private fun displayAlertDialog() {
        resetDialogResult()

        showDialog {
            messageResourceId = R.string.alert_dialog_message

            positiveButton {
                textResourceId = android.R.string.ok
            }
        }
    }

    private fun displayAlertDialogWithTitle() {
        resetDialogResult()

        showDialog {
            messageResourceId = R.string.alert_dialog_message

            titleResourceId = R.string.alert_dialog_title

            positiveButton {
                textResourceId = android.R.string.ok
            }
        }
    }

    private fun displayAlertDialogWithPositiveButtonCallback() {
        resetDialogResult()

        showDialog {
            messageResourceId = R.string.alert_dialog_message

            titleResourceId = R.string.alert_dialog_title

            positiveButton {
                textResourceId = android.R.string.ok
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_POSITIVE_BUTTON)
                }
            }
        }
    }

    private fun displayAlertDialogWithAlertDialogWithPositiveAndNegativeButtonsCallbacks() {
        resetDialogResult()

        showDialog {
            messageResourceId = R.string.alert_dialog_message

            titleResourceId = R.string.alert_dialog_title

            positiveButton {
                textResourceId = android.R.string.ok
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_POSITIVE_BUTTON)
                }
            }

            negativeButton {
                textResourceId = android.R.string.cancel
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_NEGATIVE_BUTTON)
                }
            }
        }
    }


    private fun displayAlertDialogWithDismissCallback() {
        resetDialogResult()

        showDialog {
            messageResourceId = R.string.alert_dialog_message

            titleResourceId = R.string.alert_dialog_title

            positiveButton {
                textResourceId = android.R.string.ok
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_POSITIVE_BUTTON)
                }
            }

            negativeButton {
                textResourceId = android.R.string.cancel
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_NEGATIVE_BUTTON)
                }
            }

            onDismissListener = {
                updateResultDialog(MESSAGE_DIALOG_DISMISSED)
            }
        }
    }

    private fun displayAlertDialogWithCancelCallback() {
        resetDialogResult()

        showDialog {
            messageResourceId = R.string.alert_dialog_message

            titleResourceId = R.string.alert_dialog_title

            positiveButton {
                textResourceId = android.R.string.ok
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_POSITIVE_BUTTON)
                }
            }

            negativeButton {
                textResourceId = android.R.string.cancel
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_NEGATIVE_BUTTON)
                }
            }

            onCancelListener = {
                updateResultDialog(MESSAGE_DIALOG_CANCELED)
            }
        }
    }

    private fun displayAlertDialogWithCustomView() {
        resetDialogResult()

        val layoutInflater = LayoutInflater.from(this)

        val view = layoutInflater.inflate(R.layout.dialog_custom_view, null)

        val dialog = buildDialog {
            customView = view
        }

        val customViewButton: Button = view.findViewById(R.id.buttonCustomView)

        customViewButton.setOnClickListener {
            updateResultDialog(MESSAGE_CUSTOM_VIEW_INTERACTION)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun displayAlertDialogWithoutStringResources() {
        val title = getString(R.string.alert_dialog_title)

        val message = getString(R.string.alert_dialog_message)

        val positiveButtonText = getText(android.R.string.ok)

        val negativeButtonText = getText(android.R.string.cancel)

        showDialog {
            titleText = title

            messageText = message

            positiveButton {
                text = positiveButtonText
            }

            negativeButton {
                text = negativeButtonText
            }
        }
    }

    private fun displayNonCancelableDialog() {
        showDialog {
            messageResourceId = R.string.alert_dialog_message

            titleResourceId = R.string.alert_dialog_title

            isCancelable = false

            positiveButton {
                textResourceId = android.R.string.ok
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_POSITIVE_BUTTON)
                }
            }

            negativeButton {
                textResourceId = android.R.string.cancel
                onClickListener = {
                    updateResultDialog(MESSAGE_CLICKED_ON_NEGATIVE_BUTTON)
                }
            }

            onCancelListener = {
                updateResultDialog(MESSAGE_DIALOG_CANCELED)
            }
        }
    }


    private fun updateResultDialog(resultText: String) {
        textViewDialogResult.text = resultText
    }

    private fun resetDialogResult() {
        updateResultDialog(
            getString(R.string.empty)
        )
    }
}
