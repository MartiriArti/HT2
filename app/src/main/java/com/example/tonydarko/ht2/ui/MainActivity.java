package com.example.tonydarko.ht2.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.tonydarko.ht2.R;
import com.example.tonydarko.ht2.adapters.MyAdapter;
import com.example.tonydarko.ht2.model.Product;

import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private Random random = new Random();

    private final int NUMBER = 50;
    private final int SLEEP_TIME = 500;

    private final int COUNT_BUYS = 2;
    private final int MAXIMUM_PURCHASE = 10;
    private final int NUMBER_OF_TYPES = 5;

    private int tempProductNumber;

    private LinkedList<Integer> positions = new LinkedList<>();
    private LinkedList<Product> products = new LinkedList<>();
    private int[] numbersBuyProducts = new int[COUNT_BUYS];
    private int[] numberOfPurchased = new int[COUNT_BUYS];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 5; i++) {
            products.add(new Product("product" + i, NUMBER));
            positions.add(i);
        }

        ListView products_list = findViewById(R.id.products_list);
        myAdapter = new MyAdapter(this, products);
        products_list.setAdapter(myAdapter);

        repeatBuys();
    }

    private void repeatBuys() {
        myAdapter.notifyDataSetChanged();
        if (!products.isEmpty()) {
            new MyAsyncTask().execute();
        }
    }

    private void countNumberOfPurchase(int countBuys) {
        for (int i = 0; i < countBuys; i++) {
            if (i == COUNT_BUYS - 1) {
                tempProductNumber = countNumberOfAnotherProduct(numbersBuyProducts[0]);
            } else {
                tempProductNumber = randomTypes();
            }
            numbersBuyProducts[i] = tempProductNumber;
            numberOfPurchased[i] = purchaseAmount(MAXIMUM_PURCHASE);
        }
    }

    private int countNumberOfAnotherProduct(int anotherProduct) {
        int tempNumber = randomTypes();
        if (tempNumber == anotherProduct) {
            return countNumberOfAnotherProduct(anotherProduct);
        } else return tempNumber;
    }

    private int purchaseAmount(int maximumPurchase) {
        int tempCount = random.nextInt(maximumPurchase + 1);
        if (tempCount == 0) {
            return purchaseAmount(maximumPurchase + 1);
        } else return tempCount;
    }

    private void purchase(int countBuys) {
        for (int i = 0; i < countBuys; i++) {
            int buy = numberOfPurchased[i];
            products.get(positions.indexOf(numbersBuyProducts[i])).count -= buy;
            if (products.get(positions.indexOf(numbersBuyProducts[i])).count <= 0) {
                products.remove(positions.indexOf(numbersBuyProducts[i]));
                positions.remove(positions.indexOf(numbersBuyProducts[i]));
            }
        }
    }

    private int randomTypes() {
        int tempNumber = random.nextInt(NUMBER_OF_TYPES);
        if (positions.contains(tempNumber)) {
            return tempNumber;
        } else return randomTypes();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            if (products.size() < COUNT_BUYS) {
                countNumberOfPurchase(COUNT_BUYS - 1);
            } else countNumberOfPurchase(COUNT_BUYS);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (products.size() < COUNT_BUYS) {
                purchase(COUNT_BUYS - 1);
            } else purchase(COUNT_BUYS);

            repeatBuys();
        }
    }
}
