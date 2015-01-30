package com.corpit.qr.activity;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.corpit.corpitiscan.R;
import com.corpit.qr.tools.BaseFragment;
import com.corpit.qr.tools.FragmentUtils;
import com.corpit.qr.views.ReclickableTabHost;

/**
 * 
 *****************************************************
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>Coffee</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2015-1-22 下午6:09:04</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * @author aa1000777 - Email:aa1000777@qq.com
 *****************************************************
 */
public class ActivityTabBar extends FragmentActivity {
	ReclickableTabHost mTabHost;
	TabManager mTabManager;

	public static int ANIMATION_PUSH = 1;
	public static int ANIMATION_FLIP = 2;

	private static ActivityTabBar instance;

	public static ActivityTabBar getInstance() {
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		instance = this;

		setContentView(R.layout.activity_tab_bar);

		mTabHost = (ReclickableTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
		mTabManager.addTab("Scan", R.drawable.tab_scan_selected, R.drawable.tab_scan_normal, FragmentCamera.class, null);
		mTabManager.addTab("History", R.drawable.tab_history_selected, R.drawable.tab_history_normal, FragmentHistory.class, null);
		mTabManager.addTab("Statistics", R.drawable.tab_more_selected, R.drawable.tab_more_normal, FragmentMore.class, null);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		} else {
			mTabHost.setCurrentTabByTag("Scan");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.e("", "TABBAR onSaveInstanceState");
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	public void onResume() {
		super.onResume();
		// this.setResult(0);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void addFragment(Fragment fragment, int animType) {

		BaseFragment curFrag = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.realtabcontent);
		curFrag.saveState();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (animType == ANIMATION_PUSH)
			ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out, R.anim.push_right_in, R.anim.push_right_out);
		else if (animType == ANIMATION_FLIP)
			ft.setCustomAnimations(R.anim.flip_out, R.anim.flip_in, R.anim.flip_out, R.anim.flip_in);
		ft.replace(R.id.realtabcontent, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void removeFragment() {
		getSupportFragmentManager().popBackStackImmediate();
	}

	public void selectTab(String tag) {
		// mTabManager.setTabSelected(tag, true);
		mTabHost.setCurrentTabByTag(tag);
	}

	public void setTabBarVisible(boolean visible) {
		mTabHost.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
	}

	/**
	 * This is a helper class that implements a generic mechanism for
	 * associating fragments with the tabs in a tab host. It relies on a trick.
	 * Normally a tab host has a simple API for supplying a View or Intent that
	 * each tab will show. This is not sufficient for switching between
	 * fragments. So instead we make the content part of the tab host 0dp high
	 * (it is not shown) and the TabManager supplies its own dummy view to show
	 * as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct fragment shown in a separate content area whenever
	 * the selected tab changes.
	 */
	public static class TabManager implements TabHost.OnTabChangeListener, ReclickableTabHost.OnSameTabClickedListener {
		private final FragmentActivity mActivity;
		private final ReclickableTabHost mTabHost;
		private final int mContainerId;
		private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
		TabInfo mCurrentTab;
		TabInfo mLastTab;

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private BaseFragment fragment;
			private int selectedId;
			private int normalId;
			private int id;

			TabInfo(int _id, String _tag, Class<?> _class, Bundle _args, int _selectedId, int _normalId) {
				id = _id;
				tag = _tag;
				clss = _class;
				args = _args;
				selectedId = _selectedId;
				normalId = _normalId;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabManager(FragmentActivity activity, ReclickableTabHost tabHost, int containerId) {
			mActivity = activity;
			mTabHost = tabHost;
			mContainerId = containerId;
			mTabHost.setOnTabChangedListener(this);
			mTabHost.setOnSameTabClickedListener(this);
		}

		public void addTab(String name, int selectedId, int normalId, Class<?> clss, Bundle args) {
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec(name);
			tabSpec.setContent(new DummyTabFactory(mActivity));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(mTabs.size(), tag, clss, args, selectedId, normalId);

			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			info.fragment = (BaseFragment) mActivity.getSupportFragmentManager().findFragmentByTag(tag);
			if (info.fragment != null && !info.fragment.isDetached()) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				ft.detach(info.fragment);
				ft.commit();
			}

			mTabs.put(tag, info);

			View tabView = createTabView(mTabHost.getContext(), name);
			tabSpec.setIndicator(tabView);
			mTabHost.addTab(tabSpec);
			setTabSelected(name, false);
		}

		private View createTabView(Context context, String tag) {
			View view = LayoutInflater.from(mActivity).inflate(R.layout.tabbar_button, null);
			TextView title = (TextView) view.findViewById(R.id.tabLabel);
			title.setText(tag);
			return view;
		}

		private void setTabSelected(String tabId, boolean selected) {
			TabInfo newTab = mTabs.get(tabId);
			View currentTabView = mTabHost.getTabWidget().getChildAt(newTab.id);
			ImageView icon = (ImageView) currentTabView.findViewById(R.id.tabImage);
			icon.setImageResource(selected ? newTab.selectedId : newTab.normalId);
		}

		public void clearBackStack() {
			FragmentUtils.sDisableFragmentAnimations = true;
			mActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			FragmentUtils.sDisableFragmentAnimations = false;
		}

		@Override
		public void onTabChanged(String tabId) {
			TabInfo newTab = mTabs.get(tabId);
			clearBackStack();

			if (mCurrentTab != newTab) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				if (mCurrentTab != null) {
					setTabSelected(mCurrentTab.tag, false);
					if (mCurrentTab.fragment != null) {
						ft.detach(mCurrentTab.fragment);
					}
				}
				if (newTab != null) {
					Log.e("", newTab.id + "");

					setTabSelected(tabId, true);
					if (newTab.fragment == null) {
						newTab.fragment = (BaseFragment) Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
					} else {
						newTab.fragment.refresh();
						ft.attach(newTab.fragment);
					}
				}

				mLastTab = mLastTab == null ? newTab : mCurrentTab;
				mCurrentTab = newTab;
				ft.commit();
				mActivity.getSupportFragmentManager().executePendingTransactions();
			}
		}

		@Override
		public void onTabReclicked(int index) {
			mCurrentTab.fragment.refresh();
			clearBackStack();
		}
	}
}
