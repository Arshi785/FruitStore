package com.dell.fruitstore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {
    public static final String FRUIT_UPDATE_DIALOG="fruitUpdate";
    private OnClickListener listener;
    public String fruitPrice;
    public String fruitName;
    public String fruitQuantity;
    EditText name_edt,price_edt,quantity_edt;

    public MyDialog(String fruitName, int fruitPrice, String fruitQuant) {
    this.fruitName=fruitName;
    this.fruitQuantity=fruitQuant;
    this.fruitPrice= String.valueOf(fruitPrice);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=null;
        dialog=getUpdateClassDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    public interface  OnClickListener{
        void onClick(String text1,int text2,String text3);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    private Dialog getUpdateClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

         name_edt = view.findViewById(R.id.edtname);
         price_edt = view.findViewById(R.id.edtPrice);
         quantity_edt = view.findViewById(R.id.edtQuant);

        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);
        add.setText("Update");
        name_edt.setText(fruitName);
        price_edt.setText(fruitPrice);
        quantity_edt.setText(fruitQuantity);


        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String fruitName = name_edt.getText().toString();
            int price = Integer.parseInt(price_edt.getText().toString());
            String quantity = quantity_edt.getText().toString();
            listener.onClick(fruitName,price,quantity);
            dismiss();
        });

        return builder.create();
    }

}
