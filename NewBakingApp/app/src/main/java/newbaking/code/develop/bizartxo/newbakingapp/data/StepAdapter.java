package newbaking.code.develop.bizartxo.newbakingapp.data;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import newbaking.code.develop.bizartxo.newbakingapp.R;
import newbaking.code.develop.bizartxo.newbakingapp.model.Step;

/**
 * Created by izartxo on 9/13/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{

    String[] datos = new String[3];
    ArrayList<Step> stepList;
    final private OnStepClick mStepListener;

    Cursor mStepDataCursor;

    public StepAdapter(ArrayList<Step> data,OnStepClick listener){
        datos[0] = "first";
        datos[1] = "second";
        datos[2] = "third";
        stepList = data;
        mStepListener = listener;
    }

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
        //recipeViewHolder.tnum.setText(String.valueOf(stepList.get(i).get_id()+1));
        //recipeViewHolder.ting.setText(stepList.get(i).getShortDescription());



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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
       /* if (stepList==null)
            return 0;
        else
            return stepList.size();*/
        if (mStepDataCursor==null)
            return 0;
        else
            return mStepDataCursor.getCount();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView ting,tnum;

        StepViewHolder(View v){
            super(v);
            ting = (TextView) v.findViewById(R.id.step);
            tnum = (TextView) v.findViewById(R.id.step_number);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            String recipeid = stepList.get(pos).getRid();
            mStepListener.onStepClick(pos, recipeid);
        }

        void bind(Step step, int pos){

            tnum.setText(String.valueOf(pos));
            ting.setText(step.getShortDescription());


        }

    }

    public interface OnStepClick{
        public void onStepClick(int position, String recipeid);
    }

    public void swapData(ArrayList<Step> stepa){
        stepList = stepa;
    }

    public void swapDataStep(Cursor cursor/*, Recipe recipe*/){

       /* while (cursor.moveToNext()){
            Step newstep = new Step();
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++){

                Log.d("Logging.............", "Steps: " + cursor.getString(i));

            }
            // Log.d("RiiD", "Rid: " + cursor3.getString(0));
            newstep.setRid(cursor.getString(0));
            newstep.set_id(cursor.getInt(1));
            newstep.setShortDescription(cursor.getString(2));
            newstep.setDescription(cursor.getString(3));
            newstep.setVideoURL(cursor.getString(4));
            newstep.setThumbnailURL(cursor.getString(5));
            Log.d("-------------step-----" , ":::::::> " + newstep.getRid() + "-" + newstep.get_id() +
                    "-" + newstep.getThumbnailURL() +
                    "-" + newstep.getVideoURL());
            recipe.setStep(newstep);
        }

        mStepDataCursor =
        */

        mStepDataCursor = cursor;

        notifyDataSetChanged();
    }

    public Step getItem(int position){
        Step md = new Step();

        mStepDataCursor.moveToPosition(position);
        /// FILL DATA
        md.set_id(1);
        md.setShortDescription("froga");

        return md;
    }
}

