package com.gid.milestone.gid_milestone2.ui.viewtask;

import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gid.milestone.gid_milestone2.R;
import com.gid.milestone.gid_milestone2.adapter.task_adapter;
import com.gid.milestone.gid_milestone2.model.task_item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.Query;

public class ViewTaskFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private task_item obj_task_item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_task_fragment, container, false);

        Button b = (Button) v.findViewById(R.id.view_task_button);
        recyclerView = v.findViewById(R.id.task_recycler_view);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewTaskFragment newFragment = new ViewTaskFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container, newFragment, newFragment.getTag())
                        .commit();
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
       Load();
        return v;
    }
    private void Load() {
        Query query = firebaseDatabase.getReference().child("tasks");
        FirebaseRecyclerOptions<task_item> options = new FirebaseRecyclerOptions.Builder<task_item>()
                .setQuery(query, new SnapshotParser<task_item>() {
                    @NonNull
                    @Override
                    public task_item parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new task_item(
                                snapshot.child("title").getValue().toString(),
                                snapshot.child("content").getValue().toString(),
                                snapshot.child("time").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<task_item,task_adapter>(options) {
            @NonNull
            @Override
            public task_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task, parent, false);


                return new task_adapter(view);
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

        @Override
        protected void onBindViewHolder(@NonNull task_adapter holder,  int position, @NonNull task_item task_i) {
            holder.setTxtTitle(task_i.getTitle());
            holder.setTxtContent(task_i.getDescription());
            holder.setTxtTime(task_i.getTime());

            holder.task_layoutt.findViewById(R.id.delete_task_imagebutton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebaseDatabase.getReference().child("tasks")
                            .child(getRef(position).getKey()).removeValue();


                    Toast.makeText(getActivity().getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT).show();



                }
            });
        }

    };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}