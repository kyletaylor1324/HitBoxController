package projects.kyle.ledcontroller.ColorPicker;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import projects.kyle.ledcontroller.DataHandler.LedDataHolder;
import projects.kyle.ledcontroller.DataHandler.LedDataMock;
import projects.kyle.ledcontroller.R;


public class ColorPickerFragment extends Fragment {

    private LedDataMock dataHandler;

    public ColorPickerFragment() {
        // Required empty public constructor
    }

    private int color;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        this.color = dataHandler.getData(getContext()).getValue().color;


        View v = inflater.inflate(R.layout.fragment_color_picker, container, false);
        final FloatingActionButton colorButton = (FloatingActionButton) v.findViewById(R.id.color_picker_button);
        colorButton.getBackground().setColorFilter(this.color,PorterDuff.Mode.MULTIPLY);

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dataHandler.saveColorChange(ColorPickerFragment.this.color,
                        ColorPickerFragment.this.getActivity())) {
                    Toast.makeText(getContext(), "Color Saved", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }else{
                    Toast.makeText(getContext(), "Error saving color change.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final HSLColorPicker colorPicker = (HSLColorPicker) v.findViewById(R.id.color_picker);
        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                colorButton.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                ColorPickerFragment.this.color = color;
            }
        });
        colorPicker.setColor(this.color);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataHandler = ViewModelProviders.of(getActivity()).get(LedDataMock.class);
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }








}