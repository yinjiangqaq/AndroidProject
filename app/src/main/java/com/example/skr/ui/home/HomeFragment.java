package com.example.skr.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.skr.MainActivity;
import com.example.skr.R;
import com.example.skr.SnackAdapter;
import com.example.skr.post;
import com.example.skr.posting;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    String userAccount;
    private HomeViewModel homeViewModel;
  //  private List<snack> snackList = new ArrayList<>();
    private List<post> snackList = new ArrayList<>();
    public void onAttach(Context context) {
        super.onAttach(context);
        userAccount = ((MainActivity)getActivity()).getUseraccount();

    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        Connector.getDatabase();

        //定义一个悬浮可拖动按钮
        final FloatingActionButton fab =  root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //添加跳转
                Intent intent = new Intent(getActivity(),posting.class);
                startActivity(intent);
            }
        });

        return root;
    }


    @Override
    public void onStart() {
        Connector.getDatabase();
        initSnack();
        super.onStart();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.snack);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));//一定要加manager
        SnackAdapter adapter = new SnackAdapter(snackList);
        recyclerView.setAdapter(adapter);
    }

    private  void initSnack(){
//        for(int i =0; i<20;i++){
//            snack meet = new snack("beef",R.drawable.beef);
//            snackList.add(meet);
//
//        }
        snackList= DataSupport.findAll(post.class);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main,menu);
        inflater.inflate(R.menu.search,menu);
        SearchView sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}