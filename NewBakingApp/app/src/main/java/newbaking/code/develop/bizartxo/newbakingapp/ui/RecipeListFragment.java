package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.data.IngredientAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;
import newbaking.code.develop.bizartxo.newbakingapp.data.StepAdapter;
import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;
import newbaking.code.develop.bizartxo.newbakingapp.model.Step;

/**
 * Created by izartxo on 9/13/17.
 */

public class RecipeListFragment extends Fragment implements StepAdapter.OnStepClick, LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_BAKING_LOADER = 44;
    private static final int ID_INGREDIENT_LOADER = 55;
    private static final int ID_STEP_LOADER = 66;

    static boolean terminatedAnimation = true;

    Context _context;
    Intent intent;
    Recipe recipe;
    RecyclerView rving, rvstep;
    IngredientAdapter iadapter;
    StepAdapter sadapter;
    ArrayList<String> stepList = new ArrayList<>();
    ArrayList<Step> stepArray;
    TextView tIngredients;
    LinearLayout ingframe, stepframe;
    ImageView arrow;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getActivity().getIntent();



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.main_recipe_fragment, container, false);

        rving = (RecyclerView) view.findViewById(R.id.rving);
        rvstep = (RecyclerView) view.findViewById(R.id.rvsteps);
        tIngredients = (TextView) view.findViewById(R.id.ting);
        ingframe = (LinearLayout) view.findViewById(R.id.ingframe);
        stepframe = (LinearLayout) view.findViewById(R.id.stepframe);
        arrow = (ImageView) view.findViewById(R.id.arrow);

        rving.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvstep.setLayoutManager(new LinearLayoutManager(view.getContext()));

        iadapter = new IngredientAdapter();
        rving.setAdapter(iadapter);
        //rving.setHasFixedSize(true);
        sadapter = new StepAdapter(this);
        rvstep.setAdapter(sadapter);
        //rvstep.setHasFixedSize(true);


        // Prepare the View for the animation
        rving.setVisibility(View.VISIBLE);
        //rving.setAlpha(0.0f);


        tIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (terminatedAnimation)
                    animateList();
            }
        });

// Start the animation
       /* rving.animate()
                .setDuration(3000)
                .translationY(view.getHeight())
                .alpha(1.0f)
                .setListener(null);

        rving.animate()
                .setDuration(3000)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rving.setVisibility(View.VISIBLE);
                    }
                });
*/


        recipe = new Recipe();



        getData2();
        return view;

    }

    private void animateList(){
        if (rving.getVisibility()==View.INVISIBLE || rving.getVisibility()==View.GONE){
            //rving.setVisibility(View.INVISIBLE);
            arrow.animate()
                    .setDuration(1000)
                    .rotation(0.0f)
                    .setListener(null);
            stepframe.animate()
                    .setDuration(1000)
                    .translationYBy(rving.getHeight())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            terminatedAnimation = false;


                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            terminatedAnimation = true;
                            rving.setVisibility(View.VISIBLE);
                        }
                    });

        }else{
            arrow.animate()
                    .setDuration(1000)
                    .rotation(90.f)
                    .setListener(null);
            stepframe.animate()
                    .setDuration(1000)
                    //.translationY(-rving.getHeight())
                    .translationYBy(-rving.getHeight())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            terminatedAnimation = false;
                            rving.setVisibility(View.INVISIBLE);


                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            terminatedAnimation = true;

                        }
                    });

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LISTFRAGMEEEEEEEEEEEEEENT" ,  "PAUSE....");
        //Fix static draw doubling data, i think another approach is better.
        rvstep.setAdapter(null);
        rvstep.setLayoutManager(null);
    }


    private void getData(){

        String rid = intent.getStringExtra("RID");

        Bundle bundle = new Bundle();
        bundle.putString("RID", rid);

        getLoaderManager().restartLoader(ID_BAKING_LOADER, bundle, this);



        String[] sa = new String[]{rid};

        ContentResolver contentResolver = _context.getContentResolver();

        Cursor cursor = contentResolver.query(RecipeProvider.Recipes.RECIPES, null, "_id = ?", sa, null);

        while (cursor.moveToNext()){
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++){

                Log.d("Logging.............", "Recipe: " + cursor.getString(i));
            }
            recipe.setRecipeId(rid);
            recipe.setTitle(cursor.getString(1));
            recipe.setTitle(cursor.getString(2));
        }

        cursor.close();


        getLoaderManager().restartLoader(ID_INGREDIENT_LOADER, bundle, this);

        Cursor cursor2 = contentResolver.query(RecipeProvider.Ingredients.INGREDIENTS, null, "_id = ?", sa, null);


       /* while (cursor2.moveToNext()){
            Ingredient newingredient = new Ingredient();
            int columnCount = cursor2.getColumnCount();
            for (int i = 0; i < columnCount; i++){
                if (i==0)
                    Log.d("Logging.............", "Ingredient_num: " + cursor2.getString(i));
                else
                    Log.d("Logging.............", "Ingredient: " + cursor2.getString(i));


            }
            newingredient.setIngredient(cursor2.getString(1));
            newingredient.setQuantity(cursor2.getString(2));
            recipe.setIngredient(newingredient);
        }*/


        // Añadido para llenar las listas en el mismo paso
        //iadapter = new IngredientAdapter(recipe.getIngredientList());
        // Añadido para llenar las listas en el mismo paso
        if (iadapter != null)
            //sadapter.swapData(recipe.getStepList());
            iadapter.swapDataIngredient(cursor2);
        else
            //sadapter = new StepAdapter(recipe.getStepList(), this);
            iadapter = new IngredientAdapter();
        rving.setAdapter(iadapter);

        rving.setHasFixedSize(true);
        iadapter.notifyDataSetChanged();
        //


        cursor2.close();

        //getLoaderManager().restartLoader(ID_STEP_LOADER, null, this);

        Cursor cursor3 = contentResolver.query(RecipeProvider.Steps.STEPS, null, "_id = ?", sa, null);

      /*  while (cursor3.moveToNext()){
            Step newstep = new Step();
            int columnCount = cursor3.getColumnCount();
            for (int i = 0; i < columnCount; i++){

                Log.d("Logging.............", "Steps: " + cursor3.getString(i));

            }
           // Log.d("RiiD", "Rid: " + cursor3.getString(0));
            newstep.setRid(cursor3.getString(0));
            newstep.set_id(cursor3.getInt(1));
            newstep.setShortDescription(cursor3.getString(2));
            newstep.setDescription(cursor3.getString(3));
            newstep.setVideoURL(cursor3.getString(4));
            newstep.setThumbnailURL(cursor3.getString(5));
            Log.d("-------------step-----" , ":::::::> " + newstep.getRid() + "-" + newstep.get_id() +
                    "-" + newstep.getThumbnailURL() +
                    "-" + newstep.getVideoURL());
            recipe.setStep(newstep);
        }*/


        // Añadido para llenar las listas en el mismo paso
        if (sadapter != null)
            // sadapter.swapData(recipe.getStepList());
            sadapter.swapDataStep(cursor3);
        else
            //    sadapter = new StepAdapter(recipe.getStepList(), this);
            sadapter = new StepAdapter(this);
        rvstep.setAdapter(sadapter);

        rvstep.setHasFixedSize(true);
        sadapter.notifyDataSetChanged();
        //

        cursor3.close();


        Log.d("----------------------------", "Recipe: " + recipe.getTitle());
       // Log.d("----------------------------", "Num Ingredients: " + recipe.getIngredientCount());
       // Log.d("----------------------------", "Num Steps: " + recipe.getStepCount());
    }

    @Override
    public void onStepClick(int position, String recipeid) {
        Log.d("----------------------------", "------" + recipeid + " // " + position);
        Step step = sadapter.getItem(position);
        if (step==null)
            Log.d("xxxxxxxxxxxxxxxxxxxxxxxxx null","nulllllll");
        String url = sadapter.getItem(position).getVideoURL();
        String sdesc = sadapter.getItem(position).getShortDescription();
        String desc = sadapter.getItem(position).getDescription();
        int s = position;
        //updateVideo(s, sdesc, desc, url);
        updateVideo(s, stepArray);
    }

    public void updateVideo(int step, String sd, String d, String v) {
        Log.d("VIIIIIIIIIIIIIIIIIIIDEO", "sdesc " + sd + "desc " + d +"video " + v);

        boolean novideo = true;

        if (!v.equals(""))
            novideo = false;



        Toast.makeText(getContext(),"MAIN STEP: " + step, Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString("video", v);
        args.putString("sdesc", sd);
        args.putInt("step", step);
        Log.d("---------","novideo " + args.getString("sdesc") + "-" + sd);
        args.putString("desc", d);
        args.putStringArrayList("videos", stepList);

        if (RecipeDetailActivity.getTwoPane()) {

            if (novideo){
                InfoFragment idf = new InfoFragment();

                idf.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.video_frame, idf);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            }else {


                RecipeDetailFragment rdf = new RecipeDetailFragment();

                rdf.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.video_frame, rdf);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
            //getFragmentManager().beginTransaction().add(R.id.detailf_fragment, rdf).commit();
        }else{
            Intent intent = new Intent(_context, AuxActivity.class);
            if (novideo){
                Log.d("---------","novideo " + args.getString("sdesc"));
                intent.putExtra("data", args);

            }else{

                Log.d("TEST value", "value: " + args.getString("video"));
                intent.putExtra("data", args);

            /*RecipeDetailActivity ra = (RecipeDetailActivity) getActivity();
            ra.setContentView(R.layout.detail_recipe_fragment);

            RecipeDetailFragment rdf = new RecipeDetailFragment();

            rdf.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.video_frame, rdf);
            transaction.addToBackStack(null);

            transaction.commit();*/
            }
            startActivity(intent);
        }
    }

    public void updateVideo(int step, ArrayList<Step> stepO) {


        boolean novideo = true;



        if (!stepO.get(step).getVideoURL().equals(""))
            novideo = false;



        Toast.makeText(getContext(),"MAIN STEP: " + step, Toast.LENGTH_SHORT).show();
        Bundle args = new Bundle();
        args.putString("video", stepO.get(step).getVideoURL());
        args.putString("sdesc", stepO.get(step).getShortDescription());
        args.putInt("step", step);

        args.putString("desc", stepO.get(step).getDescription());
        args.putStringArrayList("videos", stepList);


        if (RecipeDetailActivity.getTwoPane()) {

            if (novideo){
                InfoFragment idf = new InfoFragment();

                idf.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.video_frame, idf);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            }else {


                RecipeDetailFragment rdf = new RecipeDetailFragment();

                rdf.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.video_frame, rdf);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
            //getFragmentManager().beginTransaction().add(R.id.detailf_fragment, rdf).commit();
        }else{
            Intent intent = new Intent(_context, AuxActivity.class);
            intent.putExtra("stepO",stepO);
            if (novideo){
                Log.d("---------","novideo " + args.getString("sdesc"));
                intent.putExtra("data", args);

            }else{

                Log.d("TEST value", "value: " + args.getString("video"));
                intent.putExtra("data", args);

            /*RecipeDetailActivity ra = (RecipeDetailActivity) getActivity();
            ra.setContentView(R.layout.detail_recipe_fragment);

            RecipeDetailFragment rdf = new RecipeDetailFragment();

            rdf.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.video_frame, rdf);
            transaction.addToBackStack(null);

            transaction.commit();*/
            }
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("resume","resume");



    }

    //////////////////////////// LOADER LOGIC //////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String column = "_id = ?";
        String[] values = new String[]{args.getString("RID")};


        switch (id) {



            case ID_BAKING_LOADER:



                return new CursorLoader(_context, RecipeProvider.Recipes.RECIPES, null, column, values, null);

            case ID_INGREDIENT_LOADER:




                return new CursorLoader(_context, RecipeProvider.Ingredients.INGREDIENTS, null, column, values, null);

            case ID_STEP_LOADER:


                return new CursorLoader(_context, RecipeProvider.Steps.STEPS, null, column, values, null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data==null || data.getCount()==0){
            Log.d("---------","No datos...");

        }
        else {
            Log.d("---------", "Zenbat: " + data.getCount() + "ID: " + loader.getId());
            switch (loader.getId()){
                case ID_BAKING_LOADER:
                    break;
                case ID_INGREDIENT_LOADER:
                    if (iadapter != null)
                        iadapter.swapDataIngredient(data);
                    else {
                        iadapter = new IngredientAdapter();
                        rving.setAdapter(iadapter);
                        rving.setHasFixedSize(true);
                    }
                    iadapter.notifyDataSetChanged();

                    break;
                case ID_STEP_LOADER:
                    if (sadapter != null)
                        sadapter.swapDataStep(data);
                    else {
                        sadapter = new StepAdapter(this);
                        rving.setAdapter(sadapter);
                        rving.setHasFixedSize(true);
                    }
                    sadapter.notifyDataSetChanged();
                    stepArray = new ArrayList<Step>();
                    for (int u = 0; u < sadapter.getItemCount(); u++){
                        stepList.add(sadapter.getItem(u).getVideoURL());
                        stepArray.add(sadapter.getItem(u));
                    }

                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("RESET", "LoadReset............");
    }

    ///////////////////////// LOADER LOGIC END //////////////////////////

    private void getData2(){

        String rid = intent.getStringExtra("RID");

        Bundle bundle = new Bundle();
        bundle.putString("RID", rid);
        bundle.putInt("LIST", ID_INGREDIENT_LOADER);

        getLoaderManager().restartLoader(ID_INGREDIENT_LOADER, bundle, this);

        bundle.putString("RID", rid);
        bundle.putInt("LIST", ID_STEP_LOADER);

        getLoaderManager().restartLoader(ID_STEP_LOADER, bundle, this);

        Log.d("----------------------------", "Recipe: " + recipe.getTitle());
        // Log.d("----------------------------", "Num Ingredients: " + recipe.getIngredientCount());
        // Log.d("----------------------------", "Num Steps: " + recipe.getStepCount());
    }


}

