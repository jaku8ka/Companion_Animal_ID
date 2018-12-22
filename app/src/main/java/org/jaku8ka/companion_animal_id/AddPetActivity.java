package org.jaku8ka.companion_animal_id;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jaku8ka.companion_animal_id.database.AppDatabase;
import org.jaku8ka.companion_animal_id.database.TaskEntry;

public class AddPetActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    private int mTaskId = DEFAULT_TASK_ID;

    private AppDatabase mDb;

    private int sexSpinner;
    private int petSpinner;

    Button btnSaveUpdate;
    Button mBtnDate;

    EditText etNameOfPet;
    Spinner sTypeOfPet;
    TextView etDateOfBirth;
    Spinner sSex;
    EditText etSpecies;
    EditText etColorOfPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            btnSaveUpdate.setText(R.string.btn_pet_upd);
            if (mTaskId == DEFAULT_TASK_ID) {

                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                final LiveData<TaskEntry> task = mDb.taskDao().loadTaskById(mTaskId);

                task.observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(@Nullable TaskEntry taskEntry) {
                        task.removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }
        }
    }

    private void initViews() {

        etNameOfPet = findViewById(R.id.name_of_pet);
        etDateOfBirth = findViewById(R.id.date_of_birth);

        sSex = findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(this, R.array.sex_types, R.layout.spinner_item);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSex.setAdapter(adapterSex);
        sSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sexSpinner = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etSpecies = findViewById(R.id.species);
        etColorOfPet = findViewById(R.id.color_of_pet);

        sTypeOfPet = findViewById(R.id.type_of_pet);
        ArrayAdapter<CharSequence> adapterPet = ArrayAdapter.createFromResource(this, R.array.pet_types, R.layout.spinner_item);
        adapterPet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTypeOfPet.setAdapter(adapterPet);
        sTypeOfPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                petSpinner = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSaveUpdate = findViewById(R.id.add_pet_btn);
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

        mBtnDate = findViewById(R.id.add_pet_brthd);
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();

                dialogFragment.show(getSupportFragmentManager(), "Date Picker Brth");
            }
        });
    }

    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }
        etNameOfPet.setText(task.getNameOfPet());
        sTypeOfPet.setSelection(task.getPetType());
        etDateOfBirth.setText(task.getDateOfBirth());
        sSex.setSelection(task.getSex());
        etSpecies.setText(task.getSpecies());
        etColorOfPet.setText(task.getColorOfPet());
    }

    public void onSaveButtonClicked() {

        String nameOfPet = etNameOfPet.getText().toString();
        int typeOfPet = petSpinner;
        String dateOfBirth = etDateOfBirth.getText().toString();
        int sex = sexSpinner;
        String species = etSpecies.getText().toString();
        String colorOfPet = etColorOfPet.getText().toString();

        if (nameOfPet.isEmpty()) {
            Toast toast = Toast.makeText(this, "Zadaj meno zvierata!", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            final TaskEntry task = new TaskEntry(nameOfPet, typeOfPet, dateOfBirth, sex, species,
                    colorOfPet);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mTaskId == DEFAULT_TASK_ID) {
                        mDb.taskDao().insertTask(task);
                    } else {
                        task.setId(mTaskId);
                        mDb.taskDao().updateTask(task);
                    }
                    finish();
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MenuItem menuItem = menu.findItem(R.id.action_setting);
        menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                onSaveButtonClicked();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String petName = etNameOfPet.getText().toString();
        int petType = petSpinner;
        String petDate = etDateOfBirth.getText().toString();
        int petSex = sexSpinner;
        String petSpecies = etSpecies.getText().toString();
        String petColor = etColorOfPet.getText().toString();

        outState.putString("savedName", petName);
        outState.putInt("savedType", petType);
        outState.putString("savedDate", petDate);
        outState.putInt("savedSex", petSex);
        outState.putString("savedSpecies", petSpecies);
        outState.putString("savedColor", petColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String petName = savedInstanceState.getString("savedName");
        int petType = savedInstanceState.getInt("savedType");
        String petDate = savedInstanceState.getString("savedDate");
        int petSex = savedInstanceState.getInt("savedSex");
        String petSpecies = savedInstanceState.getString("savedSpecies");
        String petColor = savedInstanceState.getString("savedColor");

        etNameOfPet.setText(petName);
        sTypeOfPet.setSelection(petType);
        etDateOfBirth.setText(petDate);
        sSex.setSelection(petSex);
        etSpecies.setText(petSpecies);
        etColorOfPet.setText(petColor);
    }
}
