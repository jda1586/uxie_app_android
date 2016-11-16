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
import mx.uxie.app.uxie.items.ComentItem;


/**
 * Created by 72937 on 11/11/2016.
 */

public class ComentAdapter extends RecyclerView.Adapter<ComentAdapter.mViewHolder> {

    private final List<ComentItem> Results;
    Context context;
    private static LayoutInflater inflater = null;

    public ComentAdapter(List<ComentItem> Result, Context context) {
        Results = new ArrayList<>(Result);
        this.context = context;
        Log.e("listhChlid1", Results.size() + "");
    }


    @Override
    public void onBindViewHolder(ComentAdapter.mViewHolder mViewHolder, int i) {

        final ComentItem Object = Results.get(i);
        mViewHolder.tv_coment.setText(Object.getUser_coment());
        mViewHolder.tv_date.setText(Object.getUser_Date());

        int resId = context.getResources().getIdentifier(Object.getUser_image(), "mipmap", context.getPackageName());
        mViewHolder.iv_userimage.setImageResource(resId);

        /*
        mViewHolder.place_conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout iV = (RelativeLayout) v;
                String tO = (String) iV.getTag();
                clickTableCell.onClickTableCell(tO);
            }
        });
        */
    }

    @Override
    public ComentAdapter.mViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_lista_item, viewGroup, false);
        ComentAdapter.mViewHolder pvh = new ComentAdapter.mViewHolder(v);
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

        TextView tv_coment;
        TextView tv_date;
        ImageView iv_userimage;
        RelativeLayout place_conteiner;

        mViewHolder(View itemView) {
            super(itemView);

            tv_coment = (TextView) itemView.findViewById(R.id.tv_coment);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            iv_userimage = (ImageView) itemView.findViewById(R.id.iv_userimage);
            place_conteiner = (RelativeLayout) itemView.findViewById(R.id.place_conteiner);

        }
    }

}
