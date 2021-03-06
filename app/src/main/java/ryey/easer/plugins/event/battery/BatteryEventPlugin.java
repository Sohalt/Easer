package ryey.easer.plugins.event.battery;

import android.content.Context;

import ryey.easer.commons.plugindef.PluginViewFragment;
import ryey.easer.commons.plugindef.eventplugin.AbstractSlot;
import ryey.easer.commons.plugindef.eventplugin.EventData;
import ryey.easer.commons.plugindef.eventplugin.EventPlugin;

public class BatteryEventPlugin implements EventPlugin {

    @Override
    public String name() {
        return "battery";
    }

    @Override
    public EventData data() {
        return new BatteryEventData();
    }

    @Override
    public PluginViewFragment view() {
        return new BatteryPluginViewFragment();
    }

    @Override
    public AbstractSlot slot(Context context) {
        return new BatterySlot(context);
    }
}
