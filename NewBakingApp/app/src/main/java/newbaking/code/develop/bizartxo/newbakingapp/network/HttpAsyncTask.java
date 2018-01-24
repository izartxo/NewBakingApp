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
        // Retrieve the package manager for later use; note we don't
        // use 'context' directly but instead the save global application
        // context returned by getContext().

    }


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

        JsonWorker jw = new JsonWorker(_context, githubSearchResults);

    }
}
