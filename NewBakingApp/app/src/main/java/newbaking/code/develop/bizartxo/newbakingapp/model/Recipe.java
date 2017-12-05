package newbaking.code.develop.bizartxo.newbakingapp.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by izartxo on 9/12/17.
 */

public class Recipe{

    private static final String TAG = "Recipe class";

    private String _id;
    private int photoId;
    private String photoIdUrl;
    private String title;
    private String description;




    public Recipe(){
        Log.d(TAG, "Recipe berria sortua...");

    }

    public Recipe(String id,int p, String t, String d){
        this._id = id;
        this.photoId = p;
        this.title = t;
        this.description = d;

        Log.d(TAG, "Recipe berria sortua... " + id + " / " + p + " / " + t + " / " + d);
    }

    public void setRecipeId(String i){
        this._id = i;
    }

    public void setPhotoId(int p){
        this.photoId = p;
    }

    public void setPhotoIdUrl(String url){
        this.photoIdUrl = url;
    }

    public void setTitle(String t){
        this.title = t;
    }

    public void setDescription(String d){
        this.description = d;
    }

    public String getRecipeId(){
        return this._id;
    }

    public int getPhotoId(){
        return this.photoId;
    }

    public String getPhotoIdUrl(){
        return this.photoIdUrl;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }


}
