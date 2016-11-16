package mx.uxie.app.uxie.items;

/**
 * Created by 72937 on 11/11/2016.
 */

public class PlaceItem {
    private String place_name;
    private String place_Description;
    private String place_imagen;
    private String place_price;
    private float rating;
    public PlaceItem(String place_name, String place_Description, String place_imagen, String place_price, float rating){
        this.place_name=place_name;
        this.place_Description=place_Description;
        this.place_imagen=place_imagen;
        this.place_price=place_price;
        this.rating=rating;

    }

    public String getPlace_Description() {
        return place_Description;
    }

    public String getPlace_imagen() {
        return place_imagen;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getPlace_price() {
        return place_price;
    }

    public float getRating() {
        return rating;
    }
}
