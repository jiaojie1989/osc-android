package net.oschina.app.improve.widget.banner;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import net.oschina.app.R;
import net.oschina.app.bean.Banner;
import net.oschina.app.improve.widget.ViewNewsBanner;

import java.util.List;

/**
 * Created by haibin
 * on 2016/10/26.
 */

public class NewsHeaderView extends HeaderView {
    private TextView mTitleTextView;

    public NewsHeaderView(Context context, RequestManager loader, String api, String bannerCache) {
        super(context, loader, api,bannerCache);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_news_header_view;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        mTitleTextView.setText(mBanners.get(position).getName());
    }

    @Override
    void setBanners(List<Banner> banners) {
        super.setBanners(banners);
        if (banners.size() > 0) {
            mTitleTextView.setText(banners.get(0).getName());
        }
    }

    @Override
    protected Object instantiateItem(ViewGroup container, int position) {
        ViewNewsBanner view = new ViewNewsBanner(getContext());
        view.initData(mImageLoader, mBanners.get(position));
        container.addView(view);
        return view;
    }

    @Override
    protected void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof ViewNewsBanner) {
            container.removeView((ViewNewsBanner) object);
        }
    }
}
