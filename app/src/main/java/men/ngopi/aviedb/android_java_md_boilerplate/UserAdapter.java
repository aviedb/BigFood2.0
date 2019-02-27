package men.ngopi.aviedb.android_java_md_boilerplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private ArrayList<User> users;

    public UserAdapter() {
        this.users = new ArrayList<>();
    }

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // card view
        View userItem = inflater.inflate(R.layout.user_card, viewGroup, false);
        UserViewHolder viewHolder = new UserViewHolder(userItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        User user = users.get(i);

        userViewHolder.name.setText(user.getName());
        userViewHolder.email.setText(user.getEmail());
        userViewHolder.companyname.setText(user.getCompany().getName());
        userViewHolder.companycp.setText(user.getCompany().getCatchPrase());

    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        public TextView name;
        public TextView email;
        public TextView companyname;
        public TextView companycp;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            this.context = itemView.getContext();
            this.name = itemView.findViewById(R.id.tv_user_name);
            this.email = itemView.findViewById(R.id.tv_user_email);
            this.companyname = itemView.findViewById(R.id.tv_user_company_name);
            this.companycp = itemView.findViewById(R.id.tv_user_company_cp);

            // set click listener to current item card
            itemView.findViewById(R.id.restaurant_card).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(this.context, name.getText().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
