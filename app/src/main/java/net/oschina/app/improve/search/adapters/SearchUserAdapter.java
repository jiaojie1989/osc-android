package net.oschina.app.improve.search.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.oschina.app.R;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by thanatos on 16/10/24.
 */

public class SearchUserAdapter extends BaseRecyclerAdapter<User> {

    public SearchUserAdapter(Context context){
        this(context, ONLY_FOOTER);
    }

    public SearchUserAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_search_person, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, User item, int position) {
        ViewHolder holder = (ViewHolder) h;
        Glide.with(mContext)
                .load(item.getPortrait())
                .asBitmap()
                .placeholder(R.mipmap.widget_dface)
                .error(R.mipmap.widget_dface)
                .into(holder.mViewPortrait);
        holder.mViewNick.setText(item.getName());
        holder.mViewPosition.setText(String.format("%s  %s  %s",
                item.getMore().getCompany(), item.getMore().getPosition(), item.getMore().getCity())
                .replaceAll("null", "")
                .trim()
        );
        holder.mViewIntegral.setText(String.format("积分 %s   |   关注 %s   |   粉丝 %s",
                item.getStatistics().getScore(), item.getStatistics().getFollow(), item.getStatistics().getFans()));
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.iv_portrait) CircleImageView mViewPortrait;
        @Bind(R.id.tv_nick) TextView mViewNick;
        @Bind(R.id.tv_position) TextView mViewPosition;
        @Bind(R.id.tv_integral) TextView mViewIntegral;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
