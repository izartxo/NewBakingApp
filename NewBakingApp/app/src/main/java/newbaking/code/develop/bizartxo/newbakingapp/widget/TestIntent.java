package newbaking.code.develop.bizartxo.newbakingapp.widget;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import newbaking.code.develop.bizartxo.newbakingapp.data.RecipeProvider;

/**
 * Created by izartxo on 11/13/17.
 */

public class TestIntent extends IntentService {

    String _ing;

    public TestIntent(String name) {
        super(name);
    }

    public TestIntent() {
        super("test");
    }



    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("------------------------",">>>>>>>>>>>>onHandleIntent");

        String ing = "1";

        BakingAppWidgetProvider.setRecipenum();

        if (intent.hasExtra("ing"))
            ing = intent.getStringExtra("ing");


        Log.d("ooooooooooooooooooo>>>>>>", "ING: " + ing);

        String columns = "_id = ?";
        String[] values = new String[]{ing};

        Uri weatherForLocationUri = RecipeProvider.Ingredients.INGREDIENTS;
        Cursor cursor = getContentResolver().query(weatherForLocationUri,
                null,
                columns,
                values,
                null);




      /*  Cursor cursor = null;
        if (_ing==null){
            String column = "_id = ?";
            String[] values = new String[]{"1"};
             cursor = getContentResolver().query(weatherForLocationUri,
                    null,
                    column,
                    values,
                    null);
        }


        else {
             cursor = getContentResolver().query(weatherForLocationUri,
                    null,
                    null,
                    null,
                    null);
        }*/
        BakingAppWidgetProvider.updateWidget(cursor, getApplicationContext());
    }
}
