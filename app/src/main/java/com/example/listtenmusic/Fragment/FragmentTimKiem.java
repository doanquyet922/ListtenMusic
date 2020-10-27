package com.example.listtenmusic.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Adapter.SearchBaiHatAdapter;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTimKiem extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewSearch;
    TextView tKhongcodulieu;
    SearchBaiHatAdapter searchBaiHatAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_timkiem,container,false);
        toolbar=(Toolbar) view.findViewById(R.id.toolbarSearchBaihat);
        recyclerViewSearch=(RecyclerView) view.findViewById(R.id.recylerviewSearchBaihat);
        tKhongcodulieu=(TextView) view.findViewById(R.id.tKhongcodulieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        MenuItem menuItem=menu.findItem(R.id.menuSearch);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchBaiHat(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }
    private void SearchBaiHat(String tukhoa){
        Dataservice dataservice= APIService.getService();
        Call<List<BaiHat>> callback=dataservice.GetSearchBaihat(tukhoa);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangbaihat= (ArrayList<BaiHat>) response.body();
                if(mangbaihat.size()>0){
                    searchBaiHatAdapter=new SearchBaiHatAdapter(getActivity(),mangbaihat);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                    recyclerViewSearch.setLayoutManager(linearLayoutManager);
                    recyclerViewSearch.setAdapter(searchBaiHatAdapter);
                    tKhongcodulieu.setVisibility(View.GONE);
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                }
                else{
                    tKhongcodulieu.setVisibility(View.VISIBLE);
                    recyclerViewSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
