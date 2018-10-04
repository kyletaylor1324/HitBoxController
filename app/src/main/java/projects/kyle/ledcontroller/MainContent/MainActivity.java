package projects.kyle.ledcontroller.MainContent;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import projects.kyle.ledcontroller.ColorPicker.ColorPickerFragment;
import projects.kyle.ledcontroller.DataHandler.LedDataMock;
import projects.kyle.ledcontroller.R;


public class MainActivity extends AppCompatActivity implements MainActivityListener {



    FragmentManager fragmentManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        final LedDataMock dataHolder = ViewModelProviders.of(this).get(LedDataMock.class);



        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            MainContentFragment mainContentFragment = new MainContentFragment();
            ft.add(R.id.main_fragment_container, mainContentFragment);
            ft.commit();
        }
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchColorPicker(){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
        ft.replace(R.id.main_fragment_container,colorPickerFragment , "COLOR_PICKER");
        ft.addToBackStack("COLOR_PICKER");
        ft.commit();
    }

    public void launchModePicker() {

    }

    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            super.onBackPressed();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure you wish to exit?")
                    .setIcon(android.R.drawable.ic_lock_power_off)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                        }
                    }).setNegativeButton(android.R.string.no, null).show();
        }

    }

}
