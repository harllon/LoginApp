package Adapters.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

import roomSensors.entities.accelerometer;
import roomSensors.entities.gravity;

public class accelerometerRecyclerAdapter extends RecyclerView.Adapter<accelerometerRecyclerAdapter.ViewHolder>{
    private List<accelerometer> allAccelerometer;
    @NonNull
    @Override
    public accelerometerRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_accelerometer, parent, false);
        return new accelerometerRecyclerAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull accelerometerRecyclerAdapter.ViewHolder holder, int position) {
        //if(allSensor.get(position).getAx() != 0.0)
            String ax = String.valueOf(allAccelerometer.get(position).getAx());
            Log.d("opa: ", "entrei aq ein cla");
            holder.getAxTextView().setText(ax);

        //if(allSensor.get(position).getAy() != 0.0)
            String ay = String.valueOf(allAccelerometer.get(position).getAy());
            holder.getAyTextView().setText(ay);

        //if(allSensor.get(position).getAz() != 0.0)
            String az = String.valueOf(allAccelerometer.get(position).getAz());
            holder.getAzTextView().setText(az);



    }
    public void setAllAccelerometer(List<accelerometer> hardware){
        this.allAccelerometer = hardware;
        notifyDataSetChanged();
    }
    public accelerometerRecyclerAdapter(List<accelerometer> allHardware){
        this.allAccelerometer = allHardware;
    }
    @Override
    public int getItemCount() {
        /*int counter = 0;
        for(int i = 0; i< allAccelerometer.size(); i++){
            if(allAccelerometer.get(i).getAx() == 0){
                counter = counter + 1;
            }
        }*/
        return allAccelerometer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView axTextView;
        private final TextView ayTextView;
        private final TextView azTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            axTextView = (TextView) itemView.findViewById(R.id.axTextView);
            ayTextView = (TextView) itemView.findViewById(R.id.ayTextView);
            azTextView = (TextView) itemView.findViewById(R.id.azTextView);
        }

        public TextView getAxTextView() {
            return axTextView;
        }

        public TextView getAyTextView() {
            return ayTextView;
        }

        public TextView getAzTextView() {
            return azTextView;
        }
    }
}
