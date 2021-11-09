package Adapters.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import FragmentsNotUsed.Sensors.RotationFragment;
import FragmentsNotUsed.Sensors.Saved.SavedRotationFragment;

public class rotationAdapter extends FragmentStateAdapter {
    public rotationAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RotationFragment();
            case 1:
                return new SavedRotationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
