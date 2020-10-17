package com.mycoffeestore.coffee;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.text.NumberFormat;
import android.widget.*;
import android.net.*;

public class MainActivity extends Activity
	{

		int quantity = 1;
		@Override
		protected void onCreate ( Bundle savedInstanceState )
			{
				super.onCreate ( savedInstanceState );
				setContentView ( R.layout.main );

			}

		//Increases the number of coffee
		public void increment ( View view )
			{
				if ( quantity == 100 )
					{
						//shows a popup when user tries to go above 100 coffee
						Toast.makeText ( this, "You can not have more than 100 cups of coffee!", Toast.LENGTH_SHORT ).show ( );
						return;
					}
				quantity = quantity + 1;
				display ( quantity );
			}

		//decreases the number of coffee
		public void decrement ( View view )
			{
				if ( quantity == 1 )
					{
						//shows a popup when user tries to go below 1 cup of coffee
						Toast.makeText ( this, "You can not have less than 1 cup of coffee!", Toast.LENGTH_SHORT ).show ( );
						return;
					}
				quantity = quantity - 1;
				display ( quantity );
			}

		public void submitOrder ( View view )
			{
				//Finding the user's name
				EditText nameField = (EditText) findViewById ( R.id.name_field );
				String name = nameField.getText ( ).toString ( );

				//Figure out if the user wants whipped cream topping
				CheckBox whippedCreamCheckBox = (CheckBox) findViewById ( R.id.whipped_cream_checkbox );
				boolean hasWhippedCream = whippedCreamCheckBox.isChecked ( );

				//Figure out if the user wants chocolate topping
				CheckBox chocolateCheckBox = (CheckBox) findViewById ( R.id.chocolate_checkbox );
				boolean hasChocolate = chocolateCheckBox.isChecked ( );

				int price = calculatePrice ( hasWhippedCream, hasChocolate );
				String PriceMessage = createOrderSummary ( price, hasWhippedCream, hasChocolate, name );

				Intent intent = new Intent ( Intent.ACTION_SENDTO );
				intent.setData ( Uri.parse ( "mailto:" ) ); //only email apps should handle this
				intent.putExtra ( Intent.EXTRA_SUBJECT, "Coffee order for " + name );
				intent.putExtra ( Intent.EXTRA_TEXT, PriceMessage);
				if ( intent.resolveActivity ( getPackageManager ( ) ) != null )
					{
						startActivity ( intent );
					}
			}


		private int calculatePrice ( boolean addWhippedCream, boolean addChocolate )
			{
				int basePrice = 3;

				if ( addWhippedCream )
					{
						basePrice = basePrice + 1;
					}

				if ( addChocolate )
					{
						basePrice = basePrice + 2;
					}
				return quantity * basePrice;
			}

		private void display ( int number )
			{
				TextView quantityTextView = (TextView) findViewById ( R.id.quantity_text_view );
				quantityTextView.setText ( "" + number );
			}

		private String createOrderSummary ( int price, boolean addWhippedCream, boolean addChocolate, String name )
			{
				String priceMessage = "Name: " + name;
				priceMessage += "\nAdd whipped cream? " + addWhippedCream;
				priceMessage += "\nAdd Chocolate? " + addChocolate;
				priceMessage += "\nQuantity: " + quantity;
				priceMessage += "\nTotal: $" + price;
				priceMessage += "\nThank you!";

				return priceMessage;

			}

	}
