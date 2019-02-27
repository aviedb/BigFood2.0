package men.ngopi.aviedb.android_java_md_boilerplate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserActivity extends Activity {
    private RecyclerView mUserRecycler;
    private RecyclerView.Adapter mUserAdapter;
    private RecyclerView.LayoutManager mUserLayout;
    private TextInputEditText search;

    private ArrayList<User> users = new ArrayList<>();
    private String searchQuery = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserRecycler = findViewById(R.id.user_recycler);
        mUserLayout = new LinearLayoutManager(this);
        mUserRecycler.setLayoutManager(mUserLayout);

        search = findViewById(R.id.search_user);
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

        AndroidNetworking.get("https://jsonplaceholder.typicode.com/users")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonobject = response.getJSONObject(i);
                                JSONObject company = jsonobject.getJSONObject("company");
                                User user = new User(
                                        jsonobject.getInt("id"),
                                        jsonobject.getString("name"),
                                        jsonobject.getString("email"),
                                        new Company(company.getString("name"), company.getString("catchPhrase"), company.getString("bs"))
                                );

                                Log.d("tttt", "onResponse: " + jsonobject.getString("name"));
                                users.add(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        mUserAdapter = new UserAdapter(users);
                        mUserRecycler.setAdapter(mUserAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tttt", "onError: " + anError);
                    }
                });
    }

    public void search() {
        searchQuery = search.getText().toString();
        ArrayList<User> filtered = new ArrayList<>();

        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filtered.add(this.users.get(i));
            }
        }

        mUserAdapter = new UserAdapter(filtered);
        mUserRecycler.setAdapter(mUserAdapter);
    }

}
