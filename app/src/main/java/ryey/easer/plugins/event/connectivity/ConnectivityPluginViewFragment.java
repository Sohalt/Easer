package ryey.easer.plugins.event.connectivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.Set;

import ryey.easer.R;
import ryey.easer.commons.plugindef.PluginViewFragment;
import ryey.easer.commons.plugindef.StorageData;

public class ConnectivityPluginViewFragment extends PluginViewFragment {
    String []mode_names;
    int []values = {
            ConnectivityType.TYPE_NOT_CONNECTED,
            ConnectivityType.TYPE_WIFI,
            ConnectivityType.TYPE_MOBILE,
            ConnectivityType.TYPE_ETHERNET,
            ConnectivityType.TYPE_BLUETOOTH,
            ConnectivityType.TYPE_VPN,
    };
    CheckBox[] checkBoxes = new CheckBox[values.length];

    {
        setDesc(R.string.event_connectivity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode_names = getResources().getStringArray(R.array.connectivity_type);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i] = new CheckBox(getContext());
            checkBoxes[i].setText(mode_names[i]);
            linearLayout.addView(checkBoxes[i]);
        }
        return linearLayout;
    }

    @Override
    protected void _fill(StorageData data) {
        if (data instanceof ConnectivityEventData) {
            Set<Integer> checked_values = (Set<Integer>) data.get();
            for (int checked_value : checked_values) {
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == checked_value) {
                        checkBoxes[i].setChecked(true);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public StorageData getData() {
        Set<Integer> checked = new ArraySet<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isChecked()) {
                checked.add(values[i]);
            }
        }
        if (checked.size() == 0)
            return null;
        return new ConnectivityEventData(checked);
    }
}
