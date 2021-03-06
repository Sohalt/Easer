package ryey.easer.core.ui.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import ryey.easer.R;
import ryey.easer.commons.plugindef.PluginViewFragment;
import ryey.easer.commons.plugindef.StorageData;

public class ProfilePluginViewContainerFragment extends PluginViewContainerFragment {

    static ProfilePluginViewContainerFragment createInstance(PluginViewFragment pluginViewFragment) {
        ProfilePluginViewContainerFragment fragment = new ProfilePluginViewContainerFragment();
        fragment.pluginViewFragment = pluginViewFragment;
        return fragment;
    }

    private CheckBox mCheckBox;

    public ProfilePluginViewContainerFragment() {
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pluginview_profile, container, false);
        getChildFragmentManager().beginTransaction()
                .add(R.id.content_pluginview, pluginViewFragment)
                .commit();
        getChildFragmentManager().executePendingTransactions();
        mCheckBox = (CheckBox) view.findViewById(R.id.checkbox_pluginview_enabled);
        pluginViewFragment.setEnabled(false);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                pluginViewFragment.setEnabled(b);
            }
        });
        String desc = pluginViewFragment.desc(getResources());
        if (desc == null)
            Logger.wtf("desc == null!!!??? on <%s>", pluginViewFragment);
        ((TextView) view.findViewById(R.id.text_pluginview_desc)).setText(desc);

        return view;
    }

    @Override
    protected void _fill(StorageData data) {
        if (data == null) {
            mCheckBox.setChecked(false);
        } else {
            mCheckBox.setChecked(true);
            super._fill(data);
        }
    }

    @Override
    public StorageData getData() {
        if (mCheckBox.isChecked()) {
            return super.getData();
        } else {
            return null;
        }
    }
}
