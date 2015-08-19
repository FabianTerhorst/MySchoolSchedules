package fabianterhorst.github.io.schoolschedules.fragments;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.activities.AddActivity;
import fabianterhorst.github.io.schoolschedules.adapter.TeacherAdapter;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;

public class TeachersFragment extends RecyclerViewFragment {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new TeacherAdapter(getDataStore().getTeachers());
    }

    @Override
    public DataChangeCallback getDataChangeCallback() {
        return new DataChangeCallback() {
            @Override
            public void onTeacherDataChange() {
                getSuperRecyclerView().getAdapter().notifyDataSetChanged();
            }
        };
    }

    @Override
    public String getEmptyViewMessage() {
        return getString(R.string.no_teachers);
    }

    @Override
    public Intent getAddIntent() {
        return createAddIntent(AddActivity.type.TEACHER);
    }
}
