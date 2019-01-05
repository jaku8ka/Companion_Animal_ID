package org.jaku8ka.companionAnimal;

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

import org.jaku8ka.companionAnimal.database.AppDatabase;
import org.jaku8ka.companionAnimal.database.TaskEntry;

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

    private int odcSpinner;
    private int vacSpinner;

    Button mBtnDateOfBirth;
    Button mBtnDateOfOdc;
    Button mBtnDateOfVac;

    EditText etNameOfPet;
    Spinner sTypeOfPet;
    TextView tvDateOfBirth;
    Spinner sSex;
    EditText etSpecies;
    EditText etColorOfPet;
    TextView tvDateOfOdc;
    Spinner sOdc;
    TextView tvDateOfVac;
    Spinner sVac;


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
        tvDateOfBirth = findViewById(R.id.date_of_birth);

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

        mBtnDateOfBirth = findViewById(R.id.add_pet_brthd);
        mBtnDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();

                dialogFragment.show(getSupportFragmentManager(), "Date Picker Brth");
            }
        });

        mBtnDateOfOdc = findViewById(R.id.add_pet_odc);
        mBtnDateOfOdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();

                dialogFragment.show(getSupportFragmentManager(), "Date Picker Odc");
            }
        });

        tvDateOfOdc = findViewById(R.id.date_of_odc);

        sOdc = findViewById(R.id.loop_odc);
        ArrayAdapter<CharSequence> adapterOdc = ArrayAdapter.createFromResource(this, R.array.odc_loop, R.layout.spinner_item);
        adapterOdc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sOdc.setAdapter(adapterOdc);
        sOdc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                odcSpinner = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBtnDateOfVac = findViewById(R.id.add_pet_vac);
        mBtnDateOfVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerFragment();

                dialogFragment.show(getSupportFragmentManager(), "Date Picker Vac");
            }
        });

        tvDateOfVac = findViewById(R.id.date_of_vac);

        sVac = findViewById(R.id.loop_vac);
        ArrayAdapter<CharSequence> adapterVac = ArrayAdapter.createFromResource(this, R.array.vac_loop, R.layout.spinner_item);
        adapterVac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sVac.setAdapter(adapterVac);
        sVac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vacSpinner = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }
        etNameOfPet.setText(task.getNameOfPet());
        sTypeOfPet.setSelection(task.getPetType());
        tvDateOfBirth.setText(task.getDateOfBirth());
        sSex.setSelection(task.getSex());
        etSpecies.setText(task.getSpecies());
        etColorOfPet.setText(task.getColorOfPet());
        tvDateOfOdc.setText(task.getDateOfOdc());
        sOdc.setSelection(task.getNextOdc());
        tvDateOfVac.setText(task.getDateOfVac());
        sVac.setSelection(task.getNextVac());
    }

    public void onSaveButtonClicked() {

        String nameOfPet = etNameOfPet.getText().toString();
        int typeOfPet = petSpinner;
        String dateOfBirth = tvDateOfBirth.getText().toString();
        int sex = sexSpinner;
        String species = etSpecies.getText().toString();
        String colorOfPet = etColorOfPet.getText().toString();

        String dateOfOdc = tvDateOfOdc.getText().toString();
        int loopOdc = odcSpinner;
        String dateOfVac = tvDateOfVac.getText().toString();
        int loopVac = vacSpinner;

        if (nameOfPet.isEmpty()) {
            Toast toast = Toast.makeText(this, "Zadaj meno zvierata!", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            final TaskEntry task = new TaskEntry(nameOfPet, typeOfPet, dateOfBirth, sex, species,
                    colorOfPet, dateOfOdc, dateOfVac, loopOdc, loopVac);
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
        String petDate = tvDateOfBirth.getText().toString();
        int petSex = sexSpinner;
        String petSpecies = etSpecies.getText().toString();
        String petColor = etColorOfPet.getText().toString();
        String odcDate = tvDateOfOdc.getText().toString();
        int odcLoop = odcSpinner;
        String vacDate = tvDateOfVac.getText().toString();
        int vacLoop = vacSpinner;

        outState.putString("savedName", petName);
        outState.putInt("savedType", petType);
        outState.putString("savedDate", petDate);
        outState.putInt("savedSex", petSex);
        outState.putString("savedSpecies", petSpecies);
        outState.putString("savedColor", petColor);
        outState.putString("savedDateOdc", odcDate);
        outState.putInt("savedLoopOdc", odcLoop);
        outState.putString("savedDateVac", vacDate);
        outState.putInt("savedLoopVac", vacLoop);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String petName = savedInstanceState.getString("savedName");
        int petType = savedInstanceState.getInt("savedType");
        String petDate = savedInstanceState.getString("savedDate");
        int petSex = savedInstanceState.getInt("savedSex");
        String petSpecies = savedInstanceState.getString("savedSpecies");
        String petColor = savedInstanceState.getString("savedColor");
        String odcDate = savedInstanceState.getString("savedDateOdc");
        int odcLoop = savedInstanceState.getInt("savedLoopOdc");
        String vacDate = savedInstanceState.getString("savedDateVac");
        int vacLoop = savedInstanceState.getInt("savedLoopVac");

        etNameOfPet.setText(petName);
        sTypeOfPet.setSelection(petType);
        tvDateOfBirth.setText(petDate);
        sSex.setSelection(petSex);
        etSpecies.setText(petSpecies);
        etColorOfPet.setText(petColor);
        tvDateOfOdc.setText(odcDate);
        sOdc.setSelection(odcLoop);
        tvDateOfVac.setText(vacDate);
        sVac.setSelection(vacLoop);
    }
}
