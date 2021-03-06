package ryey.easer.core.ui.edit;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import ryey.easer.commons.plugindef.eventplugin.EventData;
import ryey.easer.commons.plugindef.eventplugin.EventPlugin;
import ryey.easer.plugins.PluginRegistry;

public class EventPluginViewPager extends ViewPager {

    MyPagerAdapter mPagerAdapter;

    Integer initial_position = null;
    EventData initial_event_data = null;

    public EventPluginViewPager(Context context) {
        super(context);
    }

    public EventPluginViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void init(AppCompatActivity activity) {
        mPagerAdapter = new MyPagerAdapter(activity.getSupportFragmentManager(), getContext());
        setAdapter(mPagerAdapter);
    }

    void setEventData(EventData eventData) {
        initial_event_data = eventData;
        int i = PluginRegistry.getInstance().event().getPluginIndex(eventData);
        initial_position = i;
        if (getCurrentItem() == i) {
            synchronized (this) {
                PluginViewContainerFragment fragment = mPagerAdapter.getRegisteredFragment(i);
                if (fragment != null)
                    fragment.fill(initial_event_data);
            }
        } else {
            setCurrentItem(i);
        }
    }

    EventData getEventData() {
        return getEventData(getCurrentItem());
    }

    EventData getEventData(int position) {
        return (EventData) mPagerAdapter.getRegisteredFragment(position).getData();
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        SparseArray<PluginViewContainerFragment> registeredFragments = new SparseArray<>();

        private Context context;
        String[] titles;

        public MyPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
            List<EventPlugin> eventPluginList = PluginRegistry.getInstance().event().getPlugins();
            titles = new String[eventPluginList.size()];
            for (int i = 0; i < eventPluginList.size(); i++) {
                titles[i] = eventPluginList.get(i).view().desc(getResources());
            }
        }

        @Override
        public Fragment getItem(int position) {
            PluginViewContainerFragment fragment = EventPluginViewContainerFragment.createInstance(
                    PluginRegistry.getInstance().event().getPlugins().get(position).view());
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PluginViewContainerFragment fragment = (PluginViewContainerFragment) super.instantiateItem(container, position);
            synchronized (EventPluginViewPager.this) {
                if ((initial_position != null) && (position == initial_position)) {
                    fragment.fill(initial_event_data);
                }
            }
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public PluginViewContainerFragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
