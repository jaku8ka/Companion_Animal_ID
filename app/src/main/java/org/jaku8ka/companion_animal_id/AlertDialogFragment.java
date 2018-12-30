package org.jaku8ka.companion_animal_id;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.jaku8ka.companion_animal_id.database.AppDatabase;
import org.jaku8ka.companion_animal_id.database.TaskEntry;

public class AlertDialogFragment extends DialogFragment {

    private static TaskEntry taskEntryDel;

    public static AlertDialogFragment newInstance(TaskEntry taskEntry) {
        AlertDialogFragment frag = new AlertDialogFragment();
        taskEntryDel = taskEntry;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_delete_forever_black_24dp)
                .setTitle("Naozaj vymazat?")
                .setPositiveButton("Ano",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                delete(taskEntryDel);
                            }
                        }
                )
                .setNegativeButton("Nie",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }
                )
                .create();
    }

    private void delete(final TaskEntry taskEntry) {

        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase.getInstance(getActivity().getApplicationContext()).taskDao().deleteTask(taskEntry);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Vymazane - " + taskEntryDel.getNameOfPet(), Toast.LENGTH_SHORT).show();
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }
}
