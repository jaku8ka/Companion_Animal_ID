package org.jaku8ka.companion_animal_id;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jaku8ka.companion_animal_id.database.TaskEntry;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    final private ItemClickListener mOnClickListener;
    private List<TaskEntry> mTaskEntries;

    private Context mContext;

    public MyAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskEntry taskEntry = mTaskEntries.get(position);

        String nameOfPet = taskEntry.getNameOfPet();

        holder.mTextView.setText(nameOfPet);

    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks() {
        return mTaskEntries;
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;

        public MyViewHolder(View view) {
            super(view);

            mTextView = view.findViewById(R.id.tv_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = mTaskEntries.get(getAdapterPosition()).getId();
            mOnClickListener.onItemClickListener(clickedPosition);
        }
    }
}
