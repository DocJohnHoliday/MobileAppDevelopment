package com.example.mobileappdevelopment.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdevelopment.R;
import com.example.mobileappdevelopment.entities.Terms;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Terms> mTerms;
    private final Context context;

    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemView;
        private final TextView termItemViewID;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemViewID = itemView.findViewById(R.id.textView1);
            termItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Terms current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.putExtra("termID", current.getTermId());
                    intent.putExtra("title_term", current.getTermTitle());
                    intent.putExtra("start_term", current.getStartDate());
                    intent.putExtra("end_term", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Terms current = mTerms.get(position);
            String name = current.getTermTitle();
            String termID = String.valueOf(current.getTermId());
            holder.termItemView.setText(name);
            holder.termItemViewID.setText(termID);
        } else {
            holder.termItemView.setText("No term Name");
            holder.termItemViewID.setText("No term ID");
        }
    }

    @Override
    public int getItemCount() {
        if (mTerms != null) {
            return mTerms.size();
        } else {
            return 0;
        }
    }


    public void setTerms(List<Terms> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }
}
