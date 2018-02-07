package newbaking.code.develop.bizartxo.newbakingapp.model;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

import static android.provider.Telephony.Mms.Part.TEXT;
import static java.sql.Types.INTEGER;

/**
 * Created by izartxo on 9/12/17.
 */

public interface StepColumns {

    @DataType(DataType.Type.INTEGER) @NotNull String _ID = "_id";
    @DataType(DataType.Type.INTEGER) String SSTEP = "step";
    @DataType(DataType.Type.TEXT) @NotNull String SDESC = "short";
    @DataType(DataType.Type.TEXT) @NotNull String DESC = "description";
    @DataType(DataType.Type.TEXT) @NotNull String VIDEO = "video";
    @DataType(DataType.Type.TEXT) @NotNull String TVIDEO = "thumbvideo";

}

