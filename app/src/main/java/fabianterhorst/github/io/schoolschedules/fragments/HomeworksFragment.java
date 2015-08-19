package fabianterhorst.github.io.schoolschedules.fragments;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.activities.AddActivity;
import fabianterhorst.github.io.schoolschedules.adapter.HomeworkAdapter;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;

public class HomeworksFragment extends RecyclerViewFragment {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new HomeworkAdapter(getDataStore().getHomeworks());
    }

    @Override
    public DataChangeCallback getDataChangeCallback() {
        return new DataChangeCallback() {
            @Override
            public void onHomeworkDataChange() {
                getSuperRecyclerView().getAdapter().notifyDataSetChanged();
            }
        };
    }

    @Override
    public String getEmptyViewMessage() {
        return getString(R.string.no_homeworks);
    }

    @Override
    public Intent getAddIntent() {
        return createAddIntent(AddActivity.type.HOMEWORK);
    }

    @Override
    public boolean hasSwipeToDismiss() {
        return true;
    }

    @Override
    public void deleteByPosition(int position) {
        getDataStore().deleteHomeworkByPosition(position);
    }
}
