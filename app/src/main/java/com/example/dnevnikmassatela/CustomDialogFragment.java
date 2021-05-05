package com.example.dnevnikmassatela;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class CustomDialogFragment extends DialogFragment {
    Zavtrak zavtrak = new Zavtrak();


/*
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Окно выбора продукта")
                .setMessage("Для закрытия окна нажмите ОК")
                .setPositiveButton("Выбрать", null)
                .setNegativeButton("Отмена", null)
                .create();
                 LayoutInflater inflater = getActivity().getLayoutInflater();
                 View view =
                 ListView productList = (ListView) findViewById(R.id.productList);
                products = zavtrak.getList();
                Log.d("MyLog", "Title : " + products);
                ProductAdapter adapter = new ProductAdapter( Zavtrak.this, R.layout.list_item, products);
    }
*/
}