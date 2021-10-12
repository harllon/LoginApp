package Adapters.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.EnvironmentFragment;
import Fragments.MotionFragment;
import Fragments.PositionFragment;

public class SensorOptionAdapter extends FragmentStateAdapter {
    public SensorOptionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MotionFragment();
            case 1:
                return new PositionFragment();
            case 2:
                return new EnvironmentFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
