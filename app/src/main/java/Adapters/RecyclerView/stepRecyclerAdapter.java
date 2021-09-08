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
import roomSensors.entities.stepCounter;

public class stepRecyclerAdapter extends RecyclerView.Adapter<stepRecyclerAdapter.ViewHolder>{
    private List<stepCounter> allStep;
    @NonNull
    @Override
    public stepRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_step, parent, false);
        return new stepRecyclerAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull stepRecyclerAdapter.ViewHolder holder, int position) {
        String step = String.valueOf(allStep.get(position).getStep());
        holder.getStepTextView().setText(step);
    }

    public stepRecyclerAdapter(List<stepCounter> allst){
        this.allStep = allst;
    }

    @Override
    public int getItemCount() {
        return allStep.size();
    }

    public void setAllStep(List<stepCounter> step){
        this.allStep = step;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView stepTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepTextView = (TextView) itemView.findViewById(R.id.stepTextView);
        }

        public TextView getStepTextView() {
            return stepTextView;
        }
    }
}
