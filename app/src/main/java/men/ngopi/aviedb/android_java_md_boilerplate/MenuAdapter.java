package men.ngopi.aviedb.android_java_md_boilerplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private ArrayList<Menu> menus;

    public MenuAdapter() {
        this.menus = new ArrayList<>();
    }

    public MenuAdapter(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // card view
        View menuItem = inflater.inflate(R.layout.menu_card, viewGroup, false);
        MenuAdapter.MenuViewHolder viewHolder = new MenuAdapter.MenuViewHolder(menuItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i) {
        Menu menu = menus.get(i);

        menuViewHolder.mMenuNameTV.setText(menu.getMenuName());
        menuViewHolder.mMenuDescTV.setText(menu.getMenuDescription());
        menuViewHolder.mMenuPriceTV.setText(menu.getMenuPrice());
        menuViewHolder.restaurantId = menu.getRestaurantId();
        menuViewHolder.id = menu.getId();
    }

    @Override
    public int getItemCount() {
        return this.menus.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        public TextView mMenuNameTV;
        public TextView mMenuDescTV;
        public TextView mMenuPriceTV;
        public int restaurantId;
        public int id;


        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            this.context = itemView.getContext();
            this.mMenuNameTV = itemView.findViewById(R.id.tv_menu_name);
            this.mMenuDescTV = itemView.findViewById(R.id.tv_menu_desc);
            this.mMenuPriceTV = itemView.findViewById(R.id.tv_menu_price);

            // set click listener to current item card
            itemView.findViewById(R.id.menu_card).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(this.context, EditMenu.class);
            i.putExtra("MENU_ID", id);
            i.putExtra("MENU_NAME", mMenuNameTV.getText().toString());
            i.putExtra("MENU_DESCRIPTION", mMenuDescTV.getText().toString());
            i.putExtra("MENU_PRICE", mMenuPriceTV.getText().toString());
            i.putExtra("RESTAURANT_ID", restaurantId);

            this.context.startActivity(i);
        }
    }
}
