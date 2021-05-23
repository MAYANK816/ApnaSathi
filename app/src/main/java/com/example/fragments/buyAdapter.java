package com.example.fragments;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.apnasathi.CropInsure;
import com.example.apnasathi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class buyAdapter extends RecyclerView.Adapter<buyAdapter.Viewholder> {
    DatabaseReference myRef;
    FirebaseDatabase database;
    Context context;
    List<CropInsure> helperList;

    public buyAdapter(@NonNull Context context, List<CropInsure> helperList) {
        this.context = context;
        this.helperList = helperList;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buylayout, parent, false);
        return new buyAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        CropInsure helper = helperList.get(position);

        holder.farmerName.setText(helper.getFarmerName());
        holder.farmeNumber.setText(helper.getFarmerNumber());
        holder.farmerAddress.setText(helper.getFarmerAddress());
        holder.farmerArea.setText(helper.getFarmerArea());
        holder.croptype.setText(helper.getFarmerCropType());
        Glide.with(context)
                .load(helperList.get(position).getImageUrl())
                .into(holder.imageView);
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("CropSell");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+helper.getFarmerNumber()));
                            v.getContext().startActivity(intent);
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return helperList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView farmerName;
        private TextView farmeNumber;
        private TextView farmerAddress;
        private TextView farmerArea;
        private ImageView imageView;
        private TextView croptype;
        private ImageButton callButton;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            farmeNumber=itemView.findViewById(R.id.farmerNumber);
            farmerName = itemView.findViewById(R.id.farmerName);
            farmerAddress = itemView.findViewById(R.id.CropType);
            imageView = itemView.findViewById(R.id.cropImageView);
            farmerArea = itemView.findViewById(R.id.farmerAddress);
            croptype = itemView.findViewById(R.id.farmerArea);
            callButton=itemView.findViewById(R.id.btncall);
        }

    }
}
