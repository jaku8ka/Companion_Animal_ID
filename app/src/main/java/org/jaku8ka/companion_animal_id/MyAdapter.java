package org.jaku8ka.companion_animal_id;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jaku8ka.companion_animal_id.database.TaskEntry;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    final private ListItemClickListener mOnClickListener;
    private List<TaskEntry> mTaskEntries;

    private Context mContext;

    public MyAdapter(Context context, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private ImageView mIVPet;
        private ImageView mIVWarning;

        public MyViewHolder(View view) {
            super(view);

            mTextView = view.findViewById(R.id.tv_name);
            mIVPet = view.findViewById(R.id.iv_pet);
            mIVWarning = view.findViewById(R.id.iv_warning);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TaskEntry taskEntry = mTaskEntries.get(position);

        String nameOfPet = taskEntry.getNameOfPet();

        holder.mTextView.setText(nameOfPet);
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

}
