package fabianterhorst.github.io.schoolschedules.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.Lesson;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private List<Lesson> lessons;

    public LessonAdapter(List<Lesson> data) {
        this.lessons = data;
    }

    @Override
    public LessonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonAdapter.ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.name.setText(lesson.getName());
        holder.shortname.setText(lesson.getShortname());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, shortname;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_lesson_name);
            shortname = (TextView) itemView.findViewById(R.id.item_lesson_shortname);
        }
    }
}
