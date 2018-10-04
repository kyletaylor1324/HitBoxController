package projects.kyle.ledcontroller.ColorPicker


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.madrapps.pikolo.HSLColorPicker
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener


import projects.kyle.ledcontroller.Kotlin.LedDataViewModel
import projects.kyle.ledcontroller.R


class ColorPickerFragment : Fragment() {

    private var dataHandler: LedDataViewModel? = null

    private var color: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        this.color = dataHandler!!.getData(context!!).value!!.primaryColor


        val v = inflater.inflate(R.layout.fragment_color_picker, container, false)
        val colorButton = v.findViewById(R.id.color_picker_button) as FloatingActionButton
        colorButton.getBackground().setColorFilter(this.color, PorterDuff.Mode.MULTIPLY)

        colorButton.setOnClickListener(View.OnClickListener {
            dataHandler!!.setValues(color, null, null)
            Toast.makeText(context, "Color Saved", Toast.LENGTH_SHORT).show()
            activity!!.onBackPressed()
        })

        val colorPicker = v.findViewById(R.id.color_picker) as HSLColorPicker
        colorPicker.setColorSelectionListener(object : SimpleColorSelectionListener() {
            override fun onColorSelected(color: Int) {
                colorButton.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY)
                this@ColorPickerFragment.color = color
            }
        })
        colorPicker.setColor(this.color)
        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            dataHandler = ViewModelProviders.of(activity!!).get(LedDataViewModel::class.java)
        } catch (castException: ClassCastException) {
            /** The activity does not implement the listener.  */
        }

    }


}// Required empty public constructor