package projects.kyle.ledcontroller.MainContent;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


import projects.kyle.ledcontroller.DataHandler.LedDataMock;
import projects.kyle.ledcontroller.Enums.DisplayModeSelection;
import projects.kyle.ledcontroller.R;


public class MainContentFragment extends Fragment {

    RecyclerView mainList;
    MainActivityListener listener;
    LedDataMock dataHandler;
    private int color;
    private DisplayModeSelection displayModeSelection;
    ArrayList<String> list;


    public MainContentFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        color = dataHandler.getData(getContext()).getValue().getColorList().get(0);
        displayModeSelection = dataHandler.getData(getContext()).getValue().getDisplayModeSelection();

        View v = inflater.inflate(R.layout.content_main_fragment, container, false);
        mainList = (RecyclerView) v.findViewById(R.id.main_recycler_view);

        mainList.setHasFixedSize(true);
        mainList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        ArrayList<MainListItem> viewList = new ArrayList<>();

        viewList.add(new MainListItem("Color", R.layout.color_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.launchColorPicker();
            }
        },
        new ViewCallback() {
            @Override
            public void callback(View view) {
               FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.color_button);
                button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
        }));

        viewList.add(new MainListItem("Mode", R.layout.spinner, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
                spinner.performClick();
            }
        }, new ViewCallback() {
            @Override
            public void callback(View view) {
                Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                list = new ArrayList<>();
                list.add("Pulse");
                list.add("Solid");
                list.add("Gradient");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(dataHandler.);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(parent.getContext(),list.get(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent){
                        Toast.makeText(parent.getContext(),"Nothing", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }));

        mainList.setAdapter(new MainActivityAdapter(viewList));

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MainActivityListener) context;
            dataHandler = ViewModelProviders.of(getActivity()).get(LedDataMock.class);
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }




}





