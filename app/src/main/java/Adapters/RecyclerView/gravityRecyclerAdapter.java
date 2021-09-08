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

public class gravityRecyclerAdapter extends RecyclerView.Adapter<gravityRecyclerAdapter.ViewHolder> {
    private List<gravity> allGravity;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_gravity, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String gx = String.valueOf(allGravity.get(position).getGx());
        String gy = String.valueOf(allGravity.get(position).getGy());
        String gz = String.valueOf(allGravity.get(position).getGz());
        holder.getGxTextView().setText(gx);
        holder.getGyTextView().setText(gy);
        holder.getGzTextView().setText(gz);
    }

    @Override
    public int getItemCount() {
        return allGravity.size();
    }
    public void setAllGravity(List<gravity> hardware){
        this.allGravity = hardware;
        notifyDataSetChanged();
    }
    public gravityRecyclerAdapter(List<gravity> allHardware){
        this.allGravity = allHardware;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView gxTextView;
        private final TextView gyTextView;
        private final TextView gzTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gxTextView = (TextView) itemView.findViewById(R.id.gxTextView);
            gyTextView = (TextView) itemView.findViewById(R.id.gyTextView);
            gzTextView = (TextView) itemView.findViewById(R.id.gzTextView);
        }

        public TextView getGxTextView() {
            return gxTextView;
        }

        public TextView getGyTextView() {
            return gyTextView;
        }

        public TextView getGzTextView() {
            return gzTextView;
        }
    }
}
