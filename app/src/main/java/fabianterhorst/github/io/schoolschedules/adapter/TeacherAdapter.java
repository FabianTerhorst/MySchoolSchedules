package fabianterhorst.github.io.schoolschedules.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.Teacher;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private List<Teacher> teachers;

    public TeacherAdapter(List<Teacher> data) {
        this.teachers = data;
    }

    @Override
    public TeacherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherAdapter.ViewHolder holder, int position) {
        Teacher teacher = teachers.get(position);
        holder.name.setText(teacher.getSalutation() + " " + teacher.getSurname());
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, lessons;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_teacher_name);
            lessons = (TextView) itemView.findViewById(R.id.item_teacher_lessons);
        }
    }
}
