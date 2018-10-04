package projects.kyle.ledcontroller.MainContent

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

import java.util.ArrayList

import projects.kyle.ledcontroller.R

class MainActivityAdapter(private val viewList: ArrayList<MainListItem>) : RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder>() {
    override fun getItemCount(): Int {
        return viewList.size
    }

    override fun  onCreateViewHolder(viewGroup: ViewGroup, i: Int): MainActivityViewHolder {

        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.main_list_item, viewGroup, false)
        v.setOnClickListener(viewList[i].listener)

        val vh = MainActivityViewHolder(v, viewGroup)
        val prev = LayoutInflater.from(viewGroup.context).inflate(viewList[i].preview, viewGroup, false)
        viewList[i].callback?.callback(prev)
        vh.preview.addView(prev)

        return vh
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onBindViewHolder(mainActivityViewHolder: MainActivityViewHolder, i: Int) {
        mainActivityViewHolder.menuLabel.text = viewList[i].label

    }

    class MainActivityViewHolder(view: View, var viewGroup: ViewGroup) : RecyclerView.ViewHolder(view) {

        var menuLabel: TextView
        var preview: FrameLayout

        init {
            menuLabel = view.findViewById<View>(R.id.main_list_item_label) as TextView
            preview = view.findViewById<View>(R.id.main_list_item_preview) as FrameLayout
        }
    }
}

