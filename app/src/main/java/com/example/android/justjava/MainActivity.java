package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 1;
    int whippedCream = 1;
    int chocolate = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //call value to check summary
        String summary = createOrderSummary( checkWhippedCream(), checkChocolate());

        // passing intent to email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_SUBJECT, "Order From: " + findEditText());
        intent.putExtra(Intent.EXTRA_TEXT, summary);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Send to email..."));
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numbers) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numbers);
    }

    /**
     * this for minus by 1
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getBaseContext(), "You cannot order less than 1 coffee", Toast.LENGTH_LONG).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /* this for plus + 1 */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "Sorry we cannot serve you more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * @param whippedCream check the box is checked or not show on screen
     * @return
     */
    public String createOrderSummary(boolean whippedCream, boolean chocolateTopping) {
        int numberOfQuantity = quantity;
        int price = calculatePrice(checkWhippedCream(), checkChocolate());
        String summary = getString(R.string.java_cus_name) + " " + findEditText();
        summary += "\n" + getString(R.string.java_add_whipped_cream) + " " + whippedCream;
        summary += "\n" + getString(R.string.java_add_chocolate) + " " + chocolateTopping;
        summary += "\n" + getString(R.string.java_quantity_string) + " " + numberOfQuantity;
        summary += "\n" + getString(R.string.java_total_price) + price;
        summary += "\n" +getString(R.string.java_thank_you);

        return summary;
    }

    /**
     * check for adding whipped cream or not
     *
     * @return whipped cream
     */
    public boolean checkWhippedCream() {
        CheckBox addingWhippedCream = (CheckBox) findViewById(R.id.box_check_view);
        boolean hasBoxCheck = addingWhippedCream.isChecked();
        return hasBoxCheck;
    }

    /**
     * check for the chocolate topping
     *
     * @return checkChocolate
     */
    public boolean checkChocolate() {
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_check);
        boolean checkChocolate = chocolate.isChecked();
        return checkChocolate;
    }

    /**
     * get the typing name of the customer
     *
     * @return of the name of the customer
     */
    public String findEditText() {
        EditText naming = (EditText) findViewById(R.id.typing_name);
        String findName = naming.getText().toString();
        return findName;
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        int finalPrice;
        if (addWhippedCream) {
            basePrice += whippedCream;
        }

        if (addChocolate) {
            basePrice += chocolate;
        }

        finalPrice = quantity * basePrice;
        return finalPrice;

    }

}