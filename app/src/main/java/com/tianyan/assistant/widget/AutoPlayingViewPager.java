package com.tianyan.assistant.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianyan.assistant.R;
import com.tianyan.assistant.model.AutoPlayInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AutoPlayingViewPager extends FrameLayout {

    private final static String TAG = "AutoPlayingViewPager";
    /**
     * 轮播图图片数量
     */
    private int IMAGE_COUNT;
    /**
     * 自动轮播的时间间隔
     */
    private final static int TIME_INTERVAL = 5;
    /**
     * 切换图片过度时间
     */
    private int swapDuration = 2000;
    /**
     * 默认图片资源(本地图片资源Id)
     */
    private int[] defaultIds = new int[]{
            R.mipmap.default_img, R.mipmap.default_img2};
    /**
     * 默认图片资源(图片URL地址)
     */
    private String[] defaultUrl = new String[]{};

    private String[] defaultTitle = new String[]{
            "学驾照到哈哈智能驾校", "学驾照到哈哈智能驾校"};
    /**
     * 自定义轮播图资源
     */
    private List<AutoPlayInfo> mAutoPlayInfoList;
    /**
     * 放圆点的View的list
     */
    private List<View> dotViewsList;
    /**
     * 轮播容器
     */
    private ViewPager mViewPager;
    /**
     * 当前轮播页
     */
    private int currentItem = 0;
    /**
     * 定时器对象
     */
    private ScheduledExecutorService scheduledExecutorService;

    private Context mContext;

    private LayoutInflater mInflate;
    /**
     * ViewPageItem点击回调接口
     */
    private OnPageItemClickListener onPageItemClickListener;

    public void setOnPageItemClickListener(OnPageItemClickListener onPageItemClickListener) {
        this.onPageItemClickListener = onPageItemClickListener;
    }

    public interface OnPageItemClickListener {
        /**
         * ViewPageItem点击事件回调
         */
        void onPageItemClick(int position, String adLink);
    }

    /**
     * 消息处理器、设置当前显示页
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem);
        }
    };

    public AutoPlayingViewPager(Context context) {
        this(context, null);
    }

    public AutoPlayingViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPlayingViewPager(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initData();
    }

    /**
     * 通过本地图片资源Id获得默认的数据
     */
    private List<AutoPlayInfo> getImageIdAutoPlayInfoList(int[] imageIds) {
        List<AutoPlayInfo> autoPlayInfoList = new ArrayList<AutoPlayInfo>();
        for (int i = 0; i < imageIds.length; i++) {
            AutoPlayInfo autoPlayInfo = new AutoPlayInfo();
            autoPlayInfo.setImageId(imageIds[i]);
            autoPlayInfoList.add(autoPlayInfo);
        }
        return autoPlayInfoList;
    }

    /**
     * 通过图片URL地址获得默认的数据
     */
    private List<AutoPlayInfo> getDefaultUrlAutoPlayInfoList() {
        List<AutoPlayInfo> autoPlayInfoList = new ArrayList<AutoPlayInfo>();
        for (int i = 0; i < defaultUrl.length; i++) {
            AutoPlayInfo autoPlayInfo = new AutoPlayInfo();
            autoPlayInfo.setImageId(defaultIds[i]);
            autoPlayInfo.setImageUrl(defaultUrl[i]);
            autoPlayInfo.setTitle(defaultTitle[i]);
            autoPlayInfoList.add(autoPlayInfo);
        }
        return autoPlayInfoList;
    }

    /**
     * 初始化
     *
     * @param imageIds 需要加载的图片Id，根据传入数量动态创建容器。
     * @return
     */
    public AutoPlayingViewPager initialize(int[] imageIds) {
        if (imageIds != null && imageIds.length != 0) {
            mAutoPlayInfoList = getImageIdAutoPlayInfoList(imageIds);
        } else {//没有数据使用默认的图片资源
            mAutoPlayInfoList = getImageIdAutoPlayInfoList(defaultIds);
        }
        IMAGE_COUNT = mAutoPlayInfoList.size();
        return this;
    }

    /**
     * 初始化
     *
     * @return
     */
    public AutoPlayingViewPager initialize(List<AutoPlayInfo> autoPlayInfoList) {
        if (autoPlayInfoList != null && !autoPlayInfoList.isEmpty()) {
            mAutoPlayInfoList = autoPlayInfoList;
        } else {//没有数据使用默认的图片资源
            mAutoPlayInfoList = getDefaultUrlAutoPlayInfoList();
        }
        IMAGE_COUNT = mAutoPlayInfoList.size();
        return this;
    }

    /**
     * 设置图片之间自动切换时间
     *
     * @param duration 切换时间
     * @return
     */
    public AutoPlayingViewPager setSwapDuration(int duration) {
        this.swapDuration = duration;
        return this;
    }

    /**
     * 开始轮播图切换 轮播之前必须调initialize()及build()
     */
    public void startPlaying() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1,
                TIME_INTERVAL, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播释放资源
     */
    public void stopPlaying() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }

    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        dotViewsList = new ArrayList<View>();
    }

    /**
     * 初始化Views 及组件UI
     */
    public void build() {
        if (mAutoPlayInfoList == null || mAutoPlayInfoList.isEmpty()) {
            Log.d(TAG, "init image fail ");
            return;
        }
        mInflate = LayoutInflater.from(mContext);
        mInflate.inflate(R.layout.view_layout_slideshow, this, true);
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();// 清除布局中的子视图，下面使用代码动态添加与图片对应的圆点

        // 热点个数与图片数量相等
        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView dotView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        setViewPagerScrollSpeed();
        mViewPager.setFocusable(true);
        mViewPager.setOffscreenPageLimit(2);// 设置缓存页面，当前页面的相邻N各页面都会被缓存
        mViewPager.setAdapter(new AutoPlayingPagerAdapter());
        AutoPlayingPageChangeListener mPageChangeListener = new AutoPlayingPageChangeListener();
        mViewPager.addOnPageChangeListener(mPageChangeListener);
    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class AutoPlayingPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final AutoPlayInfo autoPlayInfo = mAutoPlayInfoList.get(position % IMAGE_COUNT);
            View view = mInflate.inflate(R.layout.item_label_auto_play_viewpager, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_item_auto_play);
            TextView labelTitle = (TextView) view.findViewById(R.id.tv_item_label_title);
            if (!TextUtils.isEmpty(autoPlayInfo.getAdLinks())) {//有链接时才添加监听
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPageItemClickListener.onPageItemClick(position % IMAGE_COUNT, autoPlayInfo.getAdLinks());
                    }
                });
            }
            if (!TextUtils.isEmpty(autoPlayInfo.getImageUrl())) {//通过URL时使用ImageLoader加载图片
                Glide.with(mContext).load(autoPlayInfo.getImageUrl()).into(imageView);
//                ImageLoader.getInstance().displayImage(autoPlayInfo.getImageUrl(), imageView);
            } else if (autoPlayInfo.getImageId() != 0) {//本地图片时直接设置
                imageView.setImageResource(autoPlayInfo.getImageId());
            }
            if (!TextUtils.isEmpty(autoPlayInfo.getTitle())) {//有标题数据才显示
                labelTitle.setText(autoPlayInfo.getTitle());
            } else {//没有标题数据不显示文本透明背景
                labelTitle.setBackgroundDrawable(null);
            }
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }


    /**
     * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
     */
    private class AutoPlayingPageChangeListener implements ViewPager.OnPageChangeListener {
        // boolean isChange = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    // isChange = false;
                    break;
                case 2:// 界面切换中
                    // isChange = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕

                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            int p = pos % IMAGE_COUNT;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == p) {
                    dotViewsList.get(p).setBackgroundResource(
                            R.mipmap.icon_cricle_check);
                } else {
                    dotViewsList.get(i).setBackgroundResource(
                            R.mipmap.icon_cricle_uncheck);
                }
            }
        }
    }

    /**
     * 执行轮播图切换任务
     */
    boolean isLR = false;

    private class SlideShowTask implements Runnable {
        @Override
        public void run() {
            synchronized (mViewPager) {
                currentItem++;
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    /**
     * 使用反射往ViewPager中设置新的Scroller对象 覆盖默认的setCurrentItem切换时间
     */
    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    class FixedSpeedScroller extends Scroller {
        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator,
                                  boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                                int duration) {
            super.startScroll(startX, startY, dx, dy, swapDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, swapDuration);
        }
    }
}
