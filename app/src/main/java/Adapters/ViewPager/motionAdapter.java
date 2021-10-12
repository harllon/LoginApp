package Adapters.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.Sensors.MotiondetectFragment;
import Fragments.Sensors.Saved.SavedMotionFragment;

public class motionAdapter extends FragmentStateAdapter {
    public motionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MotiondetectFragment();
            case 1:
                return new SavedMotionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
