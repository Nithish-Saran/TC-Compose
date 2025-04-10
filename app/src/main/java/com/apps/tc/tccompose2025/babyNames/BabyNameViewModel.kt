package com.apps.tc.tccompose2025.babyNames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.tc.tccompose2025.App
import com.apps.tc.tccompose2025.loadJsonFromAssets
import com.apps.tc.tccompose2025.log
import com.apps.tc.tccompose2025.models.BabyNameData
import com.apps.tc.tccompose2025.objectArray
import com.apps.tc.tccompose2025.palankal.PalankalState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class BabyNameViewModel: ViewModel() {
    private val _babyNameState = MutableStateFlow<BabyNameViewState>(BabyNameViewState.Loading)
    val babyNameState: StateFlow<BabyNameViewState> = _babyNameState.asStateFlow()

    private val _boyName = MutableStateFlow<List<BabyNameData>>(emptyList())
    private val _girlName = MutableStateFlow<List<BabyNameData>>(emptyList())
    val menu = mutableListOf<Pair<String, String>>()

    private val starNamesStartingChars = arrayOf(arrayOf("சு", "சே", "சோ", "ல"),
        arrayOf("லி", "லு", "லே", "லோ"), arrayOf("அ", "இ", "உ", "எ"),
        arrayOf("ஒ", "வ", "வி", "வு"), arrayOf("வே", "வோ", "கா", "கி"),
        arrayOf("கு", "க", "ச", "ஞ"), arrayOf("கே", "கோ", "ஹ", "ஹி"),
        arrayOf("ஹு", "ஹே", "ஹோ", "ட"), arrayOf("டி", "டு", "டே", "டோ"),
        arrayOf("ம", "மி", "மு", "மெ"), arrayOf("மோ", "ட", "டி", "டு"),
        arrayOf("டே", "டோ", "ப", "பி"), arrayOf("பூ", "ஷ", "ந", "ட"),
        arrayOf("பே", "போ", "ர", "ரி"), arrayOf("ரு", "ரே", "ரோ", "த"),
        arrayOf("தி", "து", "தே", "தோ"), arrayOf("ந", "நி", "நு", "நே"),
        arrayOf("நோ", "ய", "இ", "பூ"), arrayOf("யே", "யோ", "ப", "பி"),
        arrayOf("பூ", "த", "ப", "டா"), arrayOf("பே", "போ", "ஜ ", "ஜி"),
        arrayOf("ஜூ", "ஜே", "ஜோ", "கொ"), arrayOf("க", "கீ", "கு", "கூ"),
        arrayOf("கோ", "ஸ", "ஸீ", "ஸூ"), arrayOf("ஸே", "ஸோ", "தா", "தீ"),
        arrayOf("து", "ஞ", "ச", "த"), arrayOf("தே", "தோ", "ச", "சி"))

    fun getBabyNamesData(context: App) {
        _babyNameState.value = BabyNameViewState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val babyBoyData = loadJsonFromAssets(context, "babyName/boys-names.json")
            val babyGirlData = loadJsonFromAssets(context, "babyName/girls-names.json")

            _boyName.value = JSONArray(babyBoyData).objectArray().map {
                BabyNameData.Companion.serialize(it) as BabyNameData
            }

            _girlName.value = JSONArray(babyGirlData).objectArray().map {
                BabyNameData.Companion.serialize(it) as BabyNameData
            }

            menu.clear()
            menu.add(Pair("file:///android_asset/babyName/ic_0.png", "நட்சத்திர பெயர்கள்"))
            _boyName.value.map { data ->
                menu.add(Pair(data.imgUrl, data.title))
            }

            withContext(Dispatchers.Main) {
                if (menu.isNotEmpty()) {
                    _babyNameState.value = BabyNameViewState.Menu(menu.toTypedArray())
                }
                else {
                    _babyNameState.value = BabyNameViewState.Error("No Data")
                }
            }
        }
    }

    fun loadBabyNames(
        isGirl: Boolean,
        menuId: Int,
        starIndex: Int,
    ) {
        _babyNameState.value = BabyNameViewState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val names = mutableListOf<String>()
            val data = if (isGirl) _girlName.value else _boyName.value

            if (starIndex == -1) {
                names.clear()
                data.forEach {
                    names.addAll(it.names)
                }
                log(names.toString())
            }
            else {
                names.clear()
                val startingLetters = starNamesStartingChars[starIndex]
                startingLetters.forEach { letter ->
                    data.forEach { babyData ->
                        babyData.names.filter { name ->
                            name.startsWith(letter)
                                    && !names.contains(name)
                        }.forEach { names.add(it) }
                    }
                }
            }

            withContext(Dispatchers.Main) {
                if (names.isNotEmpty()) {
                    _babyNameState.value = BabyNameViewState.BabyNames(
                        names.distinct().sorted()
                    )
                }
                else {
                    _babyNameState.value = BabyNameViewState.Error("No Data")
                }
            }
        }
    }

    fun backReq(onBackReq: () -> Unit) {
        if(_babyNameState.value is BabyNameViewState.BabyNames) {
            _babyNameState.value = BabyNameViewState.Menu(menu.toTypedArray())
        }
        else {
            onBackReq()
        }
    }
}