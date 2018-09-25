package udacity.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import udacity.bakingapp.AdapterItemClickListener;
import udacity.bakingapp.DetailView;
import udacity.bakingapp.R;
import udacity.bakingapp.model.Steps;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private ArrayList<Steps> stepsList;
    private Context context;

    //click interface
    public AdapterItemClickListener adapterItemClickListener;

    public static class StepsViewHolder extends RecyclerView.ViewHolder{
        public TextView stepDescription;
        public ImageView videoIcon;
        public LinearLayout parentLayout;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepDescription = itemView.findViewById(R.id.step_description);
            videoIcon = itemView.findViewById(R.id.video_image_view);
            parentLayout = itemView.findViewById(R.id.parentLayoutStep);
        }
    }

    public StepsAdapter(ArrayList<Steps> steps, Context context, AdapterItemClickListener adapterItemClickListener){
        this.stepsList = steps;
        this.context = context;
        this.adapterItemClickListener = adapterItemClickListener;

    }


    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        StepsViewHolder stepsViewHolder = new StepsViewHolder(view);
        return stepsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, final int position) {
        final Steps step = stepsList.get(position);

        holder.stepDescription.setText((position + 1) + ". " + step.getShortDescription());
        if(step.getVideoURL().equals("")){
            holder.videoIcon.setImageDrawable(null);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterItemClickListener.onItemClick(position);

                /*
                Intent intent = new Intent(context, DetailView.class);

                intent.putExtra("step", step);
                context.startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

}
