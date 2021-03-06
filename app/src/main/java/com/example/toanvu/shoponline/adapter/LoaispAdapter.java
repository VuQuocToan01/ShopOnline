package com.example.toanvu.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toanvu.shoponline.R;
import com.example.toanvu.shoponline.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Loaisp> arrLoaiSp = new ArrayList<>();

    public LoaispAdapter(Context context, int layout, ArrayList<Loaisp> arrLoaiSp) {
        this.context = context;
        this.layout = layout;
        this.arrLoaiSp = arrLoaiSp;
    }

    @Override
    public int getCount() {
        return arrLoaiSp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrLoaiSp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(layout,null);
        TextView tvTenloaisp=view.findViewById(R.id.tv_loaisp);
        ImageView imgloaisp=view.findViewById(R.id.img_loaisp);
        tvTenloaisp.setText(arrLoaiSp.get(i).getTenloaisp());
        Picasso.with(context).load(arrLoaiSp.get(i)
                .getHinhanhloaisp())
                .centerCrop()
                .resize(150,150)
                .into(imgloaisp);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("test1","t2");
//                Intent intent=new Intent(context, ChiTietActivity.class);
//                intent.putExtra("chitietsanpham",arrLoaiSp.get(i));
//                context.startActivity(intent);
//            }
//        });
        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
