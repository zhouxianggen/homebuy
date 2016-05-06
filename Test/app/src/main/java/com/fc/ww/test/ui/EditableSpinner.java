package com.fc.ww.test.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.fc.ww.test.R;

import java.util.ArrayList;

public class EditableSpinner extends DialogPreference {

    private String value;
    private EditText editText;
    private Spinner spinner;
    ArrayList<String> values;

    public EditableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);

        setDialogLayoutResource(R.layout.editable_spinner);

        values = new ArrayList<String>();
        values.add("item1");
        values.add("item2");
    }

    @Override
    protected void onBindDialogView(View view) {
        editText = (EditText)view.findViewById(R.id.edit_text);
        spinner = (Spinner)view.findViewById(R.id.spinner);

        SharedPreferences pref = getSharedPreferences();

        editText.setText(values.get(0));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, values);

        spinner.setAdapter(adapter);

        updateSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ( position != 0 ) {
                    value = values.get(position);
                    editText.setText(value);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        editText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSpinner();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        super.onBindDialogView(view);
    }

    void updateSpinner() {
        value = editText.getText().toString();
        int index = values.indexOf(value);
        if (index == -1)
            index = 0;
        spinner.setSelection(index);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if ( positiveResult ) {
            SharedPreferences.Editor editor = getEditor();
            editor.putString(getKey(), value);
            editor.commit();
        }
        super.onDialogClosed(positiveResult);
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }
}
