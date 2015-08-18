package fabianterhorst.github.io.schoolschedules.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.Homework;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    private List<Homework> homeworks;

    public HomeworkAdapter(List<Homework> data) {
        this.homeworks = data;
    }

    @Override
    public HomeworkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homework, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeworkAdapter.ViewHolder holder, int position) {
        Homework homework = homeworks.get(position);
        holder.title.setText(homework.getTitle());
        holder.description.setText(homework.getDescription());
    }

    @Override
    public int getItemCount() {
        return homeworks.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_homework_title);
            description = (TextView) itemView.findViewById(R.id.item_homework_description);
        }
    }
}
