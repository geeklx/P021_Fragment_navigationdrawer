package com.liangxiao.navigationdrawertest;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.liangxiao.navigationdrawertest.adapter.NavDrawerListAdapter;
import com.liangxiao.navigationdrawertest.entity.NavDrawerItem;
import com.liangxiao.navigationdrawertest.fragment.CommunityFragment;
import com.liangxiao.navigationdrawertest.fragment.FindPeopleFragment;
import com.liangxiao.navigationdrawertest.fragment.HomeFragment;
import com.liangxiao.navigationdrawertest.fragment.PagesFragment;
import com.liangxiao.navigationdrawertest.fragment.PhotosFragment;
import com.liangxiao.navigationdrawertest.fragment.WhatsHotFragment;

public class MainActivity extends FragmentActivity implements
		OnItemClickListener {
	private CharSequence mTitle;// 标题栏
	private CharSequence mDrawerTitle;// 侧边栏标题栏
	private String[] mNavMenuTitles;// 存放标题栏的数组
	private DrawerLayout mDrawerLayout;// 整个试图
	private ListView mDrawerList;// 存放左边栏的listview
	private List<NavDrawerItem> mNavDrawerItems;// 实例化左边部分的item
	private TypedArray mNavMenuIconsTypeArray;// 存储左边部分的icon
	private NavDrawerListAdapter mAdapter;// 左边部分的adapter
	private ActionBarDrawerToggle mDrawerToggle;// actionbar的棒棒

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		// 进入后选择第一个栏目
		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@SuppressLint("NewApi")
	private void findView() {

		mTitle = mDrawerTitle = getTitle();
		// 主窗体部分
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// 左边部分
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// 滑动出来后的阴影效果
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// 加载的左边部分的listview中的items文字
		mNavMenuTitles = getResources()
				.getStringArray(R.array.nav_drawer_items);
		// 加载的左边部分的listview中的items图标
		mNavMenuIconsTypeArray = getResources().obtainTypedArray(
				R.array.nav_drawer_icons);
		// 实例化数组来存放左边部分的数据
		mNavDrawerItems = new ArrayList<NavDrawerItem>();

		// 添加数据项目到数组
		// Home
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[0],
				mNavMenuIconsTypeArray.getResourceId(0, -1)));
		// Find People
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[1],
				mNavMenuIconsTypeArray.getResourceId(1, -1)));
		// Photos
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[2],
				mNavMenuIconsTypeArray.getResourceId(2, -1)));
		// Communities, Will add a counter here
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[3],
				mNavMenuIconsTypeArray.getResourceId(3, -1)));
		// Pages
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[4],
				mNavMenuIconsTypeArray.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[5],
				mNavMenuIconsTypeArray.getResourceId(5, -1), true, "50+"));

		// Recycle the typed array
		mNavMenuIconsTypeArray.recycle();

		// setting the nav drawer list adapter
		mAdapter = new NavDrawerListAdapter(getApplicationContext(),
				mNavDrawerItems);
		mDrawerList.setAdapter(mAdapter);
		mDrawerList.setOnItemClickListener(this);

		// actionbar的点击操作部分
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// 点击弹出左边的操作部分
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	/**
	 * 菜单栏部分
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * 点击搜索&刷新事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.action_search:

			Toast.makeText(this, R.string.action_search, Toast.LENGTH_SHORT)
					.show();

			return true;
		case R.id.action_refresh:

			Toast.makeText(this, R.string.action_refresh, Toast.LENGTH_SHORT)
					.show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * 左边部分的item点击事件部分
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		selectItem(position);
	}

	/**
	 * 根据左边部分的列表点击事件显示fragment
	 * */
	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new FindPeopleFragment();
			break;
		case 2:
			fragment = new PhotosFragment();
			break;
		case 3:
			fragment = new CommunityFragment();
			break;
		case 4:
			fragment = new PagesFragment();
			break;
		case 5:
			fragment = new WhatsHotFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			// 记录点击的item的位置部分
			mDrawerList.setItemChecked(position, true);
			// 记录位置状态部分
			mDrawerList.setSelection(position);
			// 把actionbar的标题设置为item的content
			setTitle(mNavMenuTitles[position]);
			// 关闭左边部分
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

}
