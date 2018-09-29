package projects.kyle.ledcontroller.MainContent;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

public class MainListItem {
    String label;
    int preview;
    OnClickListener listener;
    ViewCallback callback;



    public MainListItem(String label, int preview, @Nullable OnClickListener listener, @Nullable ViewCallback callback){
        this.label = label;
        this.preview = preview;
        this.listener = listener;
        if(callback == null){
            callback = new ViewCallback() {
                @Override
                public void callback(View view) {

                }
            };
        }else {
            this.callback = callback;
        }
    }
}