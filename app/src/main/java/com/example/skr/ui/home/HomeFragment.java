package com.example.skr.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.skr.MainActivity;
import com.example.skr.R;
import com.example.skr.PostAdapter;
import com.example.skr.post;
import com.example.skr.posting;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.xuexiang.xui.widget.toast.XToast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    String userAccount;

    private HomeViewModel homeViewModel;
  //  private List<snack> snackList = new ArrayList<>();
    private List<post> snackList = new ArrayList<>();
    private List<post> searchList = new ArrayList<>();
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
                intent.putExtra("userAccount",userAccount);
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
        PostAdapter adapter = new PostAdapter(snackList,userAccount);
        recyclerView.setAdapter(adapter);
    }

    private  void initSnack(){
        if (searchList.size()==0||searchList==null){
            snackList= DataSupport.findAll(post.class);
        }
        else {
            snackList=searchList;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        inflater.inflate(R.menu.search,menu);
        final SearchView sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (TextUtils.isEmpty(s)){
                    XToast.warning(getActivity(),"请完善输入信息").show();
                }else {
                    Connector.getDatabase();
                    searchList = DataSupport.where("post_title like ?","%"+s+"%").find(post.class);
                    if (searchList.size()==0||searchList==null){
                        XToast.info(getActivity(),"查无匹配").show();
                    }else {
                        onStart();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    searchList.clear();
                    onStart();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}