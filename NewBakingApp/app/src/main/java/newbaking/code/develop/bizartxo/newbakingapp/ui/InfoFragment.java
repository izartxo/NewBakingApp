package newbaking.code.develop.bizartxo.newbakingapp.ui;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import newbaking.code.develop.bizartxo.newbakingapp.R;

/**
 * Created by izartxo on 9/15/17.
 */

public class InfoFragment extends Fragment {




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.info_fragment, container, false);
        TextView tvsDesc = (TextView) view.findViewById(R.id.info_sdescription);
        TextView tvDesc = (TextView) view.findViewById(R.id.info_description);
        Button back = (Button) view.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (!(getArguments()==null)) {
            String sd = getArguments().getString("sdesc");
            String d = getArguments().getString("desc");
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

}

