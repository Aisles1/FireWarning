package hhu.com.warehouse_fire_warning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hhu.com.warehouse_fire_warning.adapter.LogAdapter;
import hhu.com.warehouse_fire_warning.entity.Log;
import hhu.com.warehouse_fire_warning.util.SendMailToSb;

public class LogActivity extends AppCompatActivity {

    private List<Log> logList;

    private ListView listView;

    private LogAdapter adapter;

    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        listView = findViewById(R.id.logShow);
        mSearchView = findViewById(R.id.search);

        logList = LitePal.findAll(Log.class);
        adapter = new LogAdapter(LogActivity.this, logList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮触发的方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //输入框发生改变触发的方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    listView.setFilterText(newText);
                } else {
                    listView.clearTextFilter();
                }
                return false;
            }
        });
    }
}
