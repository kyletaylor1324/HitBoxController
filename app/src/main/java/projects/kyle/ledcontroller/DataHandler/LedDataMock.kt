package projects.kyle.ledcontroller.DataHandler


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Color
import android.util.Pair
import projects.kyle.ledcontroller.Enums.DisplayModeSelection



import projects.kyle.ledcontroller.Models.LedData



class LedDataMock : ViewModel() {

    private var ledData: MutableLiveData<LedData>? = null


    fun getData(context: Context): MutableLiveData<LedData> {
        if (ledData == null) {
            ledData = MutableLiveData()
            intialLoad(context)
        }

        return ledData as MutableLiveData<LedData>
    }


    private fun intialLoad(context: Context) {
        val colorList = arrayListOf<Int>()
        colorList.add(Color.BLACK)

        var temp = LedData(
                colorList = colorList,
                timeMs = 0,
                displayModeSelection = DisplayModeSelection.SOLID)

        ledData?.value = temp

        val database = LedDbHelper(context)

        val dbRead = database.data as Pair<Boolean, LedData>
        if (dbRead.first) {
            ledData?.value = dbRead.second
        }
    }

}
