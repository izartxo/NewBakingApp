package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by izartxo on 12/14/17.
 */

public class AlertDialogTest extends AppCompatActivity {


    // this context will use when we work with Alert Dialog
    final Context context = this;

    // just for test, when we click this button, we will see the alert dialog.
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.alert);


        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("BakingApp");
        alertDialog.setMessage("No Internet connection");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("TYPE","POST Paid");
                        setResult(RESULT_OK,returnIntent);
                        finish();

                    }
                });
        alertDialog.show();






    }
}
