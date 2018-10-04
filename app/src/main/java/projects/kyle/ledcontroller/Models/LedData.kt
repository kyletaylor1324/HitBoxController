package projects.kyle.ledcontroller.Models

import projects.kyle.ledcontroller.Enums.DisplayModeSelection
import java.io.Serializable
import java.util.ArrayList


data class LedData(var colorList: ArrayList<Int>, var timeMs: Int, var displayModeSelection: DisplayModeSelection) : Serializable {

}