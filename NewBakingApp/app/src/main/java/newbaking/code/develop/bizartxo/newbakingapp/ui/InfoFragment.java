package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 9/15/17.
 */

public class InfoFragment extends Fragment {

    ArrayList<String> sl;
    int step=0;
    Intent intent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getActivity().getIntent();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info_fragment, container, false);
        TextView tvsDesc = (TextView) view.findViewById(R.id.info_sdescription);
        TextView tvDesc = (TextView) view.findViewById(R.id.info_description);
        sl = getArguments().getStringArrayList("videos");

        Button next = (Button) view.findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent nextIntent = new Intent(getContext(), AuxActivity.class);
                Bundle bundle = new Bundle();



                bundle.putString("video", sl.get(step+1));

                bundle.putInt("step", step+1);
                bundle.putStringArrayList("videos",sl);
                nextIntent.putExtra("data", bundle);
                nextIntent.putParcelableArrayListExtra("stepO", intent.getParcelableArrayListExtra("stepO"));




                getActivity().finish();
                startActivity(nextIntent);
            }
        });

        checkNextStep(next);

        if (!(getArguments()==null)) {
            String sd = getArguments().getString("sdesc");
            String d = getArguments().getString("desc");
            step = getArguments().getInt("step");
            tvsDesc.setText(sd);
            tvDesc.setText(d);

        }

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override

    public void onResume() {
        super.onResume();



    }

    public void checkNextStep(Button next){

          if (getString(R.string.size).equals("large")){
              next.setVisibility(View.INVISIBLE);
          }else{
              next.setVisibility(View.VISIBLE);
          }
    }

}

