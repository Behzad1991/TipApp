package com.heyworld.mykotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.heyworld.mykotlin.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.cos

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    //onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);


        /*Executing the calculate btn*/
        binding.calculateButton.setOnClickListener{calculateTip()};
        /*Executing the calculate btn*/

        
        //It hides the soft keyboard once the enter key pressed
        binding.costOfServiceEditTxt.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
        //It hides the soft keyboard once the enter key pressed


        /**Sending user to Theme activity*/
        binding.goToNextPage.setOnClickListener{
            val intent = Intent(this, ThemeActivity::class.java);
            startActivity(intent);
        }
        /**Sending user to Theme activity*/

    }
    //onCreate

    /*This method will calculate the tip and it runs inside onCreate() / calculateButton*/
    private fun calculateTip(){

        //Convert the tip text to double
        val stringInTextField = binding.costOfServiceEditTxt.text.toString()
        val cost = stringInTextField.toDoubleOrNull();

        //If the cost of service is null or 0, then display 0 & exit this loop early
        if(cost == null || cost == 0.0){
            displayTip(0.0);
            return;
        }

        //Calculate the tip percentage, using radio buttons
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        //Total tip, using tip % and total cost
        var tip = tipPercentage * cost;
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip);
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)

        //display the formatted tip value on the screen
        displayTip(tip);
    }
    //This method will calculate the tip and it runs inside onCreate() / calculateButton

    /*This function displays the tip amount*/
    private fun displayTip(tip: Double){
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip);
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip);

    }
    /*This function displays the tip amount*/

    /*This fun hides the soft keyboard, once an enter btn pressed.*/
    private fun handleKeyEvent(view: View, keyCode:Int): Boolean{

        if(keyCode == KeyEvent.KEYCODE_ENTER){
            //hid the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
            return true;
        }
        return false;
    }
    /*This fun hides the soft keyboard, once an enter btn pressed*/



}//Main
