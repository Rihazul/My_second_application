package com.example.studentprofile.Models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.studentprofile.R;

public class DialogFrame extends AppCompatDialogFragment {

    private EditText editTextSurname;
    private EditText editTextName;
    private EditText editTextStudentid;
    private EditText editTextGPA;
    private ExampleDialogListner listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_frame,null);
        builder.setView(view)
                .setTitle("Add new Student")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Surname= editTextSurname.getText().toString();
                        String Name = editTextName.getText().toString();
                        String Studentid = editTextStudentid.getText().toString();
                        String  GPA = editTextGPA.getText().toString();
                        listener.applytext(Surname,Name,Studentid,GPA);
                        listener.addtodb();
                    }
                });
        editTextSurname = view.findViewById(R.id.student_surname);
        editTextName = view.findViewById(R.id.student_name);
        editTextStudentid = view.findViewById(R.id.student_id);
        editTextGPA = view.findViewById(R.id.student_gpa);

        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must Implement ExampleDialogListner");
        }
    }

    public interface ExampleDialogListner{
        void applytext(String Surname,String Name,String Studentid,String GPA);
        void addtodb();
    }
}
