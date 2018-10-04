package projects.kyle.ledcontroller.Kotlin


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Color




class LedDataViewModel : ViewModel() {

    private var ledData: MutableLiveData<LedData>? = null


    fun getData(context: Context): MutableLiveData<LedData> {
        if (ledData == null) {
            ledData = MutableLiveData()
            intialLoad(context)
        }

        return ledData as MutableLiveData<LedData>
    }

    fun setValues(primaryColor : Int?, timeMs : Int?, displayModeSelection: DisplayModeSelection?){
        val temp = ledData?.value
        if(primaryColor!=null){
            temp?.primaryColor = primaryColor
        }
        if(timeMs!=null){
            temp?.timeMs = timeMs
        }
        if(displayModeSelection!=null){
            temp?.displayModeSelection = displayModeSelection
        }
        ledData?.value = temp
    }


    private fun intialLoad(context: Context) {
        val settings = context.getSharedPreferences("hitboxPreferences", Context.MODE_PRIVATE)
        val data : LedData =
                LedData(settings.getInt("primaryColor", Color.BLACK),
                        settings.getInt("timeMs", 500),
                        DisplayModeSelection.valueOf(settings.getString("displayModeSelection", DisplayModeSelection.Solid.name)))
        ledData?.value = data
    }

    fun savePreferences(context: Context) {
        val data = getData(context)
        val settings = context.getSharedPreferences("hitboxPreferences", Context.MODE_PRIVATE)
        val editor = settings.edit()

        if(data.value != null) {
            editor.putInt("primaryColor", data.value!!.primaryColor)
            editor.putInt("timeMs", data.value!!.timeMs)
            editor.putString("displayModeSelection", data.value!!.displayModeSelection.name)
            editor.commit()
        }

    }

}
