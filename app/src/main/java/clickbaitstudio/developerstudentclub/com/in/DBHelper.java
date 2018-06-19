package clickbaitstudio.developerstudentclub.com.in;

/**
 *
 * By Developer Student Club
 *
 * K Varshit Ratna (lead)
 * Manikanth P (core team member)
 * Deveraj (core team member)
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 *
 * By Developer Student Club
 *
 * K Varshit Ratna (lead)
 * Manikanth P (core team member)
 * Deveraj (core team member)
 */

public class DBHelper extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gcap.db";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    /*public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);

    }*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        //  db.execSQL("CREATE TABLE IF NOT EXISTS HRSREG(api_key text,company text,logo text,lickey text,mobileno text,deviceid text)");

        db.execSQL("CREATE TABLE IF NOT EXISTS users (Roll_No text,Name text, Auth text,Year text,Branch text,Section text)");

        //Log.d("Databases created","");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertUser(User obj) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Roll_No", obj.Roll_No);
        contentValues.put("Name", obj.Name);
        contentValues.put("Auth", obj.Auth);
        contentValues.put("Year", obj.Year);
        contentValues.put("Branch", obj.Branch);
        contentValues.put("Section", obj.Section);



        db.delete("users",null,null);
        db.insert("users", null, contentValues);
        db.close();
        return true;
    }




  /*
    public void getSettings() {
        SQLiteDatabase dbse = this.getReadableDatabase();
        String q="select * from settings where hrsuser like '%"+Global.sUSERNAME+"%'";
        Cursor res =  dbse.rawQuery( q, null );
        if(res!=null)
        {
            if(res.moveToFirst())
            {
                Global.sUPLOADTOSERVER = res.getString(res.getColumnIndex("up_server"));
            }dbse.close();
        }

    }
*/

    public void getData() {
        SQLiteDatabase dbd = this.getReadableDatabase();
        Cursor res =  dbd.rawQuery( "select * from users", null );
        if(res!=null)
        {
            if(res.moveToFirst())
            {


                Global.au.Roll_No = res.getString(res.getColumnIndex("Roll_No"));
                Global.au.Name = res.getString(res.getColumnIndex("Name"));
                Global.au.Auth = res.getString(res.getColumnIndex("Auth"));
                Global.au.Year = res.getString(res.getColumnIndex("Year"));
                Global.au.Branch = res.getString(res.getColumnIndex("Branch"));
                Global.au.Section = res.getString(res.getColumnIndex("Section"));



            }dbd.close();
        }
    }//getdata


    public void  logout()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Global.clearData();
        db.delete("users", null, null);
        db.close();

    }


}
