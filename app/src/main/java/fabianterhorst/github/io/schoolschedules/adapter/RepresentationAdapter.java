package fabianterhorst.github.io.schoolschedules.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fabianterhorst.github.io.schoolschedules.R;
import fabianterhorst.github.io.schoolschedules.models.Representation;

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
        holder.className.setText(representation.getClass_name());
        holder.date.setText(representation.getDate());
        holder.day.setText(representation.getDay());
        holder.teacher.setText(representation.getTeacher());
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
        public TextView className, teacher, date, day;

        public ViewHolder(View itemView) {
            super(itemView);
            className = (TextView) itemView.findViewById(R.id.item_representation_class);
            teacher = (TextView) itemView.findViewById(R.id.item_representation_teacher);
            date = (TextView) itemView.findViewById(R.id.item_representation_date);
            day = (TextView) itemView.findViewById(R.id.item_representation_day);
        }
    }
}
