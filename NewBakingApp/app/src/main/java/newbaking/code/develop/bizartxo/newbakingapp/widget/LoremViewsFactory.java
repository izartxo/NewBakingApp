package newbaking.code.develop.bizartxo.newbakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by bizartxo on 21/10/17.
 */

public class LoremViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer",
            "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae",
            "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat",
            "placerat", "ante",
            "porttitor", "sodales",
            "pellentesque", "augue",
            "purus"};
    private Context ctxt=null;
    private int appWidgetId;

    public LoremViewsFactory(Context ctxt, Intent intent) {
        this.ctxt=ctxt;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.ingredient_item);

        row.setTextViewText(R.id.ingredient, items[i]);

        Intent intent=new Intent();
        Bundle extras=new Bundle();

        extras.putString("extra", items[i]);
        intent.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, intent);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
