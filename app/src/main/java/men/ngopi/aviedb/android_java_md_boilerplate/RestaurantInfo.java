package men.ngopi.aviedb.android_java_md_boilerplate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableMenuHelper;

public class RestaurantInfo extends AppCompatActivity {

    private RecyclerView mMenuRecycler;
    private RecyclerView.Adapter mMenuAdapter;
    private RecyclerView.LayoutManager mMenuLayout;
    private MaterialButton mAddMenu;
    private MaterialButton mEditRestaurant;
    private TextInputEditText search;
    private TextView mRestaurantname;

    private Restaurant data;
    private String searchQuery = "";
    private ArrayList<Menu> menus = new ArrayList<>();
    private int restaurantId;

    private static RestaurantInfo instance;

    private TableMenuHelper menuHelper;

    public static RestaurantInfo getInstance() {
        return instance;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        instance = this;

        mRestaurantname = findViewById(R.id.tv_restaurant_name1);

        mMenuRecycler = findViewById(R.id.menu_recycler);
        mMenuLayout = new LinearLayoutManager(this);
        mMenuRecycler.setLayoutManager(mMenuLayout);

        menuHelper = new TableMenuHelper();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRestaurantname.setText(extras.getString("RESTAURANT_NAME"));
            restaurantId = extras.getInt("RESTAURANT_ID");

            data = new Restaurant(
                    extras.getInt("RESTAURANT_ID"),
                    extras.getString("RESTAURANT_NAME"),
                    extras.getString("RESTAURANT_ADDRESS")
            );
        }

        mAddMenu = findViewById(R.id.btn_add_menu);
        mAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantInfo.this, AddMenu.class);
                i.putExtra("RESTAURANT_ID", restaurantId);
                startActivity(i);
            }
        });

        mEditRestaurant = findViewById((R.id.btn_edit_restaurant));
        mEditRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantInfo.this, EditRestaurant.class);
                i.putExtra("RESTAURANT_ID", getIntent().getIntExtra("RESTAURANT_ID", 0));
                i.putExtra("RESTAURANT_NAME", getIntent().getStringExtra("RESTAURANT_NAME"));
                i.putExtra("RESTAURANT_ADDRESS", getIntent().getStringExtra("RESTAURANT_ADDRESS"));

                startActivityForResult(i, 2);
            }
        });

        search = findViewById(R.id.search_menu);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return false;
            }
        });
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        search();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void search() {
        searchQuery = search.getText().toString();
        fetchMenus();
    }

    public void fetchMenus() {
        menuHelper.open();
        this.menus = data.fetchMenus(searchQuery);
        menuHelper.close();

        this.mMenuAdapter = new MenuAdapter(this.menus);
        mMenuRecycler.setAdapter(this.mMenuAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1) {
            mRestaurantname.setText(data.getStringExtra("RESTAURANT_NAME"));
        } else if (resultCode == 2) {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //refetch
        this.fetchMenus();
    }
}
