package org.setu.rugbyscorebuddy.helpers
/*
I researched Number Pickers and found this URL:
    https://stackoverflow.com/questions/6796243/is-it-possible-to-make-a-horizontal-numberpicker

I used the code with the help of CHATGPT to convert the JAVA code into KOTLIN
*/

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.Nullable
import org.setu.rugbyscorebuddy.R

class HorizontalNumberPicker @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var etNumber: EditText
    private var min: Int = 0
    private var max: Int = 100
    private var onValueChangedListener: ((Int) -> Unit)? = null // Callback for value changes

    init {
        LayoutInflater.from(context).inflate(R.layout.numberpicker_horizontal, this, true)
        etNumber = findViewById(R.id.et_number)

        val btnLess: Button = findViewById(R.id.btn_less)
        val btnMore: Button = findViewById(R.id.btn_more)

        btnLess.setOnClickListener(AddHandler(-1))
        btnMore.setOnClickListener(AddHandler(1))

        // Add TextWatcher to EditText
        etNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val value = getValue()
                // Notify the listener when the value changes
                onValueChangedListener?.invoke(value)
            }
        })
    }

    /***
     * HANDLERS
     */
    private inner class AddHandler(private val diff: Int) : OnClickListener {
        override fun onClick(v: View?) {
            var newValue = getValue() + diff
            if (newValue < min) {
                newValue = min
            } else if (newValue > max) {
                newValue = max
            }
            etNumber.setText(newValue.toString())
        }
    }

    /***
     * GETTERS & SETTERS
     */

    fun getValue(): Int {
        return try {
            etNumber.text.toString().toInt()
        } catch (ex: NumberFormatException) {
            Log.e("HorizontalNumberPicker", ex.toString())
            0
        }
    }

    fun setValue(value: Int) {
        etNumber.setText(value.toString())
    }

    fun getMin(): Int = min

    fun setMin(min: Int) {
        this.min = min
    }

    fun getMax(): Int = max

    fun setMax(max: Int) {
        this.max = max
    }

    // Set the listener for value changes
    fun setOnValueChangedListener(listener: (Int) -> Unit) {
        this.onValueChangedListener = listener
    }
}
