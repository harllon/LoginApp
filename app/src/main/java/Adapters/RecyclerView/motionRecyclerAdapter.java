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
import roomSensors.entities.motion;

public class motionRecyclerAdapter extends RecyclerView.Adapter<motionRecyclerAdapter.ViewHolder>{
    private List<motion> allMotion;
    @NonNull
    @Override
    public motionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_motion, parent, false);
        return new motionRecyclerAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull motionRecyclerAdapter.ViewHolder holder, int position) {
        String motion = String.valueOf(allMotion.get(position).getMotion());
        holder.getMotionTextView().setText(motion);
    }

    @Override
    public int getItemCount() {
        return allMotion.size();
    }
    public motionRecyclerAdapter(List<motion> allMot){
        this.allMotion = allMot;
    }
    public void setAllMotion(List<motion> mot){
        this.allMotion = mot;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView motionTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            motionTextView = (TextView) itemView.findViewById(R.id.motionTextView);
        }

        public TextView getMotionTextView() {
            return motionTextView;
        }
    }
}
