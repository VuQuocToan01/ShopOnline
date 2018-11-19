package com.example.toanvu.shoponline.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.toanvu.shoponline.R;
import com.example.toanvu.shoponline.adapter.LoaispAdapter;
import com.example.toanvu.shoponline.model.Loaisp;
import com.example.toanvu.shoponline.ultil.CheckConnection;
import com.example.toanvu.shoponline.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ViewFlipper viewFlipper;
    Animation in, out;
    private RecyclerView recyclerview;
    private NavigationView navigationview;
    private ListView lvManHinhChinh;
    Toolbar toolbar;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
            actionBar();
            addEEventViewFlipper();
            getdulieuloaisp();
//            getdulieuSpmoinhat();
//            catchOnItemListView();
        } else {
            CheckConnection.showToast_Short(MainActivity.this, "khong co ket noi intenret");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tolbar_mhc, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void getdulieuloaisp() {

        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        id = object.getInt("id");
                        tenloaisp = object.getString("tenloaisp");
                        hinhanhloaisp = object.getString("hinhanhloaisp");
                        mangloaisp.add(new Loaisp(id, tenloaisp, hinhanhloaisp));
                        loaispAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mangloaisp.add(3, new Loaisp(0, "Liên Hệ", "http://capnuocbenthanh.com/images/dtlienhe_1.jpg"));
                mangloaisp.add(4, new Loaisp(0, "Thông Tin", "http://kinhtevadubao.vn/uploads/images/news/1515687283_news_10383.jpg"));
                loaispAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    //
    private void Anhxa() {
        in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_manhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        navigationview = (NavigationView) findViewById(R.id.navigationview);
        lvManHinhChinh = (ListView) findViewById(R.id.lv_manhinhchinh);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(0, "Trang Chính", "https://vietadsgroup.vn/Uploads/files/trangchu-la-gi.png"));
        loaispAdapter = new LoaispAdapter(MainActivity.this, R.layout.dong_listview_loaisp, mangloaisp);
        lvManHinhChinh.setAdapter(loaispAdapter);
    }

    private void addEEventViewFlipper() {
        ArrayList<String> arrQuangCao = new ArrayList<>();
        arrQuangCao.add("http://channel.mediacdn.vn/prupload/164/2017/05/img20170510232454337.jpg");
        arrQuangCao.add("https://cdn.mediamart.vn/Upload/images/2017/Thang12/W3/tin-J7-Pro.png");
        arrQuangCao.add("https://websaleoff.com/wp-content/uploads/2018/07/qc-top-4-dien-thoai-samsung-danh-cho-sinh-vien-dang-mua-nhat-tai-tiki-vn.jpg");
        arrQuangCao.add("https://image1.thegioitre.vn/2017/04/25/kiem-tra-chong-nuoc.jpg");
        for (int i = 0; i < arrQuangCao.size(); i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            Picasso.with(MainActivity.this).load(arrQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setAutoStart(true);
    }

}
