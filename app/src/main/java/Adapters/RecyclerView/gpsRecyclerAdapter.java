package Adapters.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

import roomGPS.gpsLocation;

public class gpsRecyclerAdapter extends RecyclerView.Adapter<gpsRecyclerAdapter.ViewHolder> {
    private List<gpsLocation> allGpsLocations;

    /*var Sleeplist = listOf<SleepNight>()
    set(value){
        field = value
        notifyDataSetChanged()
    }*/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private TextView id;
        private final TextView dateTime;
        private final TextView lng;
        private final TextView lat;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            //id = (TextView) view.findViewById(R.id.idTextView);
            lat = (TextView) view.findViewById(R.id.latTextView);
            lng = (TextView) view.findViewById(R.id.lngTextView);
            dateTime = (TextView) view.findViewById(R.id.dateTime);
        }
       /* public TextView getId(){
            //id = (TextView) itemView.findViewById(R.id.idTextView);
            return id;
        }*/
        public TextView getLatitude(){
            return lat;
        }
        public TextView getLongitude(){
            return lng;
        }
        public TextView getDateTime(){
            return dateTime;
        }
    }
    public void setAllGpsLocations(List<gpsLocation> gps){
        this.allGpsLocations = gps;
        notifyDataSetChanged();
    }
    public gpsRecyclerAdapter(List<gpsLocation> allLocations){
        this.allGpsLocations = allLocations;
        //Log.d("vendoAdapter2", allLocations.get(0).getLatitude()+"");
        //Log.d("vendoAdapter3", allGpsLocations.get(0).getLatitude()+"");
    }
    @NonNull
    @Override
    public gpsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_gps, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull gpsRecyclerAdapter.ViewHolder holder, int position) {
        //Log.d("vendoAdapter4", allGpsLocations.get(position).getLatitude()+"");
        //Log.d("position", position+"");
        //String id = String.valueOf(allGpsLocations.get(position).getId());
        String latitude = String.valueOf(allGpsLocations.get(position).getLatitude());
        String longitude = String.valueOf(allGpsLocations.get(position).getLongitude());
        String dateTime = String.valueOf(allGpsLocations.get(position).getDate());
        holder.getDateTime().setText(dateTime);
        holder.getLatitude().setText(latitude);
        holder.getLongitude().setText(longitude);
        //holder.getId().setText(id);
    }

    @Override
    public int getItemCount() {
        //Log.d("size:", "'e: " + allGpsLocations.size());
        return allGpsLocations.size();
    }
}
