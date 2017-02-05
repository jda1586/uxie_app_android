package mx.uxie.app.uxie;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mx.uxie.app.uxie.Utils.CommunicationDB;
import mx.uxie.app.uxie.adapters.PlaceAdapter;
import mx.uxie.app.uxie.events.ClickTableCell;
import mx.uxie.app.uxie.events.RetunDataEvent;
import mx.uxie.app.uxie.items.PlaceItem;


public class CategoriaActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, ClickTableCell, RetunDataEvent, GoogleMap.OnCameraChangeListener {

    private String category_name = "";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private MapFragment mapFragment;
    protected Location mLastLocation;
    private LatLng mLatLng;
    private TextView category_title;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_LOCATION = 2;
    private GoogleMap googleMapV;
    protected LocationManager locationManager;
    private Bitmap smallMarker;
    private RecyclerView Recycler_places;
    private CommunicationDB communicationDB;
    private ProgressDialog progressDialogData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        category_title = (TextView) findViewById(R.id.category_title);
        Recycler_places = (RecyclerView) findViewById(R.id.Recycler_places);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            Bundle bundle = getIntent().getExtras();
            category_name = bundle.getString("categoria_Nombre");
            category_title.setText(category_name);
        } catch (Exception e) {
        }
        StatusBar(getResources().getColor(R.color.status));

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(CategoriaActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CategoriaActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                getUserLocation();

            }
        } else {
            getUserLocation();
        }
        progressDialogData = ProgressDialog.show(this, "Uxie",
                getString(R.string.LoadData), true);
        // progressDialogData.setCancelable(true);
        progressDialogData.show();
        //PopulateList();

    }

    public void StatusBar(int Color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color);
        }
    }

    public void PopulateList(List<PlaceItem> ListPlacess) {
        List<PlaceItem> ListPlaces = new ArrayList<>();
        /*ListPlaces.add(new PlaceItem("Local1", "Descripcion Local 1 detalles referecias, que ofrece, direccion, otros", "r1", "$ $ $ $", 8.4f));
        ListPlaces.add(new PlaceItem("Local2", "Descripcion Local 2 detalles referecias, que ofrece, direccion, otros", "r2", "$ $", 7.5f));
        ListPlaces.add(new PlaceItem("Local3", "Descripcion Local 3 detalles referecias, que ofrece, direccion, otros", "r3", "$ $ $", 9.2f));
        ListPlaces.add(new PlaceItem("Local4", "Descripcion Local 4 detalles referecias, que ofrece, direccion, otros", "r4", "$", 5.3f));
        ListPlaces.add(new PlaceItem("Local5", "Descripcion Local 5 detalles referecias, que ofrece, direccion, otros", "r5", "$ $ $", 8.4f));
        */
        PlaceAdapter companiesAdapter = new PlaceAdapter(ListPlacess, CategoriaActivity.this);
        companiesAdapter.setClickTableCell(this);
        Recycler_places.setLayoutManager(new LinearLayoutManager(CategoriaActivity.this));
        Recycler_places.setAdapter(companiesAdapter);
    }

    public void getUserLocation() {

        locationManager = (LocationManager) this
                .getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                20,
                10, this);


        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle arg0) {

                        if (ActivityCompat.checkSelfPermission(CategoriaActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Check Permissions Now
                            ActivityCompat.requestPermissions(CategoriaActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

                            mLastLocation = LocationServices.FusedLocationApi
                                    .getLastLocation(mGoogleApiClient);

                            mLatLng = new LatLng(mLastLocation.getLatitude(),
                                    mLastLocation.getLongitude());

                            communicationDB = new CommunicationDB(CategoriaActivity.this);
                            communicationDB.OnReturnData(CategoriaActivity.this);
                            communicationDB.shops(mLatLng.latitude, mLatLng.longitude, category_name);
                            CenterMap();

                        } else {

                            mLastLocation = LocationServices.FusedLocationApi
                                    .getLastLocation(mGoogleApiClient);

                            mLatLng = new LatLng(mLastLocation.getLatitude(),
                                    mLastLocation.getLongitude());

                            communicationDB = new CommunicationDB(CategoriaActivity.this);
                            communicationDB.OnReturnData(CategoriaActivity.this);
                            communicationDB.shops(mLatLng.latitude, mLatLng.longitude, category_name);

                            CenterMap();
                            //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 10.0f));
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int arg0) {
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                    @Override
                    public void onConnectionFailed(ConnectionResult conResult) {
                        if (conResult.hasResolution()) {
                            try {
                                conResult.startResolutionForResult(CategoriaActivity.this,
                                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation();
                } else {
                    // Permission Denied
                    //Toast.makeText(HomeActivity.this, "LOCALIZACION DENEGADA", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapV = googleMap;
        googleMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LatLng Andares = new LatLng(20.709008, -103.412186);
        try {
            Andares = new LatLng(mLatLng.latitude, mLatLng.longitude);
        } catch (Exception e) {
        }
        googleMapV.setMyLocationEnabled(true);
        googleMapV.moveCamera(CameraUpdateFactory.newLatLngZoom(Andares, 14.5f));
        googleMapV.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }

    public void CenterMap() {
        LatLng Center = new LatLng(20.709008, -103.412186);
        try {
            Center = new LatLng(mLatLng.latitude, mLatLng.longitude);
        } catch (Exception e) {
        }
        final List<MarkerOptions> markerList = new ArrayList();
        try {
            MarkerOptions options1 = new MarkerOptions();
            options1.title("yo");
            options1.snippet("");
            options1.position(new LatLng(mLatLng.latitude, mLatLng.longitude));
            options1.icon(getBitmapDescriptor(R.drawable.ic_room_black_24px));

            markerList.add(options1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (MarkerOptions mO : markerList) {
            googleMapV.addMarker(mO);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        googleMapV.setMyLocationEnabled(true);
        googleMapV.moveCamera(CameraUpdateFactory.newLatLngZoom(Center, 14.5f));
        googleMapV.setOnCameraChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCameraChange(CameraPosition position) {
        float maxZoom = 17.0f;
        float minZoom = 14.0f;

        if (position.zoom > maxZoom) {
            googleMapV.animateCamera(CameraUpdateFactory.zoomTo(maxZoom));
        } else if (position.zoom < minZoom) {
            googleMapV.animateCamera(CameraUpdateFactory.zoomTo(minZoom));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private BitmapDescriptor getBitmapDescriptor(int id) {
        Drawable vectorDrawable = this.getDrawable(id);
        int h = 50;
        int w = 50;
        vectorDrawable.setBounds(0, 0, w, h);
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bm);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatLng = new LatLng(location.getLatitude(),
                location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClickTableCell(Object anyObject) {

        PlaceItem placeItem = (PlaceItem) anyObject;
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("Place_name", placeItem.getPlace_name());
        intent.putExtra("Place_description", placeItem.getPlace_Description());
        startActivity(intent);
    }

    @Override
    public void onDataEvent(String Json, String Route) {
        Log.e("Json",Json);
        try {
            progressDialogData.dismiss();
        }catch (Exception e){}

        try{
            JSONObject jO = new JSONObject(Json);
            if (jO.getBoolean("ok")==true){
                JSONArray jA = new JSONArray(jO.getString("shops"));
                List<PlaceItem> ListPlaces = new ArrayList<>();
                for(int i=0;i<jA.length();i++){
                    JSONObject jsonShop = new JSONObject(jA.get(i).toString());
                    String id = jsonShop.getString("id");
                    String name = jsonShop.getString("name");
                    String image = jsonShop.getString("image");
                    String score = jsonShop.getString("score");
                    String description = jsonShop.getString("description");
                    int price = jsonShop.getInt("price");
                    String latitud = new JSONArray(jsonShop.getString("location")).get(0).toString();
                    String longitud = new JSONArray(jsonShop.getString("location")).get(1).toString();
                    String dll = "";
                    if(price == 1){
                        dll = "$";
                    }if(price == 2){
                        dll = "$ $";
                    }if(price == 3){
                        dll = "$ $ $";
                    }if(price == 4){
                        dll = "$ $ $ $";
                    }
                    ListPlaces.add(new PlaceItem(name, description,image, dll,Float.parseFloat(score)));
                }

                PopulateList(ListPlaces);
            }else{
                Toast.makeText(getApplicationContext(),"Error al Leer datos",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}