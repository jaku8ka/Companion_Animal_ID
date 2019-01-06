package org.jaku8ka.companionAnimal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.jaku8ka.companionAnimal.database.AppDatabase;
import org.jaku8ka.companionAnimal.database.TaskEntry;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AppDatabase mDb;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.my_recycler_view);

        FloatingActionButton fab = findViewById(R.id.fab_btn);

        linearLayout = findViewById(R.id.ll_empty_db);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPetActivity.class);
                startActivity(intent);
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());

        retrievePets();

        NotificationScheduler.createNotificationChannel(this);
    }

    private void retrievePets() {
        LiveData<List<TaskEntry>> tasks = mDb.taskDao().loadAllTasks();

        tasks.observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntry> taskEntries) {
                mAdapter.setTasks(taskEntries);

                if (mAdapter.getItemCount() == 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else linearLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {

        Intent intent = new Intent(MainActivity.this, AddPetActivity.class);
        intent.putExtra(AddPetActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);

        MenuItem menuItem = menu.findItem(R.id.action_save);
        menuItem.setVisible(false);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(this, SlidePagerInfoActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
