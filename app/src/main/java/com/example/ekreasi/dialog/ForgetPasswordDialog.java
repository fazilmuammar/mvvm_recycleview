package com.example.ekreasi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.ekreasi.R;

public class ForgetPasswordDialog {

    public void showCartDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_forgetpassword,null);
        builder.setView(view);

        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
