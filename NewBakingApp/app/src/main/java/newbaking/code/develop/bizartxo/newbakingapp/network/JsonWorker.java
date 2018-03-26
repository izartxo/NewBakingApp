package newbaking.code.develop.bizartxo.newbakingapp.network;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.model.Ingredient;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;
import newbaking.code.develop.bizartxo.newbakingapp.model.Step;

/**
 * Created by izartxo on 9/13/17.
 */

public class JsonWorker {

    private static final String TAG = "WORKER";

    String process;
    List<Recipe> recipeList;
    Context _context;

    public JsonWorker(Context context, String result){
        _context = context;
        process = result;
        recipeList = new ArrayList<Recipe>();

        startProcess();
    }

    private void startProcess(){

        ContentResolver contentResolver = _context.getContentResolver();

        try{
            JSONArray jsonArray = new JSONArray(process);
            Recipe recipe;

            for (int z=0; z < jsonArray.length(); z++) {

                JSONObject jo = jsonArray.getJSONObject(z);

                recipe = new Recipe();
                recipe.setRecipeId(jo.getString("id"));
                recipe.setDescription(jo.getString("name"));
                recipe.setTitle(jo.getString("name"));
                recipe.setPhotoIdUrl(jo.getString("image"));

                JSONArray jing = jo.getJSONArray("ingredients");

                for (int i = 0; i < jing.length(); i++){
                    JSONObject ing = jing.getJSONObject(i);
                    ContentValues ingredientsValues = new ContentValues();
                    Ingredient io = new Ingredient();
                    io.setIngredient(ing.getString("ingredient"));
                    io.setQuantity(ing.getString("quantity"));
                    io.setRid(recipe.getRecipeId());

                    ingredientsValues.put("_id", io.getRid());
                    ingredientsValues.put("ingredient", io.getIngredient());
                    ingredientsValues.put("quantity", io.getQuantity());
                    contentResolver.insert(RecipeProvider.Ingredients.INGREDIENTS, ingredientsValues);

                }

                JSONArray jste = jo.getJSONArray("steps");

                for (int j = 0; j < jste.length(); j++){
                    JSONObject ste = jste.getJSONObject(j);
                    ContentValues stepsValues = new ContentValues();
                    Step s = new Step();
                    s.setRid(recipe.getRecipeId());
                    s.set_id(ste.getInt("id"));
                    s.setDescription(ste.getString("description"));
                    s.setShortDescription(ste.getString("shortDescription"));
                    s.setVideoURL(ste.getString("videoURL"));
                    s.setThumbnailURL(ste.getString("thumbnailURL"));

                    stepsValues.put("_id", s.getRid());
                    stepsValues.put("step", String.valueOf(s.get_id()));
                    stepsValues.put("short", s.getShortDescription());
                    stepsValues.put("description", s.getDescription());
                    stepsValues.put("video", s.getVideoURL());
                    stepsValues.put("thumbvideo", s.getThumbnailURL());

                    contentResolver.insert(RecipeProvider.Steps.STEPS, stepsValues);

                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", Integer.valueOf(recipe.getRecipeId()));
                contentValues.put("title", recipe.getTitle());

                contentValues.put("image", recipe.getPhotoIdUrl());

                // Hardcoded for this special case
                /*switch (Integer.valueOf(recipe.getRecipeId())){
                    case 1:
                        contentValues.put("image","http://thehillcountrycook.com/wp-content/uploads/2012/03/nutella-pie09.jpg");
                        break;
                    case 2:
                        contentValues.put("image", "https://www.twopeasandtheirpod.com/wp-content/uploads/2011/02/King-Arthur-Brownies.jpg");
                        break;
                    case 3:
                        contentValues.put("image","http://s3.amazonaws.com/studio-me/system/photos/photos/000/386/628/large/yellow-cake-recipe.jpg");
                        break;
                    case 4:
                        contentValues.put("image","http://food.fnr.sndimg.com/content/dam/images/food/fullset/2009/5/27/0/IGSP01_25282_s4x3.jpg.rend.hgtvcom.616.462.suffix/1485531666198.jpeg");
                        break;
                }*/


                recipeList.add(recipe);

                contentResolver.insert(RecipeProvider.Recipes.RECIPES, contentValues);
            }
        }catch (JSONException je){
            Log.d("ERROR JSON","Error: " + je.getMessage());
        }

    }


}

