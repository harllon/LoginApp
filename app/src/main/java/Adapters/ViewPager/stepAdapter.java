package Adapters.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import FragmentsNotUsed.Sensors.Saved.SavedStepFragment;
import FragmentsNotUsed.Sensors.StepFragment;

public class stepAdapter extends FragmentStateAdapter {
    public stepAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new StepFragment();
            case 1:
                return new SavedStepFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
