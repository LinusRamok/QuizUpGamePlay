package com.example.kamlesh.frd;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kamlesh.frd.Models.Questionlist;

/**
 * Created by RAJA on 18-01-2018.
 */

public class QuestionlistAdapter extends RecyclerView.Adapter<QuestionlistAdapter.QuestionlistViewHolder> {
    private RecyclerView mListener;
    private Context context;
    private Questionlist[] data;
    private int[] ans;
    public QuestionlistAdapter(Context context, Questionlist[] data, int[] ans){
        this.context=context;
        this.data=data;
        this.ans=ans;
    }

    @Override
    public QuestionlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.qusestion_list_item,parent,false);
        return new QuestionlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionlistViewHolder holder, int position) {
        final Questionlist questionList=data[position];
        int aee=ans[position];

        if (aee==1||aee==0||aee==2) {
            if (aee==1){
//                holder.color.setBackground( context.getResources().getDrawable(R.drawable.correct_answer_background));
                holder.color.setCardBackgroundColor(Color.parseColor("#C8E6C9"));
            }
            else if(aee==0){
                //holder.color.setBackground( context.getResources().getDrawable(R.drawable.wrong_answer_background));
                holder.color.setCardBackgroundColor(Color.parseColor("#FFCDD2"));
            }
            else{
                holder.color.setCardBackgroundColor(Color.parseColor("#FFFF8D"));
            }
            holder.col.setText(String.valueOf(aee));
            holder.quest.setText("Q:"+ questionList.getQuestion());
            switch (questionList.getAnswer()) {
                case "A": {
                    holder.answer.setText("A: "+ questionList.getA());
                    break;
                }
                case "B": {
                    holder.answer.setText("B: "+ questionList.getB());
                    break;
                }
                case "C": {
                    holder.answer.setText("C: "+ questionList.getC());
                    break;
                }
                default:
                    holder.answer.setText("D: "+ questionList.getD());
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemCount()
    {
        return data.length;
    }


    public class QuestionlistViewHolder extends RecyclerView.ViewHolder{
        TextView quest,col,answer,b,c,d;
        CardView color;
        public QuestionlistViewHolder(final View itemView) {
            super(itemView);
            quest = (TextView) itemView.findViewById(R.id.quest);
            col = (TextView) itemView.findViewById(R.id.col);
            answer = (TextView) itemView.findViewById(R.id.answer_i);
            color=itemView.findViewById(R.id.question_list);
        }
    }

}