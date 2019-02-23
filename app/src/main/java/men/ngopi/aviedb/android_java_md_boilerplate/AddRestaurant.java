package men.ngopi.aviedb.android_java_md_boilerplate;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableRestaurantHelper;

public class AddRestaurant extends AppCompatActivity {

    private TextInputEditText mRestaurantName;
    private TextInputEditText mRestaurantAddress;
    private MaterialButton mSaveRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        mRestaurantName = findViewById(R.id.new_restaurant_name);
        mRestaurantAddress = findViewById(R.id.new_restaurant_address);
        mSaveRestaurant = findViewById(R.id.btn_save_restaurant);

        mSaveRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mRestaurantName.getText().toString();
                String address = mRestaurantAddress.getText().toString();

                Restaurant restaurant = new Restaurant(name, address);

                TableRestaurantHelper.getInstance().open();
                TableRestaurantHelper.getInstance().insert(restaurant);
                TableRestaurantHelper.getInstance().close();

                finish();
            }
        });
    }
}
