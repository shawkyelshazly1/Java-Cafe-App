package com.example.android.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * Variables
     */
    int price = 5, quantity = 1, total;
    String name, email, summary, cName;
    String[] cEmail;
    boolean whippedcreamCheck, chocolateCheck;

    /**
     * Objects From Views
     */
    RelativeLayout mainPage, orderLayout;
    EditText nameField, emailField;
    TextView quantityView, summaryView;
    Toast incrementLimit, decrementLimit, nullName, nullEmail, nullSummary;
    CheckBox whippedcreamBox, chocolateBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Increment Method - To increase the quantity as long as it is less than 100
     *
     * @param view
     */
    public void increment(View view) {
        incrementLimit = Toast.makeText(getApplicationContext(), "100 Cups is the maximum order", Toast.LENGTH_SHORT);
        if (quantity == 100) {
            incrementLimit.show();
        } else {
            quantity += 1;
        }
        displayQuantity(quantity);
    }

    /**
     * Increment Method - To increase the quantity as long as it is less than 100
     *
     * @param view
     */
    public void decrement(View view) {
        decrementLimit = Toast.makeText(getApplicationContext(), "1 Cup is the minimum order", Toast.LENGTH_SHORT);
        if (quantity == 1) {
            decrementLimit.show();
        } else {
            quantity -= 1;
        }
        displayQuantity(quantity);
    }


    /**
     * Check Toppings Methods
     */
    public boolean checkWhippedCream() {
        //Initiate Obj
        whippedcreamBox = findViewById(R.id.whippedcreamBox);
        //Getting the boolean value if Checked or not
        whippedcreamCheck = whippedcreamBox.isChecked();
        //Return the Value
        return whippedcreamCheck;
    }

    public boolean checkChocolate() {
        //Initiate Obj
        chocolateBox = findViewById(R.id.chocolateBox);
        //Getting the boolean value if Checked or not
        chocolateCheck = chocolateBox.isChecked();
        //return the Value
        return chocolateCheck;
    }

    /**
     * Submit Order Method
     */
    public void submitOrder(View view) {
        //Call Method to get the Name & Email
        get();
        //Creating Toasts
        nullName = Toast.makeText(getApplicationContext(), "Please Type Your Name!", Toast.LENGTH_SHORT);
        nullEmail = Toast.makeText(getApplicationContext(), "Please Type Your Email!", Toast.LENGTH_SHORT);

        //Calculating Total Price
        total = quantity * addToppingPrice(price);

        //Checking that Name and Email are not blank otherwise show the Toast Message
        if (name.equals("") || name.equals(" ")) {
            nullName.show();
        } else if (email.equals("") || email.equals(" ")) {
            nullEmail.show();
        } else {
            showSummary(name, total);
        }
        // Getting the name & email before resetting to use it in the Email Intent
        cName = name;
        cEmail = new String[]{email};
        reset();
    }

    /**
     * Get Summary Method
     *
     * @param name
     * @param totalPrice
     */
    public void showSummary(String name, int totalPrice) {
        summaryView = findViewById(R.id.summarySection);
        summary = "Hey, "
                + name + "\n"
                + "Order Confirmed." + "\n"
                + "Total : $"
                + totalPrice + "\n"
                + "Wish to see You again!";
        summaryView.setText(summary);
    }

    /***
     * Getting name & email Values
     */
    public void get() {
        //assigning the View
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);

        //getting and storing values into variables
        name = nameField.getText().toString();
        email = emailField.getText().toString();
    }

    public void displayQuantity(int quantitynum) {
        quantityView = findViewById(R.id.quantity);
        quantityView.setText("" + quantitynum);
    }

    /**
     * Calculating the Price Method
     */
    public int addToppingPrice(int cupPrice) {
        if (checkChocolate()) {
            cupPrice += 2;
        }
        if (checkWhippedCream()) {
            cupPrice += 1;
        }

        return cupPrice;
    }

    /**
     * Hide main View & Show Order View
     *
     * @param view
     */
    public void hideView(View view) {
        mainPage = findViewById(R.id.mainPage);
        orderLayout = findViewById(R.id.orderLayout);
        orderLayout.setVisibility(View.VISIBLE);
        mainPage.setVisibility(View.GONE);
    }

    /**
     * Hide Order View & Show Main View
     *
     * @param view
     */
    public void showView(View view) {
        //Reset all values
        resetV2();
        mainPage = findViewById(R.id.mainPage);
        orderLayout = findViewById(R.id.orderLayout);
        orderLayout.setVisibility(View.GONE);
        mainPage.setVisibility(View.VISIBLE);
    }

    /**
     * Reset Values Function
     */
    public void reset() {
        //Creating objects from the Views
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        whippedcreamBox = findViewById(R.id.whippedcreamBox);
        chocolateBox = findViewById(R.id.chocolateBox);

        //Resetting all Values
        total = 0;
        quantity = 1;
        displayQuantity(quantity);
        emailField.setText("");
        nameField.setText("");
        chocolateBox.setChecked(false);
        whippedcreamBox.setChecked(false);

    }

    /**
     * Reset Values Function + Clear the summary Section - For Back Button
     */
    public void resetV2() {
        //Creating objects from the Views
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        whippedcreamBox = findViewById(R.id.whippedcreamBox);
        chocolateBox = findViewById(R.id.chocolateBox);
        summaryView = findViewById(R.id.summarySection);
        //Resetting all Values
        total = 0;
        quantity = 1;
        displayQuantity(quantity);
        emailField.setText("");
        nameField.setText("");
        summaryView.setText("");
        chocolateBox.setChecked(false);
        whippedcreamBox.setChecked(false);

    }


    /**
     * Method to send Email - Using Intent
     */
    public void sendEmail(View view) {
        nullSummary = Toast.makeText(getApplicationContext(), "Please Place an Order First!", Toast.LENGTH_LONG);
        if (summary.equals("")) {
            nullSummary.show();
        } else {
            String[] cafeEmail = {"javacafe@gmail.com"};
            Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
            sendEmail.setData(Uri.parse("mailto:"));
            sendEmail.putExtra(Intent.EXTRA_EMAIL, cafeEmail);
            sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Cofee Order!");
            sendEmail.putExtra(Intent.EXTRA_TEXT, summary);
            sendEmail.putExtra(Intent.EXTRA_CC, cEmail);
            if (sendEmail.resolveActivity(getPackageManager()) != null) {
                startActivity(sendEmail);
            }
        }
    }

    public void getLocation(View view) {
        Intent getLocation = new Intent(Intent.ACTION_VIEW);
        getLocation.setData(Uri.parse("geo:-33.93806720630161, 18.86145999999999?z=21"));
        if (getLocation.resolveActivity(getPackageManager()) != null) {
            startActivity(getLocation);
        }
    }
}
