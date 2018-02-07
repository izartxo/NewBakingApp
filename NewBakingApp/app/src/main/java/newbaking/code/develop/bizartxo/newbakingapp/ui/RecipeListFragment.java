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


    private static final int ID_INGREDIENT_LOADER = 55;
    private static final int ID_STEP_LOADER = 66;


    Context _context;
    Intent intent;
    Recipe recipe;
    RecyclerView rvIng, rvStep;
    IngredientAdapter ingredientAdapter;
    StepAdapter stepAdapter;
    ArrayList<String> stepList = new ArrayList<>();
    ArrayList<Step> stepArray;
    TextView textIngredients;
    LinearLayout ingFrame, stepFrame;


    int stepPos = 0;
    int ingPos = 0;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        if ( getArguments()!=null ) {
             stepPos = getArguments().getInt("stepPos");
             ingPos = getArguments().getInt("ingPos");
        }

        View view = inflater.inflate(R.layout.main_recipe_fragment, container, false);

        rvIng = (RecyclerView) view.findViewById(R.id.rving);
        rvStep = (RecyclerView) view.findViewById(R.id.rvsteps);
        textIngredients = (TextView) view.findViewById(R.id.ting);
        ingFrame = (LinearLayout) view.findViewById(R.id.ingframe);
        stepFrame = (LinearLayout) view.findViewById(R.id.stepframe);


        rvIng.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvStep.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ingredientAdapter = new IngredientAdapter();
        rvIng.setAdapter(ingredientAdapter);

        stepAdapter = new StepAdapter(this);
        rvStep.setAdapter(stepAdapter);




        recipe = new Recipe();

        getRecipeData();

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();

        RecipeDetailActivity.setRvPositions(((LinearLayoutManager) rvIng.getLayoutManager()).findFirstCompletelyVisibleItemPosition(),((LinearLayoutManager) rvStep.getLayoutManager()).findFirstCompletelyVisibleItemPosition());

        rvIng.setAdapter(null);
        rvIng.setLayoutManager(null);
        rvStep.setAdapter(null);
        rvStep.setLayoutManager(null);
    }

    @Override
    public void onStepClick(int position) {

        updateVideo(position, stepArray);

    }

    public void updateVideo(int step, ArrayList<Step> listStep) {

        boolean noVideo = true;

        if (!listStep.get(step).getVideoURL().equals(""))
            noVideo = false;

        Bundle args = new Bundle();
        args.putString("video", listStep.get(step).getVideoURL());
        args.putString("sdesc", listStep.get(step).getShortDescription());
        args.putInt("step", step);

        args.putString("desc", listStep.get(step).getDescription());
        args.putStringArrayList("videos", stepList);


        if (RecipeDetailActivity.getTwoPane()) {

            if (noVideo){
                InfoFragment idf = new InfoFragment();

                idf.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.video_frame, idf);
                transaction.addToBackStack(null);

                transaction.commit();

            }else {


                RecipeDetailFragment rdf = new RecipeDetailFragment();

                rdf.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.video_frame, rdf);
                transaction.addToBackStack(null);

                transaction.commit();
            }

        }else{

            Intent intent = new Intent(_context, AuxActivity.class);
            intent.putExtra("stepO", listStep);


            intent.putExtra("data", args);


            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    //////////////////////////// LOADER LOGIC //////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String column = "_id = ?";
        String[] values = new String[]{args.getString("RID")};

        switch (id) {

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
            return;

        }
        else {
            switch (loader.getId()){

                case ID_INGREDIENT_LOADER:
                    if (ingredientAdapter != null)
                        ingredientAdapter.swapDataIngredient(data);
                    else {
                        ingredientAdapter = new IngredientAdapter();
                        rvIng.setAdapter(ingredientAdapter);
                        rvIng.setHasFixedSize(true);
                    }
                    ingredientAdapter.notifyDataSetChanged();

                    rvIng.getLayoutManager().scrollToPosition(ingPos);

                    break;

                case ID_STEP_LOADER:
                    if (stepAdapter != null)
                        stepAdapter.swapDataStep(data);
                    else {
                        stepAdapter = new StepAdapter(this);
                        rvIng.setAdapter(stepAdapter);
                        rvIng.setHasFixedSize(true);
                    }
                    stepAdapter.notifyDataSetChanged();
                    stepArray = new ArrayList<Step>();
                    for (int u = 0; u < stepAdapter.getItemCount(); u++){
                        stepList.add(stepAdapter.getItem(u).getVideoURL());
                        stepArray.add(stepAdapter.getItem(u));
                    }


                    rvStep.getLayoutManager().scrollToPosition(stepPos);


                    break;
            }



        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("RESET", "LoadReset............");
    }

    ///////////////////////// LOADER LOGIC END //////////////////////////

    private void getRecipeData(){

        String rid = intent.getStringExtra("RID");

        Bundle bundle = new Bundle();
        bundle.putString("RID", rid);
        bundle.putInt("LIST", ID_INGREDIENT_LOADER);

        getLoaderManager().restartLoader(ID_INGREDIENT_LOADER, bundle, this);

        bundle.putString("RID", rid);
        bundle.putInt("LIST", ID_STEP_LOADER);

        getLoaderManager().restartLoader(ID_STEP_LOADER, bundle, this);


    }


}

