package com.example.skr.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skr.MainActivity;
import com.example.skr.R;
import com.example.skr.PostAdapter;
import com.example.skr.post;


import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    String userAccount;
   // private List<snack> snackList = new ArrayList<>();
    private List<post> snackList = new ArrayList<>();
    private DashboardViewModel dashboardViewModel;
    public void onAttach(Context context) {
        super.onAttach(context);
        userAccount = ((MainActivity)getActivity()).getUseraccount();

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);



        return root;
    }
    public void onStart() {
        Connector.getDatabase();
        initSnack();
        super.onStart();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.ranking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));//一定要加manager
        PostAdapter adapter = new PostAdapter(snackList,userAccount);
        recyclerView.setAdapter(adapter);
    }
    private  void initSnack(){
//        for(int i =0; i<20;i++){
//            snack chips = new snack("chips",R.drawable.chips);
//            snackList.add(chips);
//            snack meet = new snack("beef",R.drawable.beef);
//            snackList.add(meet);
//        }

        snackList= DataSupport.findAll(post.class);

    }
}