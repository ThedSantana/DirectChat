package info.hoang8f.directchat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoang8f on 11/6/13.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    private Activity activity;
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private ArrayList<String> peers = new ArrayList<String>();
    private NavigationDrawerFragment navigationDrawerFragment;
    private ListView mListView;

    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            // Out with the old, in with the new.
            peers.clear();
            for (WifiP2pDevice device : peerList.getDeviceList()) {
                peers.add(device.deviceName);
            }
//            navigationDrawerFragment.setListDevices(peers);
            navigationDrawerFragment.refreshListDevice(peers);

            // If an AdapterView is backed by this data, notify it
            // of the change.  For instance, if you have a ListView of available
            // peers, trigger an update.
//            navigationDrawerFragment.getDevicesAdapter().notifyDataSetChanged();
//            navigationDrawerFragment.getDrawerListView().invalidate();
            if (peers.size() == 0) {
                return;
            }
        }
    };

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, ChatActivity activity) {
        this.activity = activity;
        this.mChannel = channel;
        this.mManager = manager;
        this.navigationDrawerFragment = activity.getNavigationFragment();
        this.mListView = navigationDrawerFragment.getDrawerListView();
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
//                activity.setIsWifiP2pEnabled(true);
            } else {
//                activity.setIsWifiP2pEnabled(false);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed!  We should probably do something about
            // that.
            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }


        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed!  We should probably do something about
            // that.

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            /*
            DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
                    .findFragmentById(R.id.frag_list);
            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
             */

        }
    }
}