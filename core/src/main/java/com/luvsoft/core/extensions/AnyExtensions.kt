package com.example.core.extensions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.util.DisplayMetrics
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.HtmlCompat
import java.io.Serializable
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.truncate

inline fun <T> tryOrDefault(defaultValue: T, blockToTry: () -> T): T = try {
    blockToTry()
} catch (t: Throwable) {
    defaultValue
}

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

inline fun <T, U, R> Pair<T?, U?>.biLet(body: (T, U) -> R): R? {
    val first = first
    val second = second
    if (first != null && second != null) {
        return body(first, second)
    }
    return null
}

inline fun <T, U, E, R> Triple<T?, U?, E?>.triLet(body: (T, U, E) -> R): R? {
    val first = first
    val second = second
    val third = third
    if (first != null && second != null && third != null) {
        return body(first, second, third)
    }
    return null
}

fun Double.withTwoDecimals(): Double {
    return truncate(this * 100.0) / 100.0
}

fun Int.getHexStringColor() =
    "#${Integer.toHexString(this@getHexStringColor)}"

fun <T> concatenate(vararg lists: List<T>): List<T> {
    val result: MutableList<T> = ArrayList()
    lists.forEach { list: List<T> -> result.addAll(list) }
    return result
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

inline fun <reified T : java.io.Serializable> Intent.serializable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

fun Boolean?.orTrue() = this ?: true

fun Boolean?.orFalse() = this ?: false

fun Int?.orZero() = this ?: 0

fun BigInteger?.orZero() = this ?: 0

fun Float?.orZero() = this ?: 0.0

fun Double?.orZero() = this ?: 0

fun Double?.orZeroDouble() = this ?: 0.0

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Double.toMexican(): String {
    val formatD= NumberFormat.getCurrencyInstance(Locale("es", "MX"))
    return formatD.format(this)
}


/**
 * Extensions to clean Zeros at the beginning of the barcodes
 */

fun String.setPosition(): Boolean {
    var visible = false
    val positionSplit = this.split("-")
    positionSplit.forEachIndexed { index, _ ->
        if (positionSplit[index] != "0") {
            visible = true
        }
    }
    return visible
}

fun List<String>.toArrayStringList(): ArrayList<String> {
    val helperList = arrayListOf<String>()
    for (s in this) {
        helperList.add(s)
    }
    return helperList
}


fun EditText.setEditableText(text: String) {
    this.text = Editable.Factory.getInstance().newEditable(text)
}

fun String.getStringSize(): Int = this.toCharArray().size

fun String?.convertToInt(): Int = if (this.isNullOrEmpty()) 0 else this.toInt()

fun String?.convertToDouble(): Double = if (this.isNullOrEmpty()) 0.0 else this.toDouble()

fun String?.convertToLong(): Long = if (this.isNullOrEmpty()) 0 else this.toLong()

fun Double.getDecimalFormat(): String = DecimalFormat("#.###").format(this)

fun <T> ArrayList<T>?.orEmptyArray(): ArrayList<T> = this ?: ArrayList()

fun getSpannedFromString(text: String) =
    HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)

fun ArrayList<Pair<String, String>>.getJumperCountSum(): Int {
    var helper = 0
    this.forEach {
        helper += it.first.toInt()
    }
    return helper
}

fun Double?.orDoubleZero() = this ?: 0.0

fun Int.isNotZero() = this > 0
