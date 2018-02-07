package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 12/14/17.
 */

public class AlertDialogTest extends AppCompatActivity {

    final Context context = this;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage("No Internet connection");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();

                        setResult(RESULT_OK, returnIntent);
                        finish();

                    }
                });
        alertDialog.show();

    }
}
