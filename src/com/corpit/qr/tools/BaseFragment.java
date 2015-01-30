package com.corpit.qr.tools;

import com.corpit.qr.activity.ActivityTabBar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.animation.Animation;

public class BaseFragment extends Fragment {
	public FragmentManager fragmentManager;
	public Context context;

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		if (FragmentUtils.sDisableFragmentAnimations) {
			Animation a = new Animation() {
			};
			a.setDuration(0);
			return a;
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = this.getActivity();
		fragmentManager = this.getFragmentManager();

	}

	protected void sendDataToTarget(Object data) {
		BaseFragment f = (BaseFragment) this.getTargetFragment();
		if (f != null) {
			f.receiveDataFromTarget(data);
		}
	}

	protected void receiveDataFromTarget(Object data) {
		Log.e(this.getClass().getSimpleName(), "receiveDataFromTarget: " + data);
	}

	protected final void gotoNextFragment(BaseFragment fragment, Bundle args,
			int animType) {
		if (args != null)
			fragment.setArguments(args);
		fragment.setTargetFragment(this, 0);
		ActivityTabBar.getInstance().addFragment(fragment, animType);
	}

	protected final void finishFragment() {
		ActivityTabBar.getInstance().removeFragment();
	}

	public void refresh() {

	}

	public void saveState() {

	}

	public boolean onBackPressed() {
		return false;
	}
}
