package clickbaitstudio.developerstudentclub.com.in;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Fragment4 extends Fragment {
    private ProgressBar progressBar;
    private TextView credits,attendance,backlogs;
    private String creditss,backlogss,attendancee;
    public Fragment4() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_four,
                container, false);

        progressBar  = (ProgressBar) v.findViewById(R.id.progressBar11);




        load_data_from_server(0);

         credits = (TextView) v.findViewById(R.id.credits);

        backlogs = (TextView) v.findViewById(R.id.backlogs);

         attendance = (TextView) v.findViewById(R.id.attendance);




        TextView Roll_No = (TextView) v.findViewById(R.id.Roll_No);
        Roll_No.setText(Global.au.Roll_No);

        TextView Name = (TextView) v.findViewById(R.id.Name);
        Name.setText(Global.au.Name);

//###########Below code for loading student picture on load starts from here#####/////////
        final   ImageView iv = (ImageView) v.findViewById(R.id.iv1);
        final String imgURL  = "http://exams.griet.in/photosrgb/"+Global.au.Roll_No+".JPG";

        new DownLoadImageTask(iv).execute(imgURL);
//###########Below code for loading student picture on load starts from here#####/////////
        // userid1.setText("UserID:"+Global.getsroll_no1()+" "+"Mobile No:"+Global.getsName1());


       /* ImageView button = (ImageView) v.findViewById(R.id.game1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.webviewurl="games/ho.php";
                startActivity(new Intent(getActivity(),GamesActivity.class));
            }
        });*/


        return v;
    }




    //###########Below code for loading student picture on load starts from here#####/////////
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ e.printStackTrace();}
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            progressBar.setVisibility(View.GONE);
            imageView.setImageBitmap(result);
        }
    }
//###########Below code for loading student picture on load starts from here#####/////////

    private void load_data_from_server(int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Global.ROOT_URL+"posts/attendance.php?roll_no="+Global.au.Roll_No)

                        .build();



                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());



                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                       creditss =  object.getString("credits");
                       backlogss =  object.getString("backlogs");
                       attendancee = object.getString("attendance");

                       // Log.d("hewe",object.getString("credits"));
                     //   Log.d("hewe",object.getString("backlogs"));
                      //  Log.d("hewe",object.getString("attendance"));


                    }



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {

                credits.setText(creditss);
                attendance.setText(attendancee);
                backlogs.setText(" "+backlogss);

            }
        };

        task.execute(id);

    }




}