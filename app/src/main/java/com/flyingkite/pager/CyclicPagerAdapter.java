package com.flyingkite.pager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public abstract class CyclicPagerAdapter extends PagerAdapter {
    private WeakReference<ViewPager> pager;

    public abstract int getPageCount();

    public abstract Object instantiatePageItem(ViewGroup container, int position);
    public abstract void destroyPageItem(ViewGroup container, int position, Object object);
    /*
    public Object instantiatePageItem(ViewGroup container, int position) {
        throw new UnsupportedOperationException("Required method instantiatePageItem was not overridden");
    }

    public void destroyPageItem(ViewGroup container, int position, Object object) {
        throw new UnsupportedOperationException("Required method destroyPageItem was not overridden");
    }
    */

    @Override
    public int getCount() {
        return getPageCount() + 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (noPager()) {
            if (container instanceof ViewPager) {
                // Add cyclic paging to pager when first time init
                ViewPager p = (ViewPager) container;
                pager = new WeakReference<>(p);
                pager().addOnPageChangeListener(cycle);

                // And set to 1st adapter page
                if (pager().getCurrentItem() == 0) {
                    // Post it so that 1st time created page can scroll to left to see the last one
                    pager().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pager().setCurrentItem(1, false);
                        }
                    }, 20);
                }
            }
        }

        return instantiatePageItem(container, toPagerIndex(position));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        destroyPageItem(container, toPagerIndex(position), object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (position == 0 || position == getCount() - 1) return;

        setPrimaryPageItem(container, position - 1, object);
    }

    public void setPrimaryPageItem(ViewGroup container, int position, Object object) {
    }

    protected final int toPagerIndex(int position) {
        if (position == 0) {
            return getPageCount() - 1;
        } else if (position == getCount() - 1) {
            return 0;
        } else {
            return position - 1;
        }
    }

    private ViewPager pager() {
        return pager.get();
    }

    private boolean noPager() {
        return pager == null || pager.get() == null;
    }

    private ViewPager.OnPageChangeListener cycle = new ViewPager.OnPageChangeListener() {
        private int selected = -1;
        private final String[] STATE = {"IDLE", "DRAGGING", "SETTLING"}; // Use as STATE[state]

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selected = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (noPager()) return;

            if (state == ViewPager.SCROLL_STATE_IDLE) {
                setCurrentItemDelayed(selected);
            }
        }

        private void setCurrentItemDelayed(final int selected) {
            pager().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int n = getCount();
                    if (selected == 0) {
                        pager().setCurrentItem(n - 2, false);
                    } else if (selected == n - 1) {
                        pager().setCurrentItem(1, false);
                    }
                }
            }, 20);
        }
    };
}
