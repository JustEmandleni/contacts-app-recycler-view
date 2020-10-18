package com.contacts.my.are.these;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Model model = arrayList.get(position);

        String id, image, name, phone, company, addTimeStamp, updateTimeStamp;
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
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView avatarImageView;
        TextView nameTextView, phoneTextView, companyTextView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            avatarImageView = itemView.findViewById(R.id.imgAvatar);
            nameTextView = itemView.findViewById(R.id.lblName);
            phoneTextView = itemView.findViewById(R.id.lblPhone);
            companyTextView = itemView.findViewById(R.id.lblCompany);
        }

    }


}
