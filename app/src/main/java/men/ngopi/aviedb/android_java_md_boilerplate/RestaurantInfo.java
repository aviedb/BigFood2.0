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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableMenuHelper;

public class RestaurantInfo extends AppCompatActivity {

    private RecyclerView mMenuRecycler;
    private RecyclerView.Adapter mMenuAdapter;
    private RecyclerView.LayoutManager mMenuLayout;
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
        ArrayList<Menu> filtered = new ArrayList<>();

        for (int i = 0; i < this.menus.size(); i++) {
            if (this.menus.get(i).getMenuName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filtered.add(this.menus.get(i));
            }
        }

        mMenuAdapter = new MenuAdapter(filtered);
        mMenuRecycler.setAdapter(mMenuAdapter);
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

        AndroidNetworking.get("http://192.168.43.62:3000/")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonobject = response.getJSONObject(i);

                                int restaurantID = getIntent().getIntExtra("RESTAURANT_ID", 0);
                                if(jsonobject.getInt("id") == restaurantID) {
                                    JSONArray jsonmenu = jsonobject.getJSONArray("menus");
                                    for (int j = 0; j < jsonmenu.length(); j++) {
                                        JSONObject jsonobjectmenu = jsonmenu.getJSONObject(j);

                                        Menu menu = new Menu(
                                                jsonobjectmenu.getInt("id"),
                                                jsonobjectmenu.getString("menu"),
                                                jsonobjectmenu.getString("description"),
                                                jsonobjectmenu.getString("harga"),
                                                restaurantID
                                        );

                                        menus.add(menu);
                                    }
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mMenuAdapter = new MenuAdapter(menus);
                        mMenuRecycler.setAdapter(mMenuAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tttt", "onError: " + anError);
                    }
                });

        //refetch
//        this.fetchMenus();
    }
}
