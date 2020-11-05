package com.contacts.my.are.these;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private ArrayList<Model> arrayList;

    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        Model model = arrayList.get(position);

        final String id, image, date, entryType, description, addTimeStamp, updateTimeStamp;
        id = model.getId();
        image = model.getImage();
        date = model.getDate();
        entryType = model.getEntryType();
        description = model.getDescription();
        addTimeStamp = model.getAddTimeStamp();
        updateTimeStamp = model.getUpdateTimeStamp();

        holder.avatarImageView.setImageURI(Uri.parse(image));
        holder.descriptionTextView.setText(description);
        holder.entryTypeTextView.setText(entryType);
        holder.dateTextView.setText(date);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(
                        //"" + position,
                        "" + id,
                        "" + image,
                        "" + date,
                        "" + entryType,
                        "" + description,
                        "" + addTimeStamp,
                        "" + updateTimeStamp
                );
            }
        });
    }

    private void editDialog(final String id, final String image, final String date, final String entryType, final String description, final String addTimeStamp, final String updateTimeStamp) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Edit Contact");
//        builder.setMessage("Do you want to update contact?");
//        builder.setCancelable(false);
//        builder.setIcon(R.drawable.ic_edit_button);
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, UpdateContactActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("IMAGE", image);
                intent.putExtra("DATE", date);
                intent.putExtra("ENTRY_TYPE", entryType);
                intent.putExtra("DESCRIPTION", description);
                intent.putExtra("ADDTIMESTAMP", addTimeStamp);
                intent.putExtra("UPDATETIMESTAMP", updateTimeStamp);
                intent.putExtra("editMode", true);
                context.startActivity(intent);
//            }
//        });
//
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //NB:
//                dialog.cancel();
//            }
//        });
//
//        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        ImageView avatarImageView;
        TextView dateTextView, entryTypeTextView, descriptionTextView;
        ImageButton editButton;

        public Holder(@NonNull View itemView) {
            super(itemView);

            avatarImageView = itemView.findViewById(R.id.imgAvatar);
            dateTextView = itemView.findViewById(R.id.lblDate);
            entryTypeTextView = itemView.findViewById(R.id.lblEntryType);
            descriptionTextView = itemView.findViewById(R.id.lblDescription);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }


}
