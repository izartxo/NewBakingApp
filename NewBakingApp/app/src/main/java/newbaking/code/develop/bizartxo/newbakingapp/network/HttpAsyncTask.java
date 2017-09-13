package newbaking.code.develop.bizartxo.newbakingapp.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import newbaking.code.develop.bizartxo.newbakingapp.model.Recipe;

/**
 * Created by izartxo on 9/12/17.
 */

public class HttpAsyncTask extends AsyncTask<URL,Void,String> {


    List<Recipe> mApps;
    Context _context;


    public HttpAsyncTask(Context context) {
        _context = context;
        // Retrieve the package manager for later use; note we don't
        // use 'context' directly but instead the save global application
        // context returned by getContext().

    }

   /* *//**
     * This is where the bulk of our work is done.  This function is
     * called in a background thread and should generate a new set of
     * data to be published by the loader.
     *//*
    @Override public List<Recipe> loadInBackground() {

        String githubSearchResults = null;
        try{
        URL searchUrl = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");


        githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonWorker jw = new JsonWorker(githubSearchResults);


        // Done!
        return jw.getList();
    }

    *//**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     *//*
    @Override public void deliverResult(List<Recipe> apps) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (apps != null) {
                onReleaseResources(apps);
            }
        }
        List<Recipe> oldApps = mApps;
        mApps = apps;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(apps);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    *//**
     * Handles a request to start the Loader.
     *//*
    @Override protected void onStartLoading() {
        if (mApps != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mApps);
        }
        if (mApps==null)
            forceLoad();
    }

    *//**
     * Handles a request to stop the Loader.
     *//*
    @Override protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    *//**
     * Handles a request to cancel a load.
     *//*
    @Override public void onCanceled(List<Recipe> apps) {
        super.onCanceled(apps);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }

    *//**
     * Handles a request to completely reset the Loader.
     *//*
    @Override protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mApps != null) {
            onReleaseResources(mApps);
            mApps = null;
        }

    }

    *//**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     *//*
    protected void onReleaseResources(List<Recipe> apps) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }*/
    ///------------------------------------------------------------


    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String githubSearchResults = null;
        try {
            githubSearchResults = NetworksUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return githubSearchResults;
    }


    @Override
    protected void onPostExecute(String githubSearchResults) {
        Log.d("RESULT", "RESULT" + githubSearchResults);
        JsonWorker jw = new JsonWorker(_context, githubSearchResults);
        //return jw.getList();
    }
}
