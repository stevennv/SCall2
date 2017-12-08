package com.example.admin.scall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.scall.R;
import com.example.admin.scall.activity.EditNameActivity;
import com.example.admin.scall.model.InfoStyle;

import java.util.List;

/**
 * Created by admin on 12/8/2017.
 */

public class InfoStyleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<InfoStyle> list;

    public InfoStyleAdapter(Context context, List<InfoStyle> list) {
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
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        final InfoStyle infoStyle = list.get(position);
        myViewHolder.tvName.setText(infoStyle.getName() + "\n" + "\t \t" + infoStyle.getPhone());
        if (infoStyle.getFont() != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + infoStyle.getFont());
            myViewHolder.tvName.setTypeface(typeface);
        }
        myViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNameActivity.class);
                intent.putExtra("Style", infoStyle);
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
