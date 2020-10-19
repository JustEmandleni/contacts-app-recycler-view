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
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Model model = arrayList.get(position);

        final String id, image, name, phone, company, addTimeStamp, updateTimeStamp;
        id = model.getId();
        image = model.getImage();
        name = model.getName();
        phone = model.getPhone();
        company = model.getCompany();
        addTimeStamp = model.getAddTimeStamp();
        updateTimeStamp = model.getUpdateTimeStamp();

        holder.avatarImageView.setImageURI(Uri.parse(image));
        holder.companyTextView.setText(company);
        holder.phoneTextView.setText(phone);
        holder.nameTextView.setText(name);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(
                        "" + position,
                        "" + id,
                        "" + image,
                        "" + name,
                        "" + phone,
                        "" + company,
                        "" + addTimeStamp,
                        "" + updateTimeStamp
                );
            }
        });
    }

    private void editDialog(String position, final String id, final String image, final String name, final String phone, final String company, final String addTimeStamp, final String updateTimeStamp) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Contact");
        builder.setMessage("Do you want to update contact?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_edit_button);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, UpdateContactActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("IMAGE", image);
                intent.putExtra("NAME", name);
                intent.putExtra("PHONE", phone);
                intent.putExtra("COMPANY", company);
                intent.putExtra("ADDTIMESTAMP", addTimeStamp);
                intent.putExtra("UPDATETIMESTAMP", updateTimeStamp);
                intent.putExtra("editMode", true);
                context.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //NB:
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        ImageView avatarImageView;
        TextView nameTextView, phoneTextView, companyTextView;
        ImageButton editButton;

        public Holder(@NonNull View itemView) {
            super(itemView);

            avatarImageView = itemView.findViewById(R.id.imgAvatar);
            nameTextView = itemView.findViewById(R.id.lblName);
            phoneTextView = itemView.findViewById(R.id.lblPhone);
            companyTextView = itemView.findViewById(R.id.lblCompany);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }


}
