package com.example.event_planner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.event_planner.R;
import com.example.event_planner.models.Event;
import com.example.event_planner.utils.DateTimeUtils;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;
    private OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(Event event);
        void onEventEdit(Event event);
        void onEventDelete(Event event);
    }

    public EventAdapter(Context context, List<Event> eventList, OnEventClickListener listener) {
        this.context = context;
        this.eventList = eventList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return eventList != null ? eventList.size() : 0;
    }

    public void updateEvents(List<Event> newEventList) {
        this.eventList = newEventList;
        notifyDataSetChanged();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tvTitle, tvDateTime, tvLocation, tvPriority;
        private ImageView ivPriority, ivEdit, ivDelete;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_event);
            tvTitle = itemView.findViewById(R.id.tv_event_title);
            tvDateTime = itemView.findViewById(R.id.tv_event_datetime);
            tvLocation = itemView.findViewById(R.id.tv_event_location);
            tvPriority = itemView.findViewById(R.id.tv_event_priority);
            ivPriority = itemView.findViewById(R.id.iv_priority);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }

        public void bind(Event event) {
            tvTitle.setText(event.getTitle());
            tvDateTime.setText(DateTimeUtils.formatEventDateTime(event.getStartDateTime(), event.getEndDateTime()));

            // Location
            if (event.getLocation() != null && !event.getLocation().isEmpty()) {
                tvLocation.setText(event.getLocation());
                tvLocation.setVisibility(View.VISIBLE);
            } else {
                tvLocation.setVisibility(View.GONE);
            }

            tvPriority.setText(event.getPriority().toUpperCase());
            setPriorityStyle(event.getPriority());

            cardView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEventClick(event);
                }
            });

            ivEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEventEdit(event);
                }
            });

            ivDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEventDelete(event);
                }
            });
        }

        private void setPriorityStyle(String priority) {
            int colorRes;
            int iconRes;

            switch (priority.toLowerCase()) {
                case Event.PRIORITY_HIGH:
                    colorRes = android.R.color.holo_red_light;
                    iconRes = R.drawable.ic_priority_high;
                    break;
                case Event.PRIORITY_MEDIUM:
                    colorRes = android.R.color.holo_orange_light;
                    iconRes = R.drawable.ic_priority_medium;
                    break;
                case Event.PRIORITY_LOW:
                default:
                    colorRes = android.R.color.holo_green_light;
                    iconRes = R.drawable.ic_priority_low;
                    break;
            }

            tvPriority.setTextColor(context.getResources().getColor(colorRes));
            ivPriority.setImageResource(iconRes);
            ivPriority.setColorFilter(context.getResources().getColor(colorRes));
        }
    }
}