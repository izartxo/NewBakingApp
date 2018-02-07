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


    Context _context;


    public HttpAsyncTask(Context context) {
        _context = context;

    }


    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String recipeResults = null;
        try {
            recipeResults = NetworksUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipeResults;
    }


    @Override
    protected void onPostExecute(String githubSearchResults) {

        JsonWorker jw = new JsonWorker(_context, githubSearchResults);

    }
}
