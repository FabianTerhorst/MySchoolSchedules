package fabianterhorst.github.io.schoolschedules.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.Representation;
import fabianterhorst.github.io.schoolschedules.views.TextDrawable;

public class RepresentationAdapter extends RecyclerView.Adapter<RepresentationAdapter.ViewHolder> {

    private List<Representation> representations;

    public RepresentationAdapter(List<Representation> data) {
        this.representations = data;
    }

    @Override
    public RepresentationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_representation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepresentationAdapter.ViewHolder holder, int position) {
        Representation representation = representations.get(position);
        holder.date.setText(representation.getDay() + " (" + representation.getDate() + ")");
        holder.teacher.setText(representation.getTeacher());
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(representation.getLesson(), Color.RED);
        holder.lesson.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return representations.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacher, date;
        public ImageView lesson;

        public ViewHolder(View itemView) {
            super(itemView);
            teacher = (TextView) itemView.findViewById(R.id.item_representation_teacher);
            date = (TextView) itemView.findViewById(R.id.item_representation_date);
            lesson = (ImageView) itemView.findViewById(R.id.item_representation_lesson);
        }
    }
}
