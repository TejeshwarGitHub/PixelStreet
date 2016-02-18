package pixelstreet.com.pixelstreet.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tejeshwar Reddy on 12-02-2016.
 */
public class TableManager {

    private static final String DB_NAME="pixelstreet";
    private static final String DB_Table="profileinfo";
    private static final int DB_VERSION=1;

    class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, TableManager.DB_NAME, null, TableManager.DB_VERSION);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
