package men.ngopi.aviedb.android_java_md_boilerplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private ArrayList<Restaurant> restaurants;

    public RestaurantAdapter() {
        this.restaurants = new ArrayList<>();
    }

    public RestaurantAdapter(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // card view
        View restaurantItem = inflater.inflate(R.layout.restaurant_card, viewGroup, false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(restaurantItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int i) {
        Restaurant restaurant = restaurants.get(i);

        restaurantViewHolder.mRestaurantNameTV.setText(restaurant.getRestaurantName());
        restaurantViewHolder.mRestaurantAddressTV.setText(restaurant.getRestaurantAddress());
        restaurantViewHolder.mRestaurantId = restaurant.getId();
    }

    @Override
    public int getItemCount() {
        return this.restaurants.size();
    }

    public void addRestaurant(ArrayList<Restaurant> restaurants) {
        this.restaurants.addAll(restaurants);
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        public TextView mRestaurantNameTV;
        public TextView mRestaurantAddressTV;
        public int mRestaurantId;


        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            this.context = itemView.getContext();
            this.mRestaurantNameTV = itemView.findViewById(R.id.tv_restaurant_name);
            this.mRestaurantAddressTV = itemView.findViewById(R.id.tv_restaurant_address);

            // set click listener to current item card
            itemView.findViewById(R.id.restaurant_card).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(this.context, RestaurantInfo.class);

            i.putExtra("RESTAURANT_ID", mRestaurantId);
            i.putExtra("RESTAURANT_NAME", mRestaurantNameTV.getText());
            i.putExtra("RESTAURANT_ADDRESS", mRestaurantAddressTV.getText());

            this.context.startActivity(i);
        }
    }

}
