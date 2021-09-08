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
import roomSensors.entities.rotation;

public class rotationRecyclerAdapter extends RecyclerView.Adapter<rotationRecyclerAdapter.ViewHolder>{
    private List<rotation> allRotation;
    @NonNull
    @Override
    public rotationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rotation, parent, false);
        return new rotationRecyclerAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull rotationRecyclerAdapter.ViewHolder holder, int position) {
        String xsin = String.valueOf(allRotation.get(position).getXsin());
        String ysin = String.valueOf(allRotation.get(position).getYsin());
        String zsin = String.valueOf(allRotation.get(position).getZsin());
        String cos = String.valueOf(allRotation.get(position).getCos());
        String sha = String.valueOf(allRotation.get(position).getSha());
        holder.getCosTextView().setText(cos);
        holder.getShaTextView().setText(sha);
        holder.getXsinTextView().setText(xsin);
        holder.getYsinTextView().setText(ysin);
        holder.getZsinTextView().setText(zsin);
    }

    @Override
    public int getItemCount() {
        return allRotation.size();
    }

    public void setAllRotation(List<rotation> rot){
        this.allRotation = rot;
        notifyDataSetChanged();
    }

    public rotationRecyclerAdapter(List<rotation> allRot){
        this.allRotation = allRot;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView xsinTextView;
        private final TextView ysinTextView;
        private final TextView zsinTextView;
        private final TextView cosTextView;
        private final TextView shaTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            xsinTextView = (TextView) itemView.findViewById(R.id.xsinTextView);
            ysinTextView = (TextView) itemView.findViewById(R.id.ysinTextView);
            zsinTextView = (TextView) itemView.findViewById(R.id.zsinTextView);
            cosTextView = (TextView) itemView.findViewById(R.id.cosTextView);
            shaTextView = (TextView) itemView.findViewById(R.id.accuracyTextView);
        }

        public TextView getCosTextView() {
            return cosTextView;
        }

        public TextView getShaTextView() {
            return shaTextView;
        }

        public TextView getXsinTextView() {
            return xsinTextView;
        }

        public TextView getYsinTextView() {
            return ysinTextView;
        }

        public TextView getZsinTextView() {
            return zsinTextView;
        }
    }
}
