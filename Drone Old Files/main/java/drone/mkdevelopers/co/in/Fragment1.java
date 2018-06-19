package clickbaitstudio.developerstudentclub.com.in;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




public class Fragment1 extends Fragment {

    public Fragment1()  {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
    }

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;
    private ProgressDialog pDialog;

    ////// slide show
    SliderLayout sliderLayout ;

    HashMap<String, String> HashMapForURL ;

    HashMap<String, Integer> HashMapForLocalRes ;
    ///////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v= inflater.inflate(R.layout.fragment_one, container, false);

        //////// slide show
        sliderLayout = (SliderLayout)v.findViewById(R.id.slider);
        //////


///////////
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        data_list  = new ArrayList<>();
        //Log.d("card1:", Integer.toString(data_list.size()));
        load_data_from_server(0);
//very important span count is very imp here its so usefull u can keep it 2 or 1 or 3 or anything
        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);



       // Log.d("card3:", Integer.toString(data_list.size()));

        adapter = new CustomAdapter(getActivity(),data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    //Kinda Coment tesethe ifnity loops vastai vachinave vastai Very Imp Code
                    //  load_data_from_server(data_list.get(data_list.size()-1).getId());

                }

            }
        });

        /////////////////////////////////

        ///////////////////////////////// slide show

        //Call this method if you want to add images from URL .
        AddImagesUrlOnline();

        //Call this method to add images from local drawable folder .
        //AddImageUrlFormLocalRes();

        //Call this method to stop automatic sliding.
        //sliderLayout.stopAutoCycle();

        for(String name : HashMapForURL.keySet()){

            //  Idi pedite kinda black line lo name kooda vastadi
          //  TextSliderView textSliderView = new TextSliderView(getActivity());
              DefaultSliderView  textSliderView = new DefaultSliderView(getActivity());


            textSliderView
                    .description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
           // textSliderView.setOnSliderClickListener(getActivity());

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        // on unte oka laga vastadi   sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(3000);

      //  sliderLayout.addOnPageChangeListener(getActivity());
/////////////////


        return v;

    }

    private void load_data_from_server(int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
        // itla undali basically
   //http://griet.ga/drone/posts/getposts.php/?roll_no=3-ECE','3-ECE-B','2-3-4-ECE','15241a0401
   .url(Global.ROOT_URL+"posts/getposts.php/?roll_no="
           +Global.au.Roll_No+"','"
           +Global.au.Branch+"','"
           +Global.au.Year+"-"+Global.au.Branch+"','"
           +Global.au.Year+"-"+Global.au.Branch+"-"+Global.au.Section+"','ALL"


   )
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                       // Log.d("response:", "vvv");
                        //   Log.d("response:",  object.getString("name"));

                        MyData data = new MyData(
                                object.getString("id"),
                                object.getString("description"),
                                object.getString("image"),
                                object.getString("date"),
                                object.getString("title"),
                                object.getString("fromimage")

                        );

                        data_list.add(data);


                    }
                    //Log.d("card2:", Integer.toString(data_list.size()));


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);

    }

    private void shareit() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "The app");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://mkdevelopers.co.in/apps/drone.apk ");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }



    @Override
    public void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }


    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }


    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}


    public void onPageSelected(int position) {

        //Log.d("Slider Demo", "Page Changed: " + position);

    }


    public void onPageScrollStateChanged(int state) {

    }



    public void AddImagesUrlOnline(){

        HashMapForURL = new HashMap<String, String>();

        HashMapForURL.put("3", Global.ROOT_URL+"posts/bannerimages/3.jpeg");
        HashMapForURL.put("6", Global.ROOT_URL+"posts/bannerimages/6.jpeg");
        HashMapForURL.put("2", Global.ROOT_URL+"posts/bannerimages/2.jpeg");
        HashMapForURL.put("5", Global.ROOT_URL+"posts/bannerimages/5.jpeg");
        HashMapForURL.put("1", Global.ROOT_URL+"posts/bannerimages/1.jpeg");
        HashMapForURL.put("4", Global.ROOT_URL+"posts/bannerimages/4.jpeg");

    }

  /*  public void AddImageUrlFormLocalRes(){

        HashMapForLocalRes = new HashMap<String, Integer>();

        HashMapForLocalRes.put("CupCake", R.drawable.cupcake);
        HashMapForLocalRes.put("Donut", R.drawable.donut);
        HashMapForLocalRes.put("Eclair", R.drawable.eclair);
        HashMapForLocalRes.put("Froyo", R.drawable.froyo);
        HashMapForLocalRes.put("GingerBread", R.drawable.gingerbread);

    }*/



}