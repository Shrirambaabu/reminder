package in.myreminder.srb.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.igenius.customcheckbox.CustomCheckBox;

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
    public void onBindViewHolder(@NonNull AlertAdapter.MyViewHolder holder, final int position) {

        MyAlert myAlertModel=myAlertArrayList.get(position);

        holder.reminderTime.setText(myAlertModel.getAlertTime());


        if (myAlertArrayList.get(position).getAlertRead().equals("1")) {
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Log.e("Chec", "" + isChecked + ":Pos:" + myAlertArrayList.get(position).getAlertId());

                if (isChecked) {
                    myAlertArrayList.get(position).setAlertRead("1");
                    changeCheckStatus(myAlertArrayList.get(position).getAlertId(), "1");

                } else {
                    myAlertArrayList.get(position).setAlertRead("0");
                    changeCheckStatus(myAlertArrayList.get(position).getAlertId(), "0");
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return myAlertArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView reminderTime;
        private CustomCheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reminderTime = itemView.findViewById(R.id.reminder_time);
            checkBox = itemView.findViewById(R.id.checkbox);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // stage delete request to admin 1,1
                    AlertDialog.Builder builderdelete = new AlertDialog.Builder(context);
                    builderdelete.setMessage("Are You Want Delete This Alert ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteNote(myAlertArrayList.get(getAdapterPosition()).getAlertId(), (getAdapterPosition()));
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.dismiss();
                                }
                            });
                    builderdelete.show();
                    return true;
                }
            });
        }
    }

    private void deleteNote(int alertId, int position) {
        databaseHelper.deleteAlert(String.valueOf(alertId));
        myAlertArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, myAlertArrayList.size());

    }


    private void changeCheckStatus(int alertId, String status) {
        databaseHelper.updateAlert(alertId, status);
        // notifyDataSetChanged();

    }
}
