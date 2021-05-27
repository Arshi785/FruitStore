package com.dell.fruitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextInputLayout fruit,price;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<FruitItem> fruitItems = new ArrayList<>();
    FruitAdapter fruitAdapter;
    LinearLayout linearLayout;
    private TextInputLayout fruitQuantity;
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        fruit=findViewById(R.id.fruitName);
        linearLayout=findViewById(R.id.linear);
        fruitQuantity=findViewById(R.id.selectQuantity);
        price=findViewById(R.id.fruitPrice);
        String[] items=new String[]{"Select Quantity","500 gms","200 gms","1 Kg","2 Kg","3 Kg","5 Kg","10 kg","15kg","Others"};
            linearLayout.getBackground().setAlpha(100);

        ArrayAdapter<String> adapter=new ArrayAdapter <>(this,R.layout.dropdown_menu_popup_item,items);
        final AutoCompleteTextView editTextFilledExposedDropdown =
                findViewById(R.id.fruit_tv);
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view, int i, long l) {
                quantity=adapterView.getItemAtPosition(i).toString();
            }
        });


        dbHelper=new DbHelper(this);
        loadData();
        recyclerView=findViewById(R.id.recycler_fruit);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fruitAdapter=new FruitAdapter(this,fruitItems);
        recyclerView.setAdapter(fruitAdapter);
        //fruitAdapter.setOnItemClickListener(position -> gotoItemActivity(position));


        //showTable();

    }

    private void loadData() {
        Cursor cursor=dbHelper.getClassTable();
        fruitItems.clear();
        while (cursor.moveToNext()){
         //   int id=cursor.getInt(cursor.getColumnIndex(String.valueOf(0)));
            String fruitName=cursor.getString(cursor.getColumnIndex(DbHelper.FRUIT_NAME_KEY));
            int fruitPrice=cursor.getInt(cursor.getColumnIndex(DbHelper.FRUIT_PRICE_KEY));
            String fruitQuantity=cursor.getString(cursor.getColumnIndex(DbHelper.FRUIT_QUANTITY_KEY));

            fruitItems.add(new FruitItem(fruitName,fruitPrice,fruitQuantity));

        }
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:showUpdateDialog(item.getGroupId());
                break;
            case 1:deleteClass(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog=new MyDialog(fruitItems.get(position).getFruitName(),fruitItems.get(position).getFruitPrice(),fruitItems.get(position).getFruitQuant());
        dialog.show(getSupportFragmentManager(),MyDialog.FRUIT_UPDATE_DIALOG);
        dialog.setListener((fruitName,price,fruitQuantity)->updateClass(position,fruitName,price,fruitQuantity));
    }

    private void addFruit(String fruitName, int fruitPrice, String fruitQuantity) {

        dbHelper.addFruit(fruitName,fruitPrice,fruitQuantity);
        FruitItem fruitItem=new FruitItem(fruitName,fruitPrice,fruitQuantity);
        fruitItems.add(fruitItem);
        fruitAdapter.notifyDataSetChanged();


    }


    private void updateClass( int position,String fruitName,int price, String fruitQuantity) {
        dbHelper.updateClass(fruitName,price,fruitQuantity);
        fruitItems.get(position).setFruitName(fruitName);
        fruitItems.get(position).setFruitPrice(price);
        fruitItems.get(position).setFruitQuant(fruitQuantity);
        fruitAdapter.notifyItemChanged(position);

//        textView.setText("Class Successfully Updated ");
//        toast.setView(layout);
//        toast.show();


    }

    public void addData(View view) {
        String Fruit=fruit.getEditText().getText().toString();
        int Price= Integer.parseInt(price.getEditText().getText().toString());
        String quant= quantity;
        addFruit(Fruit,Price,quant);
    }

    private void deleteClass(int position) {
        dbHelper.deleteClass(fruitItems.get(position).getFruitName());
        fruitItems.remove(position);
        fruitAdapter.notifyItemRemoved(position);

        Toast.makeText(this, "Fruit Deleted", Toast.LENGTH_SHORT).show();


    }
}