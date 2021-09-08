package Adapters.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.Sensors.AccelerometerFragment;
import Fragments.Sensors.Saved.SavedAccelerometerFragment;


public class accelerometerAdapter extends FragmentStateAdapter {
    public accelerometerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AccelerometerFragment();
            case 1:
                return new SavedAccelerometerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
