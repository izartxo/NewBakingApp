package newbaking.code.develop.bizartxo.newbakingapp.model;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static android.provider.Telephony.Mms.Part.TEXT;
import static java.sql.Types.INTEGER;

/**
 * Created by izartxo on 9/12/17.
 */
public interface RecipeColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    String TITLE = "title";
    @DataType(DataType.Type.TEXT) String IMAGE = "image";

}