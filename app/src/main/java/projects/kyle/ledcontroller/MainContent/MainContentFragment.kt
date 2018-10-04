package projects.kyle.ledcontroller.MainContent


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import java.util.ArrayList

import projects.kyle.ledcontroller.Kotlin.DisplayModeSelection
import projects.kyle.ledcontroller.Kotlin.LedDataViewModel
import projects.kyle.ledcontroller.R


class MainContentFragment : Fragment() {

    internal lateinit var mainList: RecyclerView
    internal lateinit var listener: MainActivityListener
    internal lateinit var dataHandler: LedDataViewModel
    private var color: Int = 0
    private val timeMs = 500
    private var displayModeSelection: DisplayModeSelection? = null
    internal lateinit var selectionList: Array<DisplayModeSelection>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)


        color = dataHandler.getData(context!!).value!!.primaryColor
        displayModeSelection = dataHandler.getData(context!!).value!!.displayModeSelection

        val v = inflater.inflate(R.layout.content_main_fragment, container, false)
        mainList = v.findViewById(R.id.main_recycler_view) as RecyclerView

        mainList.setHasFixedSize(true)
        mainList.setLayoutManager(LinearLayoutManager(container!!.context))

        val viewList = ArrayList<MainListItem>()

        viewList.add(MainListItem("Color", R.layout.color_button, View.OnClickListener { listener.launchColorPicker() },
                 object : ViewCallback { override fun callback(view : View) {
                     val button = view.findViewById<View>(R.id.color_button) as FloatingActionButton
                     button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY)
                 }}))

        viewList.add(MainListItem("Mode", R.layout.spinner, View.OnClickListener { v ->
            val spinner = v.findViewById<View>(R.id.spinner) as Spinner
            spinner.performClick()
        },  object : ViewCallback { override fun callback(view : View) {
            val spinner = view.findViewById<View>(R.id.spinner) as Spinner
            selectionList = DisplayModeSelection.values()
            val adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, selectionList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setSelection(displayModeSelection!!.ordinal)

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    displayModeSelection = selectionList[position]
                    dataHandler.setValues(null, null, displayModeSelection)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // do nothing
                }
            }
        }}))

        mainList.setAdapter(MainActivityAdapter(viewList))

        return v
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as MainActivityListener
            dataHandler = ViewModelProviders.of(activity!!).get(LedDataViewModel::class.java)
        } catch (castException: ClassCastException) {
            /** The activity does not implement the listener.  */
        }

    }


}





