package clickbaitstudio.developerstudentclub.com.in;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import de.mrapp.android.dialog.MaterialDialog;


public class Clickoo extends AppCompatActivity {

    private ProgressDialog pDialog;
    private JSONObject json;
    private int success=4;
    private HTTPURLConnection service;
    private String id = "";

    //Initialize webservice URL
    private String pathviews = Global.ROOT_URL+"posts/views.php";

    private String pathlikes = Global.ROOT_URL+"posts/liked.php";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickoo);

        progressBar  = (ProgressBar) findViewById(R.id.progressBar);

        service=new HTTPURLConnection();

        new Clickoo.PostDataTOServerviews().execute();


        TextView   nav_Nameee=(TextView) findViewById(R.id.editText);
        nav_Nameee.setText(Global.title);


        TextView   nav_Name=(TextView) findViewById(R.id.textView2);
        nav_Name.setText(Global.description);

        TextView   nav_Namee=(TextView) findViewById(R.id.ic_views);
        nav_Namee.setText(Global.views+" Views");




        String filename =  Global.clickooimag;

        String filenameArray[] = filename.split("\\.");
        final  String extension = filenameArray[filenameArray.length-1];
        if(extension.equals("pdf")||extension.equals("docx")||extension.equals("doc"))
        {
           Toast.makeText(Clickoo.this,"You Have One Document",Toast.LENGTH_SHORT).show();
           ImageView lala = (ImageView) findViewById(R.id.imageView);
           lala.setImageResource(R.drawable.pdf);

           progressBar.setVisibility(View.GONE);

      // Global.clickooimag  = "https://drive.google.com/viewerng/viewer?url="+Global.clickooimag;
       Global.clickooimag  = "http://drive.google.com/viewerng/viewer?embedded=true&url="+Global.clickooimag;
        }
      // else if  ( Global.clickooimag.equals(Global.ROOT_URL+"principal/uploads/")||  Global.clickooimag.equals(Global.ROOT_URL+"ecehod/uploads/")){
        else if  ( Global.clickooimag.equals(Global.ROOT_URL)){

           // Toast.makeText(Clickoo.this,"No Image",Toast.LENGTH_SHORT).show();

            ImageView lala = (ImageView) findViewById(R.id.imageView);
            lala.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            //  ImageView lala1 = (ImageView) findViewById(R.id.documentview);
            //  lala1.setVisibility(View.GONE);

        }
        else
       {
           //  ImageView lala2 = (ImageView) findViewById(R.id.documentview);
          // lala2.setVisibility(View.GONE);

            final ImageView iv = (ImageView) findViewById(R.id.imageView);
            final String imgURL  =  Global.clickooimag;

            new DownLoadImageTask(iv).execute(imgURL);

       }

//###########Below code for loading student picture on load starts from here#####/////////

        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(extension.equals("pdf")||extension.equals("docx")||extension.equals("doc"))
                {

                    Intent intent = new Intent(Clickoo.this, PdfActivity.class);
                    startActivity(intent);
                }
                else
                    {

                    WebView webView = new WebView(Clickoo.this);
                    webView.loadUrl(Global.clickooimag);
                    // Below Two Lines Very Imp for fitting image
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setBuiltInZoomControls(true);

                    MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(Clickoo.this);
                    dialogBuilder.setTitle(Global.title);

                    dialogBuilder.setView(webView);

                    //  dialogBuilder.setPositiveButton("agree", null);

                    MaterialDialog dialog = dialogBuilder.create();
                    dialog.show();

                    }

            }
        });


    } //////////////////// Note: on create ends here



    public class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        public Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ e.printStackTrace();
                Toast.makeText(Clickoo.this,"Error Downloading Image",Toast.LENGTH_SHORT).show();
            }
            return logo;
        }

        public void onPostExecute(Bitmap result){

            progressBar.setVisibility(View.GONE);
            imageView.setImageBitmap(result);
        }
    }

//###########Below code for loading student picture on load starts from here#####/////////



    public void ic_heart_click(View v) {

        final MediaPlayer mp = MediaPlayer.create(Clickoo.this, R.raw.beep);
        mp.start();

        if (Global.liked.equals("1")){

            Global.liked="0";
            ImageView lala = (ImageView) findViewById(R.id.ic_heart);
            lala.setImageResource(R.drawable.ic_empty_heart);
        }
        else if(Global.liked.equals("0"))
        {
            Global.liked="1";
            ImageView lala = (ImageView) findViewById(R.id.ic_heart);
            lala.setImageResource(R.drawable.ic_heart);
        }
        new Clickoo.PostDataTOServer().execute();


    }

    private class PostDataTOServerviews extends AsyncTask<Void, Void, Void> {

        String response = "";
        HashMap<String, String> postDataParams;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            postDataParams=new HashMap<String, String>();
            postDataParams.put("id", Global.id);

            response= service.ServerData(pathviews,postDataParams);
            try {
                json = new JSONObject(response);
                success = json.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(success==1) {

                try {
                    json = new JSONObject(response);

                    Global.views=json.getString("views");
                    Global.liked=json.getString("liked");

                    TextView   nav_Namee=(TextView) findViewById(R.id.ic_views);
                    nav_Namee.setText(Global.views+" Views");

                    if ( Global.liked.equals("1")){
                        ImageView lala = (ImageView) findViewById(R.id.ic_heart);
                        lala.setImageResource(R.drawable.ic_heart);
                    }
                    else
                    {   ImageView lala = (ImageView) findViewById(R.id.ic_heart);
                        lala.setImageResource(R.drawable.ic_empty_heart);
                    }
                } catch (JSONException e) {e.printStackTrace();}
            }
            else{ Toast.makeText(getApplicationContext(),"Cannot Get Views ", Toast.LENGTH_LONG).show(); }
        }
    }


    private class PostDataTOServer extends AsyncTask<Void, Void, Void> {
        String response = "";
        HashMap<String, String> postDataParams;
        @Override
        protected void onPreExecute() {super.onPreExecute();}
        @Override
        protected Void doInBackground(Void... arg0) {
            postDataParams=new HashMap<String, String>();
            postDataParams.put("id", Global.id);
            postDataParams.put("liked", Global.liked);
            response= service.ServerData(pathlikes,postDataParams);
            return null;
        }
    }



}
