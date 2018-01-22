package com.example.tonydarko.ht2.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tonydarko.ht2.R;
import com.example.tonydarko.ht2.adapters.MyAdapter;
import com.example.tonydarko.ht2.model.Product;
import com.example.tonydarko.ht2.utils.Constants;

import java.util.LinkedList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    Unbinder unbinder;
    private MyAdapter myAdapter;
    private Random random = new Random();
    private int tempProductNumber;

    @BindView(R.id.products_list)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    private LinkedList<Integer> positions = new LinkedList<>();
    private LinkedList<Product> products = new LinkedList<>();
    private int[] numbersBuyProducts = new int[Constants.COUNT_BUYS];
    private int[] numberOfPurchased = new int[Constants.COUNT_BUYS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       unbinder = ButterKnife.bind(this);
        for (int i = 0; i < 5; i++) {
            products.add(new Product("product" + i, Constants.NUMBER));
            positions.add(i);
        }
        layoutManager = new LinearLayoutManager(this);

        myAdapter = new MyAdapter(products, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

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
            if (i == Constants.COUNT_BUYS - 1) {
                tempProductNumber = countNumberOfAnotherProduct(numbersBuyProducts[0]);
            } else {
                tempProductNumber = randomTypes();
            }
            numbersBuyProducts[i] = tempProductNumber;
            numberOfPurchased[i] = purchaseAmount(Constants.MAXIMUM_PURCHASE);
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
        int tempNumber = random.nextInt(Constants.NUMBER_OF_TYPES);
        if (positions.contains(tempNumber)) {
            return tempNumber;
        } else return randomTypes();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            if (products.size() < Constants.COUNT_BUYS) {
                countNumberOfPurchase(Constants.COUNT_BUYS - 1);
            } else countNumberOfPurchase(Constants.COUNT_BUYS);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(Constants.SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (products.size() < Constants.COUNT_BUYS) {
                purchase(Constants.COUNT_BUYS - 1);
            } else purchase(Constants.COUNT_BUYS);

            repeatBuys();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
