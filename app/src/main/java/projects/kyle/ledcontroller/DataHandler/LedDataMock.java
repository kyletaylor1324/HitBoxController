package projects.kyle.ledcontroller.DataHandler;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Pair;

import java.io.File;
import java.io.IOException;


public class LedDataMock extends ViewModel {

    private MutableLiveData<LedDataHolder> ledData;



    public MutableLiveData<LedDataHolder> getData(Context context){
        if (ledData == null){
            ledData = new MutableLiveData<>();
            intialLoad(context);
        }

        return ledData;
    }

    public boolean saveColorChange(int color, Context context){
        this.getData(context).setValue(new LedDataHolder(color));
        LedDbHelper db = new LedDbHelper(context);
        return db.insertData(color);
    }


    private void intialLoad(Context context){
        LedDataHolder temp = new LedDataHolder();
        temp.color = Color.BLACK;
        ledData.setValue(temp);
        LedDbHelper database = new LedDbHelper(context);

        Pair<Boolean, Integer> dbRead = database.getColor();
        if(dbRead.first){
                ledData.setValue(new LedDataHolder(dbRead.second)); }
        }

 }
