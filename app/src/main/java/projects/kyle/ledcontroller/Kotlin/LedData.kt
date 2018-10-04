package projects.kyle.ledcontroller.Kotlin

import java.io.Serializable


data class LedData(var primaryColor: Int, var timeMs: Int, var displayModeSelection: DisplayModeSelection) : Serializable {

}