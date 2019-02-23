package men.ngopi.aviedb.android_java_md_boilerplate;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableMenuHelper;

public class AddMenu extends AppCompatActivity {

    private TextInputEditText mMenuName;
    private TextInputEditText mMenuDescription;
    private TextInputEditText mMenuPrice;
    private MaterialButton mSaveMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        mMenuName = findViewById(R.id.new_menu_name);
        mMenuDescription = findViewById(R.id.new_menu_description);
        mMenuPrice = findViewById(R.id.new_menu_price);

        mSaveMenu = findViewById(R.id.btn_save_menu);
        mSaveMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mMenuName.getText().toString();
                String desc = mMenuDescription.getText().toString();
                String price = mMenuPrice.getText().toString();

                Menu menu = new Menu(name, desc, price, getIntent().getIntExtra("RESTAURANT_ID", 0));

                TableMenuHelper.getInstance().open();
                TableMenuHelper.getInstance().insert(menu);
                TableMenuHelper.getInstance().close();
                finish();
            }
        });
    }
}
