package newbaking.code.develop.bizartxo.newbakingapp.data;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Step;

/**
 * Created by izartxo on 9/13/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{


    ArrayList<Step> stepList;
    final private OnStepClick mStepListener;

    LinearLayout lrow;

    Cursor mStepDataCursor;

    public StepAdapter(OnStepClick listener){

        mStepListener = listener;
    }

    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_item, viewGroup, false);
        StepAdapter.StepViewHolder rvh = new StepAdapter.StepViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepViewHolder recipeViewHolder, int i) {
        if (!mStepDataCursor.isClosed()){
        mStepDataCursor.moveToPosition(i);

        String stepId = String.valueOf(mStepDataCursor.getString(0));
        String stepStep = String.valueOf(mStepDataCursor.getString(1));
        String stepShort = String.valueOf(mStepDataCursor.getString(2));
        String stepDesc = String.valueOf(mStepDataCursor.getString(3));
        String stepVideo = String.valueOf(mStepDataCursor.getString(4));
        String stepIThumb = String.valueOf(mStepDataCursor.getString(5));



        Step mStep = new Step();

        mStep.set_id(Integer.valueOf(stepId));
        mStep.setRid(stepStep);
        mStep.setShortDescription(stepShort);
        mStep.setDescription(stepDesc);
        mStep.setVideoURL(stepVideo);
        mStep.setThumbnailURL(stepIThumb);

        recipeViewHolder.bind(mStep, i);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {

        if (mStepDataCursor==null)
            return 0;
        else
            return mStepDataCursor.getCount();
    }



    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView ting,tnum;
        ImageView cam;
        String url;

        StepViewHolder(View v){
            super(v);
            ting = (TextView) v.findViewById(R.id.step);
            tnum = (TextView) v.findViewById(R.id.step_number);
            cam = (ImageView) v.findViewById(R.id.camerapic);
            lrow = (LinearLayout) v.findViewById(R.id.lrow);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();

            String recipeId = mStepDataCursor.getString(0);
            mStepListener.onStepClick(pos, recipeId);
        }

        void bind(Step step, int pos){

            tnum.setText(String.valueOf(pos));
            ting.setText(step.getShortDescription());
            url = step.getVideoURL();



            if (((pos+1) % 2) != 0) {
                lrow.setBackgroundColor(Color.argb(255, 255, 153, 255));

            }else {
                lrow.setBackgroundColor(Color.argb(255, 204, 153, 255));

            }

            if (url.equals(""))
                cam.setVisibility(View.GONE);
            else
                cam.setVisibility(View.VISIBLE);

        }



    }

    public interface OnStepClick{
        void onStepClick(int position, String recipeid);
    }

    public void swapDataStep(Cursor cursor){

        mStepDataCursor = cursor;

        notifyDataSetChanged();
    }

    public Step getItem(int position){
        Step md = new Step();

        mStepDataCursor.moveToPosition(position);
        /// FILL DATA
        md.set_id(mStepDataCursor.getInt(1));
        md.setRid(mStepDataCursor.getString(0));
        md.setShortDescription(mStepDataCursor.getString(2));
        md.setDescription(mStepDataCursor.getString(3));
        md.setVideoURL(mStepDataCursor.getString(4));
        md.setThumbnailURL(mStepDataCursor.getString(5));
        return md;
    }
}

