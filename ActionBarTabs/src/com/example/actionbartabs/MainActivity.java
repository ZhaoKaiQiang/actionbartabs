package com.example.actionbartabs;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

/**
 * 
 * @ClassName: com.example.actionbartabs.MainActivity
 * @Description: ActionBar、DrawerLayout、ViewPager、Tab综合实例
 * @author zhaokaiqiang
 * @date 2014-10-28 下午4:30:09
 * 
 */
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	// 滑动Fragment适配器
	SectionsPagerAdapter mSectionsPagerAdapter;
	// Fragment容器
	ViewPager mViewPager;
	// 用于控制DrawerLayout的行为
	ActionBarDrawerToggle mDrawerToggle;
	// 原生侧滑栏布局
	private DrawerLayout mDrawerLayout;
	// ActionBar对象
	private ActionBar actionBar;

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ColorDrawable drawable = new ColorDrawable(getResources().getColor(
				android.R.color.holo_orange_light));
		
		getActionBar().setBackgroundDrawable(drawable);
		
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		listView = (ListView) findViewById(R.id.left_drawer);
		listView.setAdapter(new MenuAdapter(this));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 关闭侧滑栏
				mDrawerLayout.closeDrawers();
			}

		});

		// 初始化
		actionBar = getActionBar();
		// 设置导航模式为Tab方式
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 设置显示返回剪头
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		// 初始化DrawerLayout布局
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// 将DrawerLayout布局绑定到当前界面，并设置点击事件
		mDrawerToggle = new ActionBarDrawerToggle(this, /* 宿主 Activity */
		mDrawerLayout, /* 需要绑定的DrawerLayout对象 */
		R.drawable.ic_drawer, /* 用于替换向上的图片 */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				// creates call to onPrepareOptionsMenu()
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// creates call to onPrepareOptionsMenu()
				invalidateOptionsMenu();
			}
		};

		// 绑定监听器
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		
		
		
		
		// 初始化ViewPager的适配器对象
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// 在不同的Fragment之间滑动的时候，修改选中的tab，我们也可以使用ActionBar.Tab#select()完成，如果我们有Tab的引用的话
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// 根据界面数量添加Tab
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 创建标题栏菜单
		getMenuInflater().inflate(R.menu.main, menu);

		// 设置分享
		MenuItem item = menu.findItem(R.id.action_share);
		ShareActionProvider provider = (ShareActionProvider) item
				.getActionProvider();
		// 定义分享的Intent
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "share");
		intent.putExtra(Intent.EXTRA_TEXT, "share by Action Provider");
		provider.setShareIntent(intent);

		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// 处理Tab的点击，ViewPager滑动到对应的位置
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	// 如果我们想使用ActionBarDrawerToggle，我们必须在onPostCreate()和onConfigurationChanged()方法里面调用下面的方法
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
