package com.apps.tc.tccompose2025.numerology

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NumerologyViewModel: ViewModel() {
    private val _numerologyViewState = MutableStateFlow<NumerologyViewState>(NumerologyViewState.Loading)
    val numerologyViewState: StateFlow<NumerologyViewState> = _numerologyViewState.asStateFlow()

    fun getDobMatch(name: String, dob: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _numerologyViewState.value = NumerologyViewState.Loading
            val nameValue = verifyName(name)
            val dobValue = if(dob.isNotEmpty()) verifyDOB(dob) else 0
            val areFriendly = areFriendlyNumbers(nameValue, dobValue)
            withContext(Dispatchers.Main) {
                _numerologyViewState.value = NumerologyViewState.DobMatch(nameValue, dobValue, areFriendly)
            }
        }
    }

    fun getNameMatch(firstName: String, secondName: String) {

    }

    private fun verifyName(name: String): Int {
        if (name.isNotBlank() && name.length >= 2) {
            val value = getWordNumber(name)
            if (value <= 0) {
                _numerologyViewState.value = NumerologyViewState.Error
            }
            else {
                return value
            }
        }
        else {
            _numerologyViewState.value = NumerologyViewState.Error
        }
        return -1
    }
    private fun verifyDOB(dob: String): Int {
        if (dob.isNotBlank() && dob.length == 8) {
            val dobValue = dob.toIntOrNull()
            if (dobValue != null) {
                return countNumber(dobValue)
            }
            else {
                _numerologyViewState.value = NumerologyViewState.Error
            }
        }
        else {
            _numerologyViewState.value = NumerologyViewState.Error
        }
        return -1
    }

    private fun areFriendlyNumbers(one: Int, two: Int): Boolean = when (one) {
        1 -> arrayOf(1, 2, 3, 5, 9).contains(two)
        2 -> arrayOf(1, 2, 3, 9).contains(two)
        3 -> arrayOf(1, 2, 3, 4, 7, 8, 9).contains(two)
        4 -> arrayOf(3, 4, 5, 6, 7, 8).contains(two)
        5 -> arrayOf(1, 4, 5, 6, 7, 8).contains(two)
        6 -> arrayOf(4, 5, 6, 7, 8, 9).contains(two)
        7 -> arrayOf(3, 4, 5, 6, 7, 8).contains(two)
        8 -> arrayOf(3, 4, 5, 6, 7, 8).contains(two)
        9 -> arrayOf(1, 2, 3, 6, 9).contains(two)
        else -> false
    }

    private fun countNumber(value: Int): Int {
        val result = value.toString().toCharArray().map { char -> char.toString() }.map {
                string -> string.toInt() }.reduce { acc, i -> acc + i }
        return if (result < 10) result else countNumber(result)
    }

    private fun getWordNumber(word: String): Int {
        val value = word.uppercase().toCharArray().map { getLetterNumber(it) }.reduce { acc, i -> acc + i }
        return if (value > 10) countNumber(value) else value
    }

    // function to get letter number for each letter in english alphabets
    private fun getLetterNumber(char: Char): Int = when (char) {
        'A' -> 1
        'B' -> 2
        'C' -> 3
        'D' -> 4
        'E' -> 5
        'F' -> 8
        'G' -> 3
        'H' -> 5
        'I' -> 1
        'J' -> 1
        'K' -> 2
        'L' -> 3
        'M' -> 4
        'N' -> 5
        'O' -> 7
        'P' -> 8
        'Q' -> 1
        'R' -> 2
        'S' -> 3
        'T' -> 4
        'U' -> 6
        'V' -> 6
        'W' -> 6
        'X' -> 5
        'Y' -> 1
        'Z' -> 7
        ' ', '.', ',', '-', '_' -> 0
        else -> -1
    }
}