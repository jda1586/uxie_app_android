package mx.uxie.app.uxie;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.uxie.app.uxie.adapters.ComentAdapter;
import mx.uxie.app.uxie.adapters.ImageSlideAdapter;
import mx.uxie.app.uxie.items.ComentItem;


public class PlaceActivity extends AppCompatActivity {
    TextView tv_place_name, tv_description;
    RecyclerView Recycler_Comets;
    ViewPager mViewPager;
    List<String> images = new ArrayList<>();
    boolean stopSliding = false;
    private Handler handler;
    private Runnable animateViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_place_name = (TextView) findViewById(R.id.tv_place_name);
        tv_description = (TextView) findViewById(R.id.tv_description);
        Recycler_Comets = (RecyclerView) findViewById(R.id.Recycler_Comets);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        try {
            Bundle bundle = getIntent().getExtras();
            String place_name = bundle.getString("Place_name");
            String Place_description = bundle.getString("Place_description");
            tv_place_name.setText(place_name);
            tv_description.setText(Place_description);
        } catch (Exception e) {
        }
        setPagerView();
        PopulateList();
    }

    public void setPagerView() {

        images.add("image1");
        images.add("image2");
        images.add("image3");

        mViewPager.setAdapter(new ImageSlideAdapter(PlaceActivity.this, images,
                PlaceActivity.this));


        mViewPager.setHorizontalFadingEdgeEnabled(true);

        mViewPager.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (images != null && images.size() != 0) {
                            stopSliding = false;
                            runnable(images.size());
                            //handler.postDelayed(animateViewPager,
                            //      ANIM_VIEWPAGER_DELAY_USER_VIEW);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });

    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == size - 1) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    //-> handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            try {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (images != null) {
                    }
                }
            } catch (Exception e) {
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }
    }


    public void PopulateList() {
        List<ComentItem> ListPlaces = new ArrayList<>();
        ListPlaces.add(new ComentItem("En un buen lugar", "10/14/2016", "el1"));
        ListPlaces.add(new ComentItem("No esta mal", "10/18/2016", "el2"));
        ListPlaces.add(new ComentItem("Me agrada", "10/22/2016", "el1"));


        ComentAdapter Adapter = new ComentAdapter(ListPlaces, PlaceActivity.this);
        Recycler_Comets.setLayoutManager(new LinearLayoutManager(PlaceActivity.this));
        Recycler_Comets.setAdapter(Adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
        }
        return super.onOptionsItemSelected(item);
    }

}
