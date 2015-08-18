package fabianterhorst.github.io.schoolschedules.fragments;

import android.support.v7.widget.RecyclerView;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.adapter.LessonAdapter;
import fabianterhorst.github.io.schoolschedules.callbacks.DataChangeCallback;

public class LessonsFragment extends RecyclerViewFragment {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new LessonAdapter(getDataStore().getLessons());
    }

    @Override
    public DataChangeCallback getDataChangeCallback() {
        return new DataChangeCallback() {
            @Override
            public void onLessonDataChange() {
                getSuperRecyclerView().getAdapter().notifyDataSetChanged();
            }
        };
    }

    @Override
    public String getEmptyViewMessage() {
        return getString(R.string.no_lessons);
    }
}
