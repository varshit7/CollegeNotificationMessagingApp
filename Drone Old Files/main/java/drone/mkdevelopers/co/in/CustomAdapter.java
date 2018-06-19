package clickbaitstudio.developerstudentclub.com.in;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{


    private final Context context;
    private List<MyData> my_data;

    public CustomAdapter(Context context, List<MyData> my_data) {
        this.context = context;
        this.my_data = my_data;
          }


    public void ReloadDatax(List<MyData> my_data)
    {
        this.my_data = my_data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);

        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.description.setText(my_data.get(position).getDescription());
        holder.date.setText(my_data.get(position).getDate());
        holder.title.setText(my_data.get(position).getTitle());

       // Log.d("response:", my_data.get(position).getfromimage_link());

       // Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.imageView);

       //  Glide.with(context).load(my_data.get(position).getfromimage_link()).into(holder.imageView);
        if( my_data.get(position).getfromimage_link().equals("Principal.jpeg"))
        {
            Glide.with(context).load(R.drawable.principal).into(holder.fromimageView);
        }
       else if  ( my_data.get(position).getfromimage_link().equals("ECEHOD.jpeg") )
        {
            Glide.with(context).load(R.drawable.csehod).into(holder.fromimageView);
        }
        else if( my_data.get(position).getfromimage_link().equals("CSEHOD.jpeg"))
        {
            Glide.with(context).load(R.drawable.csehod).into(holder.fromimageView);
        }



        holder.fromimageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Clickoo.class);
                Global.title = my_data.get(position).getTitle();
                Global.description = my_data.get(position).getDescription();
                Global.clickooimag = Global.ROOT_URL+my_data.get(position).getImage_link();
                Global.id= my_data.get(position).getId();
                context.startActivity(intent);
            }

        });

        holder.title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Clickoo.class);
                Global.title = my_data.get(position).getTitle();
                Global.description = my_data.get(position).getDescription();
                Global.clickooimag = Global.ROOT_URL+my_data.get(position).getImage_link();
                Global.id= my_data.get(position).getId();
                context.startActivity(intent);
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Clickoo.class);
                Global.title = my_data.get(position).getTitle();
                Global.description = my_data.get(position).getDescription();
                Global.clickooimag = Global.ROOT_URL+my_data.get(position).getImage_link();
                Global.id= my_data.get(position).getId();
                context.startActivity(intent);
            }

        });



    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description,date,title;
        public ImageView imageView,fromimageView;



        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            fromimageView = (ImageView) itemView.findViewById(R.id.fromimage);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.title);
          //  fromimageView.setImageResource(R.drawable.ic_heart);
            //  imageView = (ImageView) itemView.findViewById(R.id.image);


        }
    }


}


