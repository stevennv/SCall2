package com.example.admin.scall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.scall.R;
import com.example.admin.scall.activity.DetailContactActivity;
import com.example.admin.scall.activity.EditNameActivity;
import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;
import com.example.admin.scall.utils.SqliteHelper;
import com.example.admin.scall.utils.Utils;

import java.util.List;

/**
 * Created by Admin on 11/21/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Contact> list;
    private SqliteHelper db;

    public ContactAdapter(Context context, List<Contact> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        db = new SqliteHelper(context);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Contact contact = list.get(position);
        myViewHolder.tvName.setText(contact.getName() + "\n" + "\t \t" + contact.getPhoneNumber());
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNameActivity.class);
                intent.putExtra("Main", "Abc");
                intent.putExtra("Contact", contact);
                String phoneFormat = Utils.formatNumber(contact.getPhoneNumber());
                try {
                    InfoStyle infoStyle = db.getStyleById(phoneFormat);
                    intent.putExtra("Style", infoStyle);
                } catch (Exception e) {
                    Log.d("onClick:", "onClick: " + e.getMessage());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llItem;
        public TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
