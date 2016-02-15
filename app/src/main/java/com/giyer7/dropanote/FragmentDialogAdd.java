package com.giyer7.dropanote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.giyer7.dropanote.beans.Note;

import io.realm.Realm;

/**
 * Created by giyer7 on 2/14/16.
 */
public class FragmentDialogAdd extends DialogFragment {

    Button btn_add, btn_cancel;
    EditText et_addnote;

    public FragmentDialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_addnote = (EditText) view.findViewById(R.id.editText_dialog);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAction();
                dismiss();
            }
        });
    }

    private void addAction() {
        String enteredNote = et_addnote.getText().toString();
        // process date
        long currentTime = System.currentTimeMillis();
        Realm realm = Realm.getDefaultInstance();
        Note note = new Note(enteredNote, currentTime, 0, false);
        realm.beginTransaction();
        realm.copyToRealm(note);
        realm.commitTransaction();
        realm.close();
    }
}
