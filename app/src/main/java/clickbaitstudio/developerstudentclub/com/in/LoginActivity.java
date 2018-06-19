package clickbaitstudio.developerstudentclub.com.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private Button btnSubmitlogin;
    private String usernamee ="", passwordd ="";
    public String Roll_No="",Name="", Auth="",Year="",Branch="",Section=""; //which comes from server
    private ProgressDialog pDialog;
    private JSONObject json;
    private int success=4;

    private HTTPURLConnection service;

    //Initialize webservice URL
    private String path = Global.ROOT_URL+"login.php";

    DBHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username= (EditText) findViewById(R.id.username);
        username.setText("15241A0401");
        password= (EditText) findViewById(R.id.password);
        btnSubmitlogin= (Button) findViewById(R.id.btnSubmitlogin);
        //signup= (TextView) findViewById(R.id.signup);

        //Initialize DBHelper class
        dbHelper = new DBHelper(this);

        //Initialize HTTPURLConnection class object
        service=new HTTPURLConnection();



        btnSubmitlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().equals("") && !username.getText().toString().equals("")) {
                    usernamee = username.getText().toString();
                    passwordd = password.getText().toString();
                    //Call WebService
                    new LoginActivity.PostDataTOServer().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter all fields", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private class PostDataTOServer extends AsyncTask<Void, Void, Void> {

        String response = "";
        //Create hashmap Object to send parameters to web service
        HashMap<String, String> postDataParams;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            postDataParams=new HashMap<String, String>();
            postDataParams.put("usernamee", usernamee);
            postDataParams.put("passwordd", passwordd);
            postDataParams.put("udid", Global.udid);
            postDataParams.put("IMEI", Global.IMEI);
            postDataParams.put("phone", Global.phone);
            postDataParams.put("devicename", Global.devicename);



            //Call ServerData() method to call webservice and store result in response
            response= service.ServerData(path,postDataParams);

            try {
                json = new JSONObject(response);
                //Get Values from JSONobject
                // System.out.println("success=" + json.get("success"));
                success = json.getInt("success");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();
            //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

            if(success==0) {

                clear();
            }

            if(success==100) {

               // Toast.makeText(getApplicationContext(),"Welcome"+" "+ Name, Toast.LENGTH_LONG).show();
            }
            if(success==111) {

                try {
                    json = new JSONObject(response);

                    Roll_No=json.getString("Roll_No");
                    Auth = json.getString("Auth");
                    Name=json.getString("Name");
                    Year=json.getString("Year");
                    Branch=json.getString("Branch");
                    Section=json.getString("Section");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 User uobj = new User();

                 uobj.Roll_No = Roll_No;
                 uobj.Auth=Auth;
                 uobj.Name=Name;
                 uobj.Year=Year;
                 uobj.Branch=Branch;
                 uobj.Section=Section;

                 dbHelper.insertUser(uobj);
                 Global.au =  uobj;
                 //dbHelper.getData();//imp anukunta // chala

                if(Auth.equals("student")){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Toast.makeText(getApplicationContext(),"Welcome"+" "+ Name, Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(Auth.equals("hod")){
                    Global.webviewurl="hod/index.php?branch="+Branch;
                    startActivity(new Intent(getApplicationContext(),WebviewActivity.class));
                    Toast.makeText(getApplicationContext(),"Welcome"+" "+ Name, Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(Auth.equals("all")){
                    Global.webviewurl="principal/index.php?who="+Branch;
                    startActivity(new Intent(getApplicationContext(),WebviewActivity.class));
                    Toast.makeText(getApplicationContext(),"Welcome"+" "+ Name, Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(Auth.equals("faculty")){
                    Global.webviewurl="faculty/index.php?branch="+Branch;
                    startActivity(new Intent(getApplicationContext(),WebviewActivity.class));
                    Toast.makeText(getApplicationContext(),"Welcome"+" "+ Name, Toast.LENGTH_LONG).show();
                    finish();
                }

            }



        }
    }

    public void clear() {
        Toast.makeText(getApplicationContext(), "Invalid Credentials..!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void signup(View v) {
          /* Intent intent = new Intent(this, SignupActivity.class);
           startActivity(intent); */
    }


}
