package clickbaitstudio.developerstudentclub.com.in;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import de.mrapp.android.dialog.ProgressDialog;


public class SplashActivity extends Activity {

    DBHelper dbHelper;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        dbHelper = new DBHelper(this);
        Global.devicename=getDeviceName();

        Global.udid = Settings.Secure.getString(SplashActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

      /*  if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    1001 );
        }
*/
        //TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
       // if (tm != null) { Global.IMEI = tm.getDeviceId();}


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        if (internetConnectionCheck(SplashActivity.this)) {
            //   Toast.makeText(getApplicationContext(), "Internet Connection is available. Abhijeet !!!", Toast.LENGTH_LONG).show();

            try{

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        dbHelper.getData();
                        if(Global.au.Roll_No.equals("")) {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class)); }
                        else if(Global.au.Auth.equals("student")) {
                            Toast.makeText(getApplicationContext(),"Welcome"+" "+Global.au.Name, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else if(Global.au.Auth.equals("hod")) {
							Global.webviewurl="/hod/index.php?branch="+Global.au.Branch;
                            startActivity(new Intent(getApplicationContext(),WebviewActivity.class));
                        }
                        else if(Global.au.Auth.equals("all")) {
                            Global.webviewurl="/principal/index.php?who="+Global.au.Branch;
                            startActivity(new Intent(getApplicationContext(),WebviewActivity.class));
                        }
                        else if(Global.au.Auth.equals("faculty")) {
                            Global.webviewurl="/faculty/index.php?branch="+Global.au.Branch;
                            Toast.makeText(getApplicationContext(),"Welcome"+" "+Global.au.Name, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),WebviewActivity.class));
                        }
						else
                        { startActivity(new Intent(getApplicationContext(),LoginActivity.class)); }

						finish();
                    }
                }, 1000);

            }catch (Exception e) {e.printStackTrace();}


        } else

        {
            dialog();
        }


    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }


    public static boolean internetConnectionCheck(Activity CurrentActivity) {
        Boolean Connected = false;
        ConnectivityManager connectivity = (ConnectivityManager) CurrentActivity.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) for (int i = 0; i < info.length; i++)
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                  //  Log.d("My Network is: ", "Connected");
                    Connected = true;
                } else {}
        } else {
           // Log.d("My Network is: ", "Not Connected");

            Toast.makeText(CurrentActivity.getApplicationContext(),
                    "Please Check Your internet connection",
                    Toast.LENGTH_LONG).show();
            Connected = false;

        }
        return Connected;
    }


    public void dialog()
    {
        ProgressDialog.Builder dialogBuilder = new ProgressDialog.Builder(this);
        dialogBuilder.setTitle("No Internet Connection!!");
        dialogBuilder.setMessage("Please Check Ur Internet Connection");
        // dialogBuilder.setPositiveButton(android.R.string.ok, null);

        dialogBuilder .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        } );

        dialogBuilder.setProgressBarPosition(ProgressDialog.ProgressBarPosition.LEFT);
        ProgressDialog dialog = dialogBuilder.create();
        dialog.show();

    }

}
