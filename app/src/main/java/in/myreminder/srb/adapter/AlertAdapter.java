package in.myreminder.srb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.myreminder.srb.R;
import in.myreminder.srb.database.DatabaseHelper;
import in.myreminder.srb.model.MyAlert;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.MyViewHolder> {


    private ArrayList<MyAlert> myAlertArrayList = new ArrayList<MyAlert>();
    private Context context;
    private DatabaseHelper databaseHelper;

    public AlertAdapter(ArrayList<MyAlert> myAlertArrayList, Context context) {
        this.myAlertArrayList = myAlertArrayList;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public AlertAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reminder_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return myAlertArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
