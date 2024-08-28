package com.android.template.others

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class NameTextWatcher(private val editText: EditText) : TextWatcher {
    override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
        // Not needed, but you can implement if necessary.
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
        // Not needed, but you can implement if necessary.
    }

    override fun afterTextChanged(editable: Editable?) {
        val text = editable.toString()
        val filteredText = text.replace(Regex("[^a-zA-Z\\s]"), "") // Remove special characters and numbers
        if (text != filteredText) {
            editText.setText(filteredText)
            editText.setSelection(filteredText.length)
        }
    }
}
