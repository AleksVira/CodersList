package ru.virarnd.coderslist.models.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import ru.virarnd.coderslist.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<User> oldUserList;
    private ArrayList<User> filteredUserList;
    private LoadMoreListener loadMoreListener;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar())
                .apply(bitmapTransform(new RoundedCornersTransformation(36, 0, RoundedCornersTransformation.CornerType.OTHER_BOTTOM_LEFT)))
                .placeholder(R.drawable.avatar_error)
                .error(R.drawable.avatar_error)
                .into(holder.imageView);

        if ((position == getItemCount() - 1) && (loadMoreListener != null)) {
            loadMoreListener.loadMoreUsers(user.getUserId());
        }

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public void addUsers(List<User> users) {
        int lastPosition = userList.size();
        userList.addAll(users);
        notifyItemRangeInserted(lastPosition, users.size());
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setFilteredUsers(List<User> filteredList) {
        userList.clear();
        userList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public void setNoFilter(List<User> oldUserList) {
        userList.clear();
        userList.addAll(oldUserList);
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_user_name)
        TextView tvName;
        @BindView(R.id.iv_user_avatar)
        AppCompatImageView imageView;


        UserViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface LoadMoreListener {
        public void loadMoreUsers(long userId);
    }
}
