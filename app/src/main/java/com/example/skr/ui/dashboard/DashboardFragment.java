package com.example.skr.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    private List<post> snackList = new ArrayList<>();
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        userAccount = ((MainActivity)getActivity()).getUseraccount();
        Log.d("find bug", "DashboardFragment onresume userAccount : "+userAccount);
        Connector.getDatabase();
        initSnack();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.ranking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));//一定要加manager
        PostAdapter adapter = new PostAdapter(snackList,userAccount);
        recyclerView.setAdapter(adapter);
    }

    private  void initSnack(){
        //采用LitePal在查询数据的时候进行对收藏数进行降序排序，返回对应的post数组
        snackList= DataSupport.order("post_collect_num desc").find(post.class);
    }
}