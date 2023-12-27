package it.unina.dietideals24.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import it.unina.dietideals24.R;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerViewNotification;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        initializeViews(view);

        return view;
    }

    private void initializeViews(View view) {
        recyclerViewNotification = view.findViewById(R.id.notificationList);
    }

    private void initializeNotification(View view) {

    }
}