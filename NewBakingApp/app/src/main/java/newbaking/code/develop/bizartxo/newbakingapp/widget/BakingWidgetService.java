package newbaking.code.develop.bizartxo.newbakingapp.widget;

import android.content.Intent;

import android.widget.RemoteViewsService;


import newbaking.code.develop.bizartxo.newbakingapp.data.IngredientAdapter;

/**
 * Created by izartxo on 10/19/17.
 */

public class BakingWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new IngredientAdapter(this.getApplicationContext(),
                intent));
    }
}
