package projects.kyle.ledcontroller.MainContent

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.view.Menu
import android.view.MenuItem
import android.view.View

import projects.kyle.ledcontroller.ColorPicker.ColorPickerFragment
import projects.kyle.ledcontroller.Kotlin.LedDataViewModel
import projects.kyle.ledcontroller.R


class MainActivity : AppCompatActivity(), MainActivityListener {


    private lateinit var fragmentManager: FragmentManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        fragmentManager = supportFragmentManager
        val dataHolder = ViewModelProviders.of(this).get(LedDataViewModel::class.java)

        if (savedInstanceState == null) {
            val ft = fragmentManager.beginTransaction()
            val mainContentFragment = MainContentFragment()
            ft.add(R.id.main_fragment_container, mainContentFragment)
            ft.commit()
        }
        setSupportActionBar(toolbar)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun launchColorPicker() {
        val ft = fragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        val colorPickerFragment = ColorPickerFragment()
        ft.replace(R.id.main_fragment_container, colorPickerFragment, "COLOR_PICKER")
        ft.addToBackStack("COLOR_PICKER")
        ft.commit()
    }

    override fun launchModePicker() {

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure you wish to exit?")
                    .setIcon(android.R.drawable.ic_lock_power_off)
                    .setPositiveButton(android.R.string.yes) { dialog, which -> super@MainActivity.onBackPressed() }.setNegativeButton(android.R.string.no, null).show()
        }

    }

    public override fun onDestroy() {
        ViewModelProviders.of(this).get(LedDataViewModel::class.java).savePreferences(applicationContext)
        super.onDestroy()
    }

}
