package neet.com.knowweather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import neet.com.knowweather.Adapter.OrderAdapter;
import neet.com.knowweather.R;
import neet.com.knowweather.bean.OrderRecord;

public class OrderListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lvOrderlist;
    private List<OrderRecord> list =new ArrayList<>();
    private OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        intview();
    }

    void intview(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvOrderlist = (ListView) findViewById(R.id.lv_orderlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//主键按钮能否可点击x
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回图标
        setOrderList();
        orderAdapter=new OrderAdapter(getApplicationContext(),R.layout.item_orderlist,list);

        lvOrderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderRecord orderRecord= (OrderRecord) orderAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("order",orderRecord);
                startActivity(intent);
            }
        });
        lvOrderlist.setAdapter(orderAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void setOrderList(){
        OrderRecord orderRecord1 = new OrderRecord(1,1,1,"2020年12月12日","2020年12月12日",1,1);
        OrderRecord orderRecord2 = new OrderRecord(1,2,1,"2020年12月12日","2020年12月12日",1,1);
        list.add(orderRecord1);list.add(orderRecord2);
    }
}
