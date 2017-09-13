package newbaking.code.develop.bizartxo.newbakingapp.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

import newbaking.code.develop.bizartxo.newbakingapp.model.IngredientColumns;
import newbaking.code.develop.bizartxo.newbakingapp.model.RecipeColumns;
import newbaking.code.develop.bizartxo.newbakingapp.model.StepColumns;

/**
 * Created by izartxo on 9/12/17.
 */

@Database(version = RecipeDataBase.VERSION)
public final class RecipeDataBase {

    public static final int VERSION = 2;

    @Table(RecipeColumns.class) public static final String RECIPES = "recipes";
    @Table(IngredientColumns.class) public static final String INGREDIENTS = "ingredients";
    @Table(StepColumns.class) public static final String STEPS = "steps";
}
