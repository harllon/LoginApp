package Adapters.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

import roomSensors.entities.gravity;
import roomSensors.entities.gyroscope;

public class gyroscopeRecyclerAdapter extends RecyclerView.Adapter<gyroscopeRecyclerAdapter.ViewHolder>{
    private List<gyroscope> allGyroscope;
    @NonNull
    @Override
    public gyroscopeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_gyroscope, parent, false);
        return new gyroscopeRecyclerAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull gyroscopeRecyclerAdapter.ViewHolder holder, int position) {
        String wx = String.valueOf(allGyroscope.get(position).getWx());
        String wy = String.valueOf(allGyroscope.get(position).getWy());
        String wz = String.valueOf(allGyroscope.get(position).getWz());
        holder.getWxTextView().setText(wx);
        holder.getWyTextView().setText(wy);
        holder.getWzTextView().setText(wz);
    }

    @Override
    public int getItemCount() {
        return allGyroscope.size();
    }

    public gyroscopeRecyclerAdapter(List<gyroscope> allGyro){
        this.allGyroscope = allGyro;
    }
    public void setAllGyroscope(List<gyroscope> gyro){
        this.allGyroscope = gyro;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView wxTextView;
        private final TextView wyTextView;
        private final TextView wzTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wxTextView = (TextView) itemView.findViewById(R.id.wxTextView);
            wyTextView = (TextView) itemView.findViewById(R.id.wyTextView);
            wzTextView = (TextView) itemView.findViewById(R.id.wzTextView);
        }

        public TextView getWxTextView() {
            return wxTextView;
        }

        public TextView getWyTextView() {
            return wyTextView;
        }

        public TextView getWzTextView() {
            return wzTextView;
        }
    }
}
