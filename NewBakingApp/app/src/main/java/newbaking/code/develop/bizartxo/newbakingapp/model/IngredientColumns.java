package newbaking.code.develop.bizartxo.newbakingapp.model;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;



/**
 * Created by izartxo on 9/12/17.
 */

public interface IngredientColumns {

    @DataType(DataType.Type.INTEGER) @NotNull String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull String INGREDIENT = "ingredient";
    @DataType(DataType.Type.TEXT) @NotNull
    String QUANTITY = "quantity";

}
