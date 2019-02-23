package men.ngopi.aviedb.android_java_md_boilerplate;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableRestaurantHelper;

public class EditRestaurant extends AppCompatActivity {

    private TextInputEditText mEditName;
    private TextInputEditText mEditAddress;
    private MaterialButton mSave;
    private MaterialButton mDelete;

    private int restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        mEditName = findViewById(R.id.edit_restaurant_name);
        mEditAddress = findViewById(R.id.edit_restaurant_address);

        restaurantId = getIntent().getIntExtra("RESTAURANT_ID", 0);

        if (getIntent().getStringExtra("RESTAURANT_NAME") != null)
            mEditName.setText(getIntent().getStringExtra("RESTAURANT_NAME"));

        if (getIntent().getStringExtra("RESTAURANT_ADDRESS") != null)
            mEditAddress.setText(getIntent().getStringExtra("RESTAURANT_ADDRESS"));

        mSave = findViewById(R.id.btn_save_restaurant1);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditName.getText().toString();
                String address = mEditAddress.getText().toString();

                Restaurant restaurant = new Restaurant(restaurantId, name, address);

                TableRestaurantHelper.getInstance().open();
                TableRestaurantHelper.getInstance().update(restaurant);
                TableRestaurantHelper.getInstance().close();


                Intent returnI = new Intent();
                returnI.putExtra("RESTAURANT_NAME", name);
                returnI.putExtra("RESTAURANT_ADDRESS", address);

                setResult(1, returnI);
                finish();
            }
        });

        mDelete = findViewById(R.id.btn_delete_restaurant);
        mDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TableRestaurantHelper.getInstance().open();
                TableRestaurantHelper.getInstance().delete(restaurantId);
                TableRestaurantHelper.getInstance().close();

                setResult(2);
                finish();
            }
        });
    }
}
