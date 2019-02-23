package men.ngopi.aviedb.android_java_md_boilerplate;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableMenuHelper;

public class EditMenu extends AppCompatActivity {

    private TextInputEditText mEditName;
    private TextInputEditText mEditDesc;
    private TextInputEditText mEditPrice;
    private MaterialButton mSave;
    private MaterialButton mDelete;

    private int restaurantId;
    private int menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        mEditName = findViewById(R.id.edit_menu_name);
        mEditDesc = findViewById(R.id.edit_menu_description);
        mEditPrice = findViewById(R.id.edit_menu_price);

        restaurantId = getIntent().getIntExtra("RESTAURANT_ID", 0);
        menuId = getIntent().getIntExtra("MENU_ID", 0);

        if (getIntent().getStringExtra("MENU_NAME") != null)
            mEditName.setText(getIntent().getStringExtra("MENU_NAME"));

        if (getIntent().getStringExtra("MENU_DESCRIPTION") != null)
            mEditDesc.setText(getIntent().getStringExtra("MENU_DESCRIPTION"));

        if (getIntent().getStringExtra("MENU_PRICE") != null)
            mEditPrice.setText(getIntent().getStringExtra("MENU_PRICE"));

        mSave = findViewById(R.id.btn_save_menu1);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditName.getText().toString();
                String address = mEditDesc.getText().toString();
                String price = mEditPrice.getText().toString();

                Menu menu = new Menu(menuId, name, address, price, restaurantId);

                TableMenuHelper.getInstance().open();
                TableMenuHelper.getInstance().update(menu);
                TableMenuHelper.getInstance().close();

                finish();
            }
        });

        mDelete = findViewById(R.id.btn_delete_menu);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableMenuHelper.getInstance().open();
                TableMenuHelper.getInstance().delete(menuId);
                TableMenuHelper.getInstance().close();

                finish();
            }
        });
    }
}
