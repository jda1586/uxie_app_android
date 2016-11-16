package mx.uxie.app.uxie.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import mx.uxie.app.uxie.R;


/**
 * Created by 72937 on 11/11/2016.
 */

public class ImageSlideAdapter extends PagerAdapter {
    //ImageLoader imageLoader = ImageLoader.getInstance();
    //DisplayImageOptions options;
    FragmentActivity activity;
    List<String> products;
    Context context;
    private Dialog dialog;

    public ImageSlideAdapter(FragmentActivity activity, List<String> products,
                             Context context) {
        this.activity = activity;
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vp_image, container, false);

        ImageView mImageView = (ImageView) view
                .findViewById(R.id.image_display);



        mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                Fragment fragment = null;
                Log.e("position adapter", "" + position);
                //final String product = (String) products.get(position);
                //arguments.putParcelable("singleProduct", product);

            }
        });
            int resId = context.getResources().getIdentifier(products.get(position), "mipmap", context.getPackageName());
            mImageView.setBackgroundResource(resId);
        //iv_circle.setBackgroundResource(resId);
            mImageView.setTag(products.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
