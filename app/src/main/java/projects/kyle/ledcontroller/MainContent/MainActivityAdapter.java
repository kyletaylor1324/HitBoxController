package projects.kyle.ledcontroller.MainContent;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import projects.kyle.ledcontroller.R;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder> {

    private ArrayList<MainListItem> viewList;

    public MainActivityAdapter(ArrayList<MainListItem> viewList){
        this.viewList = viewList;
    }



    @NonNull
    @Override
    public MainActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list_item, viewGroup, false);
        v.setOnClickListener(viewList.get(i).listener);

        MainActivityViewHolder vh = new MainActivityViewHolder(v, viewGroup);
        View prev = LayoutInflater.from(viewGroup.getContext()).inflate(viewList.get(i).preview, viewGroup, false);
        viewList.get(i).callback.callback(prev);
        vh.preview.addView(prev);

        return vh;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }
    

    @Override
    public void onBindViewHolder(@NonNull MainActivityViewHolder mainActivityViewHolder, int i) {
        mainActivityViewHolder.menuLabel.setText(viewList.get(i).label);

    }

    @Override
    public int getItemCount() {
        return viewList.size();
    }

    public static class MainActivityViewHolder extends RecyclerView.ViewHolder {

        public TextView menuLabel;
        public FrameLayout preview;
        public ViewGroup viewGroup;

        public MainActivityViewHolder(View view, ViewGroup viewGroup) { super(view);
           this.viewGroup = viewGroup;
           menuLabel = (TextView) view.findViewById(R.id.main_list_item_label);
           preview = (FrameLayout) view.findViewById(R.id.main_list_item_preview);
        }
    }
}

