package com.example.selectedgridview;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("NewApi") public class MainActivity extends ActionBarActivity implements OnClickListener{

	private TextView select,selectAll,finish;
	private LinearLayout llSelect;
	private GridView gridview;
	private GridAdapter mGridAdapter;
	private int flag = 0;//0全选；1全不选
	private int mode = 0;//0普通；1筛选
	private Map<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();
	private int[] mImgIds = new int[] {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,
			R.drawable.ic_launcher,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();
        setListener();
    }


    private void setListener() {
		// TODO 自动生成的方法存根
		select.setOnClickListener(this);
		selectAll.setOnClickListener(this);
		finish.setOnClickListener(this);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO 自动生成的方法存根
				if(mode==1){
					//筛选模式
					SelectView scView = (SelectView) view;
					if(scView.isChecked()){
						mSelectMap.put(position, false);
						gridview.setItemChecked(position, false);
					}else{
						mSelectMap.put(position, true);
						gridview.setItemChecked(position, true);
					}
					mGridAdapter.notifyDataSetChanged();
				}
			}
		});
	}


	private void initViews() {
		// TODO 自动生成的方法存根
		select = (TextView) findViewById(R.id.select);
		llSelect = (LinearLayout) findViewById(R.id.ll_select);
		selectAll = (TextView) findViewById(R.id.selectall);
		finish = (TextView) findViewById(R.id.finish);
		gridview = (GridView) findViewById(R.id.gridview);
		mGridAdapter = new GridAdapter(this);
		gridview.setAdapter(mGridAdapter);
		for (int i = 0; i < gridview.getCount(); i++) {
			gridview.setItemChecked(i, false);
			mSelectMap.clear();
		}
		flag = 0;
		selectAll.setText("全选");
		mGridAdapter.notifyDataSetChanged();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	
	private class GridAdapter extends BaseAdapter {

		private Context mContext;

		public GridAdapter(Context ctx) {
			mContext = ctx;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImgIds.length;
		}

		@Override
		public Integer getItem(int position) {
			// TODO Auto-generated method stub
			return Integer.valueOf(mImgIds[position]);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressWarnings("deprecation")
		@Override
		/* 得到View */
		public View getView(int position, View convertView, ViewGroup parent) {
			SelectView item;
			if (convertView == null) {
				item = new SelectView(mContext);
				item.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT));
			} else {
				item = (SelectView) convertView;
			}
			item.setImgResId(getItem(position));
			item.setChecked(mSelectMap.get(position) == null ? false
					: mSelectMap.get(position));
			return item;
		}
	}
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.select:
			mode = 1;
			select.setVisibility(View.GONE);
			llSelect.setVisibility(View.VISIBLE);
			break;
		case R.id.selectall:
			if(flag == 0){
				//全选
				for (int i = 0; i < gridview.getCount(); i++) {
					gridview.setItemChecked(i, true);
					mSelectMap.put(i, true);
				}
				flag = 1;
				selectAll.setText("全不选");
			}else{
				//全不选
				for (int i = 0; i < gridview.getCount(); i++) {
					gridview.setItemChecked(i, false);
					mSelectMap.clear();
				}
				flag = 0;
				selectAll.setText("全选");
			}
			mGridAdapter.notifyDataSetChanged();
			break;
		case R.id.finish:
			mode = 0;
			select.setVisibility(View.VISIBLE);
			llSelect.setVisibility(View.GONE);
			finish(mSelectMap);
			for (int i = 0; i < gridview.getCount(); i++) {
				gridview.setItemChecked(i, false);
				mSelectMap.clear();
			}
			flag = 0;
			selectAll.setText("全选");
			mGridAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}


	private void finish(Map<Integer, Boolean> mSelectMap2) {
		// TODO 自动生成的方法存根
		//完成操作
	}
}
