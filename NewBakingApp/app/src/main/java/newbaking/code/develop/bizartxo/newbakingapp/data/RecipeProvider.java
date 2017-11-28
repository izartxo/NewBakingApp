package newbaking.code.develop.bizartxo.newbakingapp.data;

import android.graphics.Path;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

import newbaking.code.develop.bizartxo.newbakingapp.model.IngredientColumns;
import newbaking.code.develop.bizartxo.newbakingapp.model.RecipeColumns;
import newbaking.code.develop.bizartxo.newbakingapp.model.StepColumns;

/**
 * Created by izartxo on 9/12/17.
 */

@ContentProvider(authority = RecipeProvider.AUTHORITY, database = RecipeDataBase.class)
public final class  RecipeProvider {

    public static final String AUTHORITY = "newbaking.code.develop.bizartxo.newbakingapp";

    @TableEndpoint(table = RecipeDataBase.RECIPES) public static class Recipes {

        @ContentUri(
                path = "recipes",
                type = "vnd.android.cursor.dir/recipe",
                defaultSort = RecipeColumns.TITLE + " ASC")
        public static final Uri RECIPES = Uri.parse("content://" + AUTHORITY + "/recipes");

    }

    @TableEndpoint(table = RecipeDataBase.INGREDIENTS) public static class Ingredients {

        @ContentUri(
                path = "ingredients",
                type = "vnd.android.cursor.dir/ingredient")

        public static final Uri INGREDIENTS = Uri.parse("content://" + AUTHORITY + "/ingredients");



    }

    @TableEndpoint(table = RecipeDataBase.STEPS) public static class Steps {

        @ContentUri(
                path = "steps",
                type = "vnd.android.cursor.dir/step",
                defaultSort = StepColumns.SSTEP + " ASC")
        public static final Uri STEPS = Uri.parse("content://" + AUTHORITY + "/steps");

    }
}

