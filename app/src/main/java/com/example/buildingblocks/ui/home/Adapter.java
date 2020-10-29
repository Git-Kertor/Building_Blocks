package com.example.buildingblocks.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildingblocks.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<list_element> list_list;
    private List<CompoundButton> boxes_list;
    public View toDelete;

    public Adapter(List<list_element> list_list)
    {
        this.list_list = list_list;
        boxes_list = new ArrayList<CompoundButton>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String _text = list_list.get(position).getString();
        boolean check = list_list.get(position).getBool();
        int order = list_list.get(position).getOrder();
        holder.itemView.setId(order);

        boxes_list.add(holder.itemView.findViewById(R.id.checkBox));

        setAnimation(holder.itemView, position);
        holder.setData(_text, check);
    }

    public void setAnimationOut(View viewToAnimate)
    {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_out_right);
        animation.setStartOffset(0);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeAt(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewToAnimate.startAnimation(animation);
    }

    public void removeAt(int position) {
        list_list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_list.size());
    }

    public void setAnimation(View viewToAnimate, int position)
    {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            animation.setStartOffset(list_list.get(position).getOrder() * 200 + 200);
            viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return list_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox _texts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _texts = itemView.findViewById(R.id.checkBox);

            _texts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        toDelete = buttonView;
                        setAnimationOut(buttonView);
                        buttonView.setClickable(false);
                    }
                }
            });
        }


        private void setData(String _text, boolean check)
        {
            _texts.setText(_text);
            _texts.setChecked(check);
        }

    }
}
