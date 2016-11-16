package mx.uxie.app.uxie.items;

/**
 * Created by 72937 on 11/11/2016.
 */

public class ComentItem {
    private String user_coment;
    private String user_image;
    private String user_Date;
    public ComentItem(String user_coment, String user_Date, String user_image){
        this.user_coment=user_coment;
        this.user_Date=user_Date;
        this.user_image=user_image;

    }

    public String getUser_coment() {
        return user_coment;
    }

    public String getUser_Date() {
        return user_Date;
    }

    public String getUser_image() {
        return user_image;
    }
}
