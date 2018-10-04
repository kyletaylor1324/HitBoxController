package projects.kyle.ledcontroller.MainContent

import android.view.View
import android.view.View.OnClickListener

class MainListItem(internal var label: String, internal var preview: Int, internal var listener: OnClickListener?, var callback: ViewCallback?) {

}