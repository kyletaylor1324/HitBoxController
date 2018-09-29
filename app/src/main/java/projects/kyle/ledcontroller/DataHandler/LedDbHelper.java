package projects.kyle.ledcontroller.DataHandler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Pair;

public class LedDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_COLOR + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LEDControllerData.db";

    public LedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertData(int color){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_COLOR, color);
        long result;

        if(getColor().first){
            result = db.update(FeedReaderContract.FeedEntry.TABLE_NAME,
                    contentValues, null, null);
        }else {
             result =
                    db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,
                            null,
                            contentValues);
        }

        return result != -1;

    }

    public Pair<Boolean, Integer> getColor(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor color =
                db.rawQuery("select " + FeedReaderContract.FeedEntry.COLUMN_NAME_COLOR
                        + " from " + FeedReaderContract.FeedEntry.TABLE_NAME, null);
        if(color.getCount() > 0){
            color.moveToLast();
            return new Pair<>(true, color.getInt(0));
        }
        return new Pair<>(false, -1);
    }
}

 final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "ledData_table";
        public static final String COLUMN_NAME_COLOR= "color";
    }
}
