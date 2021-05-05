package com.example.dnevnikmassatela;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private int layout;
    private Integer id, Act;
    private ArrayList<Product> productList;



    ProductAdapter(Context context, int resource, ArrayList<Product> products, Integer Id, Integer act) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.id = Id;
        this.Act = act;
        Log.d("MyLog", "Act : " + act);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Product product = productList.get(position);

        viewHolder.nameView.setText(product.getName());
        viewHolder.countView.setText(product.getDescription());

        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = product.getName();
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                String description = product.getDescription();
                //Вытаскиваю протеин
                int indP = description.indexOf("Protein: ");
                String Protein_s = description.substring(indP+"Protein: ".length(), description.length()-1).trim(); // -1 для удаления "g"
                double Protein = Double.parseDouble(Protein_s);
                //Вытаскиваю Углеводы
                int indC = description.indexOf("Carbs: ");
                int indCP = description.indexOf("g | P");
                String Carbs_s = description.substring(indC+"Carbs: ".length(), indCP).trim();
                double Carbs = Double.parseDouble(Carbs_s);
                //Вытаскиваю жиры
                int indF = description.indexOf("Fat: ");
                int indFC = description.indexOf("g | C");
                String Fat_s = description.substring(indF+"Fat: ".length(), indFC).trim();
                double Fat = Double.parseDouble(Fat_s);
                //Вытаскиваю каллории
                int indK = description.indexOf("Calories: ");
                int indKF = description.indexOf("kcal");
                String Cal_s = description.substring(indK+"Calories: ".length(), indKF).trim();
                double Cal = Double.parseDouble(Cal_s);
                //получение даты
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String dat = dateFormat.format(date);
                Integer em = id;
                if (Act == 1){
                    //вставка в таблицу завтрак
                    Boolean checkinsertdata = databaseHelper.insertBreakfast(id, name, Protein, Fat, Carbs, Cal, dat);
                }
                if (Act == 2){
                    //вставка в таблицу обед
                    Boolean checkinsertdata = databaseHelper.insertDinner(id, name, Protein, Fat, Carbs, Cal, dat);
                }
                if (Act == 3){
                    //вставка в таблицу ужин
                    Boolean checkinsertdata = databaseHelper.insertUzhin(id, name, Protein, Fat, Carbs, Cal, dat);
                }
                if (Act == 4){
                    //вставка в таблицу ужин
                    Boolean checkinsertdata = databaseHelper.insertPerekus(id, name, Protein, Fat, Carbs, Cal, dat);
                }
            }
        });
        return convertView;
    }
    private class ViewHolder {
        final Button addButton;
        final TextView nameView, countView;
        ViewHolder(View view){
            addButton = (Button) view.findViewById(R.id.addButton);
            nameView = (TextView) view.findViewById(R.id.nameView);
            countView = (TextView) view.findViewById(R.id.countView);
        }
    }
}

