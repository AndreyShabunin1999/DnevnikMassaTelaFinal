package com.example.dnevnikmassatela;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.CompactRecipe;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Recipe;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Zavtrak extends AppCompatActivity {
    EditText find_food;
    Button btn_searh;
    ListView userList;
    TextView header;
    SimpleCursorAdapter userAdapter;
    Cursor userCursor;
    SQLiteDatabase db;
    private Integer id, kol, act;
    private String email;
    private DatabaseHelper databaseHelper;
    private ArrayList<Product> products = new ArrayList<Product>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zavtrak);
        //инициализация элементов
        initViews();
        act=1;
        setAct(act);

        userList = (ListView) findViewById(R.id.list);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Zavtrak.this);
                builder.setTitle("Важное сообщение!")
                        .setMessage("Вы точно хотите удалить продукт?")
                        .setPositiveButton("Да, удалить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.delete("TABLE_BREAKFAST", "_id = ?", new String[]{String.valueOf(id)});
                            }
                        })
                        .setNegativeButton("Отмена", null)
                        .show();
            }
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        //получаем email
        Bundle arguments = getIntent().getExtras();
        email = arguments.get("EMAIL").toString();
        setEmai(email);
        databaseHelper.checkWeight(email);
        id = databaseHelper.getuId();
        setId(id);
        //задать картинку на кнопку
        btn_searh.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this, R.drawable.loupe1), null, null, null);
        db = databaseHelper.getReadableDatabase();
        Cursor w = db.rawQuery("select * from TABLE_BREAKFAST",null);

        kol = w.getCount();

        final boolean Set = true;
        new Thread() {
            public void run() {
                while (Set == true) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Cursor c = db.rawQuery("select * from TABLE_BREAKFAST",null);
                                if(kol != c.getCount()){
                                    Spisochek();
                                    setKol(c.getCount());
                                }
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Spisochek();
    }

    public void Spisochek(){
        // открываем подключение
        db = databaseHelper.getReadableDatabase();
        //получение даты
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dat = dateFormat.format(date);
        Integer f = id;
        //получаем данные из бд в виде курсора
        userCursor =  db.rawQuery("Select * from TABLE_BREAKFAST where data = date('now','localtime') AND br_user = " + id, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME_PROD,DatabaseHelper.COLUMN_KKAL};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(Zavtrak.this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        header.setText("Добавленные продукты: " +  userCursor.getCount());
        header.setTextColor(Color.parseColor("#FF3700B3"));
        userList.setAdapter(userAdapter);
    }

    public void setEmai(String email) {
        this.email = email;
    }
    public void setAct(Integer act) {
        this.act = act;
    }
    public void setKol(Integer kol) {
        this.kol = kol;
    }
    public void setId(Integer Id) {
        this.id = Id;
    }
    private String getEmail() {
        return email;
    }

    public ArrayList<Product> getList() {
        return products;
    }

    public void setList(ArrayList<Product> products) {
        this.products = products;
    }

    private void initViews() {
        find_food = findViewById(R.id.find_food);
        btn_searh = findViewById(R.id.btn_search);
        header = (TextView)findViewById(R.id.header);
        userList = (ListView)findViewById(R.id.list);
    }

    public void onMyButtonClick(View view)
    {
        String key = "7b701fc864a94bc7a1abb55160aad2f6"; //Потребительский ключ
        String secret = "64bf8c905a3b4c5ea45a67fb097bd1b1"; //Потребительский секрет
        String query = find_food.getText().toString();
        RequestQueue requestQueue =  Volley.newRequestQueue(Zavtrak.this);
        Zavtrak.Listener listener = new Zavtrak.Listener();
        Request req = new Request(key, secret, listener);
        //Этот ответ содержит список продуктов питания на странице № 0 этого запроса
        req.getFoods(requestQueue, query,0);
        //Этот пищевой объект содержит подробную информацию о продукте питания
        req.getFood(requestQueue, 29304L);
    }

    class Listener implements ResponseListener {

        ArrayList<Product> products = new ArrayList<Product>();
        ListView productList = (ListView) findViewById(R.id.productList);
        ProductAdapter adapter = new ProductAdapter( Zavtrak.this, R.layout.list_item, products, id, act);

        @Override
        public void onFoodListRespone(Response<CompactFood> response) {
            System.out.println("TOTAL FOOD ITEMS: " + response.getTotalResults());

            List<CompactFood> foods = response.getResults();
            //Этот список содержит сводную информацию о продуктах питания

            System.out.println("=========FOODS============");
            for (CompactFood food : foods) {
                System.out.println(food.getName());
                System.out.println(food.getDescription());
                products.add(new Product(food.getName(), food.getDescription()));
                productList.setAdapter(adapter);
            }
        }

        @Override
        public void onRecipeListRespone(Response<CompactRecipe> response) {
            System.out.println("TOTAL RECIPES: " + response.getTotalResults());
            List<CompactRecipe> recipes = response.getResults();
            System.out.println("=========RECIPES==========");
            for (CompactRecipe recipe: recipes) {
                System.out.println(recipe.getName());
            }
        }

        @Override
        public void onFoodResponse(Food food) {
            System.out.println("FOOD NAME: " + food.getName());
        }

        @Override
        public void onRecipeResponse(Recipe recipe) {
            System.out.println("RECIPE NAME: " + recipe.getName());
        }
    }
}