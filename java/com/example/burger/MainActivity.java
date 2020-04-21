package com.example.burger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private CheckBox lettuceCheckBox;
    private CheckBox cheeseCheckBox;
    private CheckBox avocadoCheckBox;
    private Button increaseButton;
    private TextView quantityTextView;
    private Button decreaseButton;
    private TextView priceTextView;
    private Button orderSummaryButton;
    private Button orderButton;
    TextView orderSummaryTextView;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding + button with ID increase_btn
        increaseButton = findViewById(R.id.increase_btn);
        //Setting action for that button
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(quantityTextView);
                displayTotalPrice(priceTextView);
            }
        });

        //Finding - button with ID decrease_btn
        decreaseButton = findViewById(R.id.decrease_btn);
        //Setting action for that button
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement(quantityTextView);
                displayTotalPrice(priceTextView);
            }
        });

        //Finding lettuce checkbox with ID lettuce_checkbox
        lettuceCheckBox = findViewById(R.id.lettuce_checkbox);
        //Setting action when clicked
        lettuceCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTotalPrice(priceTextView);
            }
        });

        //Finding cheese checkbox with ID cheese_checkbox
        cheeseCheckBox = findViewById(R.id.cheese_checkbox);
        //Setting action when clicked
        cheeseCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTotalPrice(priceTextView);
            }
        });

        //Finding avocado checkbox with ID avocado_checkbox
        avocadoCheckBox = findViewById(R.id.avocado_checkbox);
        //Setting action when clicked
        avocadoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTotalPrice(priceTextView);
            }
        });

        //Finding orderSummary button with ID order_summary_btn
        orderSummaryButton = findViewById(R.id.order_summary_btn);
        //Setting action for that button
        orderSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderSummaryTextView = findViewById(R.id.order_summary);
                orderSummaryTextView.setText(""+ orderSummary());
            }
        });

        //Finding orderButton with ID order_btn
        orderButton = findViewById(R.id.order_btn);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",getEmail(), null));;
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject)+" "+getName());
                intent.putExtra(Intent.EXTRA_TEXT,orderSummary());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * This method is called when + button is clicked and increase quantity by 1
     * @param v is the quantityTextView where the quantity will be set
     */
    public void increment(View v){
        if(quantity==100){
            Toast.makeText(MainActivity.this,getString(R.string.max_order_warning),Toast.LENGTH_SHORT).show();
            return;
        }
            ++quantity;
        displayQuantity(quantity);
    }

    /**
     * This method is called when - button is clicked and decrease quantity by 1
     * @param v is the quantityTextView where the quantity will be set
     */
    public void decrement(View v){
        if(quantity==1){
            Toast.makeText(MainActivity.this,getString(R.string.min_order_warning),Toast.LENGTH_SHORT).show();
            return;
        }
        --quantity;
        displayQuantity(quantity);
    }

    public void displayTotalPrice(View v){
        priceTextView = findViewById(R.id.price_text);
        priceTextView.setText("$"+calculatePrice());
    }

    /**
     * This method is used to create order_summary in the order summary text view
     */
    private String orderSummary(){
        String lettuce = "";
        String cheese = "";
        String avocado = "";
        if(hasLettuce())
            lettuce = getString(R.string.has_lettuce) + "\n";
        if(hasCheese())
            cheese = getString(R.string.has_cheese) + "\n";
        if(hasAvocado())
            avocado = getString(R.string.has_avocado) + "\n";

        String orderSummary = getString(R.string.name_text) + ": " + getName() + "\n" +
                getString(R.string.email_text)+": "+ getEmail()+"\n"+
                lettuce + cheese + avocado +
                getString(R.string.quantity_text)+": "+quantity+"\n"+ getString(R.string.total_text) +
                calculatePrice()+
                "\n"+getString(R.string.thank_you_text);
        return orderSummary;
    }

    /**
     * This method displays the burger quantity
     * @param quantity is the desired burger quantity of the user
     */
    private void displayQuantity(int quantity){
        quantityTextView = findViewById(R.id.quantity_text);
        quantityTextView.setText(""+quantity);
    }

    /**
     * This method calculate the total price of the order
     * @return total price
     */
    private double calculatePrice(){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double basePrice =  5;
        if(hasLettuce())
            basePrice += .4;

        if(hasCheese())
            basePrice += .6;

        if(hasAvocado())
            basePrice += .5;

        return Double.parseDouble(decimalFormat.format(quantity * basePrice));
    }

    //This method returns user email
    public String getEmail(){
        emailEditText = findViewById(R.id.email_edit_text);
        return emailEditText.getText().toString();
    }

    //This method returns whether the lettuce checkbox is checked or not
    public boolean hasLettuce(){
        lettuceCheckBox = findViewById(R.id.lettuce_checkbox);
        return lettuceCheckBox.isChecked();
    }

    //This method returns whether the cheese checkbox is checked or not
    public boolean hasCheese(){
        cheeseCheckBox = findViewById(R.id.cheese_checkbox);
        return cheeseCheckBox.isChecked();
    }

    //This method returns whether the avocado checkbox is checked or not
    public boolean hasAvocado(){
        avocadoCheckBox = findViewById(R.id.avocado_checkbox);
        return avocadoCheckBox.isChecked();
    }

    //This method returns the name that the user enters
    public String getName(){
        nameEditText = findViewById(R.id.name_edit_text);
        return nameEditText.getText().toString();
    }

}
