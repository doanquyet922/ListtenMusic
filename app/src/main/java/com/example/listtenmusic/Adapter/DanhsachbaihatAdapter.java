package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatAdapter extends RecyclerView.Adapter<DanhsachbaihatAdapter.ViewHolder>{
    Context context;
    ArrayList<BaiHat>mangBaihat;

    public DanhsachbaihatAdapter(Context context, ArrayList<BaiHat> mangBaihat) {
        this.context = context;
        this.mangBaihat = mangBaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_danhsachbaihat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat=mangBaihat.get(position);
        holder.tCasi.setText(baiHat.getCaSi());
        holder.tTenBaihat.setText(baiHat.getTenBaiHat());
        holder.tIndex.setText(position+1+"");


    }

    @Override
    public int getItemCount() {
        return mangBaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tIndex,tTenBaihat,tCasi;
        ImageView imluotthichbdanhsachbaihat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tCasi=itemView.findViewById(R.id.tTenCasi);
            tIndex=itemView.findViewById(R.id.tDanhsachIndex);
            tTenBaihat=itemView.findViewById(R.id.tTenbaihat);
            imluotthichbdanhsachbaihat=itemView.findViewById(R.id.imLuotthichdanhsachbaihat);

            imluotthichbdanhsachbaihat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imluotthichbdanhsachbaihat.setImageResource(R.drawable.icon_love_true);
                    Dataservice dataservice= APIService.getService();
                    Call<String> callback=dataservice.UpdateLuotThich("1",mangBaihat.get(getPosition()).getIDBaiHat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String kq=response.body();
                            if(kq.equals("ok")){
                                Toast.makeText(context,"Đã thích",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,"Bị lỗi",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imluotthichbdanhsachbaihat.setEnabled(false);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc",mangBaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
