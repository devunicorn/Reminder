package com.devunicorn.reminder.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.devunicorn.reminder.R;
import com.devunicorn.reminder.fagment.Utils;

import java.util.Calendar;


public class AddingTaskDialogFragment extends DialogFragment {

    private AddingTaskListener addingTaskListener;

    public interface AddingTaskListener {
        void onTaskAdded();

        void onTaskAddingCancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addingTaskListener = (AddingTaskListener) activity;
        } catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement AddingTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //класс, создающий диалоговое окно

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater(); //layoutinflater - класс, создающий view element из содержимого layout file

        View container = inflater.inflate(R.layout.activity_new_task, null);

        final TextInputLayout tilTitle = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTitle);
        final EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        final EditText etDate = tilDate.getEditText();

        TextInputLayout tilTime = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTime);
        final EditText etTime = tilTime.getEditText();

        tilTitle.setHint(getResources().getString(R.string.task_title));
        tilDate.setHint(getResources().getString(R.string.task_date));
        tilTime.setHint(getResources().getString(R.string.task_time));

        builder.setView(container);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etDate.length() == 0) {
                    etDate.setText(" ");
                }
                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMotnh) {
                        Calendar dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMotnh);
                        etDate.setText(Utils.getDate(dateCalendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etTime.length() == 0) {
                    etTime.setText(" ");
                }
                DialogFragment timePickerFragment = new TimePickerFragment() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar timeCalendar = Calendar.getInstance();
                        timeCalendar.set(0, 0, 0, hourOfDay, minute);
                        etTime.setText(Utils.getTime(timeCalendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addingTaskListener.onTaskAdded();
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addingTaskListener.onTaskAddingCancel();
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create(); //создание диалога
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() { //слушатель для диалогового окна
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button pozitiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                if (etTitle.length() == 0) {
                    pozitiveButton.setEnabled(false); //блокировка кнопки, для предотвращения создания пустых тасков
                    tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etTitle.addTextChangedListener(new TextWatcher() { //слушатель события изменения текста
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 0) {
                            pozitiveButton.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                        } else {
                            pozitiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        return alertDialog;
    }
}
