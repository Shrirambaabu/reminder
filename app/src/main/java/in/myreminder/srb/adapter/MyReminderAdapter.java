package in.myreminder.srb.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import in.myreminder.srb.model.MyNotes;

import static in.myreminder.srb.utils.Utils.showFancyDate;

public class MyReminderAdapter extends RecyclerView.Adapter<MyReminderAdapter.MyViewHolder> {


    private ArrayList<MyNotes> myNotesArrayList = new ArrayList<MyNotes>();
    private Context context;
    private DatabaseHelper databaseHelper;

    public MyReminderAdapter(ArrayList<MyNotes> myNotesArrayList, Context context) {
        this.myNotesArrayList = myNotesArrayList;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyReminderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyReminderAdapter.MyViewHolder holder, final int position) {

        final MyNotes myNotesModel = myNotesArrayList.get(position);

        holder.noteName.setText(myNotesModel.getNotesName());
        holder.noteDate.setText(showFancyDate(myNotesModel.getNotesDate()));
        holder.notePriority.setText(myNotesModel.getNotesPriority());
        if (holder.notePriority.getText().toString().equals("Normal")) {
            holder.notePriority.setTextColor(Color.parseColor("#54D66A"));
        }
        if (holder.notePriority.getText().toString().equals("Medium")) {
            holder.notePriority.setTextColor(Color.parseColor("#FFC400"));
        }
        if (holder.notePriority.getText().toString().equals("High")) {
            holder.notePriority.setTextColor(Color.parseColor("#F44336"));
        }

        if ( myNotesArrayList.get(position).getNotesRead().equals("1")) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setEnabled(false);
        }

        holder.checkBox.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Log.e("Chec", "" + isChecked + ":Pos:" +  myNotesArrayList.get(position).getNotesId());

                if (isChecked) {
                    if (myNotesArrayList.get(position).getNotesRead().equals("0")) {
                        myNotesArrayList.get(position).setNotesRead("1");
                        changeCheckStatus(myNotesArrayList.get(position).getNotesId());
                    }
                }else {

                }

            }
        });

    }

    private void changeCheckStatus(int notesId) {
        databaseHelper.updateNotes(notesId);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return myNotesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView noteName, noteDate, notePriority;

        private CustomCheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.note_name);
            noteDate = itemView.findViewById(R.id.date);
            notePriority = itemView.findViewById(R.id.priority);
            checkBox = itemView.findViewById(R.id.checkbox);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // stage delete request to admin 1,1
                    AlertDialog.Builder builderdelete = new AlertDialog.Builder(context);
                    builderdelete.setMessage("Are You Want Delete This Note ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteNote(myNotesArrayList.get(getAdapterPosition()).getNotesId(), (getAdapterPosition()));
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

    private void deleteNote(int notesId, int position) {
        databaseHelper.deleteNotes(String.valueOf(notesId));
        myNotesArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, myNotesArrayList.size());

    }

}
