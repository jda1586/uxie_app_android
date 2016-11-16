package mx.uxie.app.uxie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.uxie.app.uxie.R;
import mx.uxie.app.uxie.events.ClickTableCell;
import mx.uxie.app.uxie.items.PlaceItem;


/**
 * Created by 72937 on 11/11/2016.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.mViewHolder>{
    private final List<PlaceItem> Results;
    Context context;
    private static LayoutInflater inflater = null;

    public PlaceAdapter(List<PlaceItem> Result, Context context) {
        Results = new ArrayList<>(Result);
        this.context = context;
        Log.e("listhChlid1", Results.size() + "");
    }
    ClickTableCell clickTableCell;

    public void setClickTableCell(ClickTableCell clickTableCell) {
        this.clickTableCell = clickTableCell;
    }

    @Override
    public void onBindViewHolder(mViewHolder mViewHolder, int i) {

        final PlaceItem Object = Results.get(i);
        mViewHolder.tv_place_name.setText(Object.getPlace_name());
        mViewHolder.tv_description.setText(Object.getPlace_Description());
        mViewHolder.tv_price.setText(Object.getPlace_price());
        mViewHolder.tv_rating.setText(Object.getRating()+"");
        if(Object.getRating()<6){
            mViewHolder.tv_rating.setBackgroundResource(R.color.md_yellow_800);
        }else{
            mViewHolder.tv_rating.setBackgroundResource(R.color.colorPrimary);
        }

        int resId = context.getResources().getIdentifier(Object.getPlace_imagen(), "mipmap", context.getPackageName());
        mViewHolder.iv_place_image.setImageResource(resId);
        mViewHolder.place_conteiner.setTag(Object);
        mViewHolder.place_conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout iV = (RelativeLayout) v;
                PlaceItem tO = (PlaceItem) iV.getTag();
                clickTableCell.onClickTableCell(tO);
            }
        });

    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categoria_lista_item, viewGroup, false);
        mViewHolder pvh = new mViewHolder(v);
        return pvh;
    }

    @Override
    public int getItemCount() {
        return Results.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        TextView tv_place_name;
        TextView tv_description;
        TextView tv_price;
        TextView tv_rating;
        ImageView iv_place_image;
        RelativeLayout place_conteiner;

        mViewHolder(View itemView) {
            super(itemView);

            tv_place_name = (TextView) itemView.findViewById(R.id.tv_place_name);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_rating = (TextView) itemView.findViewById(R.id.tv_rating);
            iv_place_image = (ImageView) itemView.findViewById(R.id.iv_place_image);
            place_conteiner = (RelativeLayout) itemView.findViewById(R.id.place_conteiner);

        }
    }
}
