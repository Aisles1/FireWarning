package hhu.com.warehouse_fire_warning.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hhu.com.warehouse_fire_warning.R;
import hhu.com.warehouse_fire_warning.entity.Log;

public class LogAdapter extends BaseAdapter {

    private Context mContext;

    private List<Log> logList;

    public LogAdapter(Context mContext, List<Log> logList) {
        this.mContext = mContext;
        this.logList = logList;
    }

    @Override
    public int getCount() {
        return logList.size();
    }

    @Override
    public Object getItem(int position) {
        return logList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(mContext, R.layout.item_log,null);
        }
        TextView time = view.findViewById(R.id.time);
        TextView smoke = view.findViewById(R.id.smoke);
        TextView tem = view.findViewById(R.id.tem);
        TextView hum = view.findViewById(R.id.hum);

        Log log = logList.get(position);

        time.setText(log.getTime());
        smoke.setText(String.valueOf(log.getSmoke()));
        tem.setText(String.valueOf(log.getTem()));
        hum.setText(String.valueOf(log.getHum()));
        return view;
    }
}
