package com.guangoon.weibo;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.ActionProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guangoon.weibo.tabfragment.DiscoverFragment;
import com.guangoon.weibo.tabfragment.HomeFragment;
import com.guangoon.weibo.tabfragment.MessageFragment;
import com.guangoon.weibo.tabfragment.ProfileFragment;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	private static final String TAG = "MainActivity";
	private FragmentTabHost mFragmentTabHost;
	private ImageView mWeiboAdd;
	private ActionbarProfileController mActionbarProfileController;
	private ActionbarDiscover mActionbarDiscover;
	private ActionbarHome mActionbarHome;
	private ActionbarMessage mActionbarMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mFragmentTabHost.setup(this, getSupportFragmentManager(),
				R.id.fragment_content);
		mFragmentTabHost.addTab(
				mFragmentTabHost.newTabSpec(Constants.TAB_HOME).setIndicator(
						View.inflate(this, R.layout.tab_indicator_home, null)),
				HomeFragment.class, null);
		mFragmentTabHost.addTab(
				mFragmentTabHost.newTabSpec(Constants.TAB_MESSAGE)
						.setIndicator(
								View.inflate(this,
										R.layout.tab_indicator_message, null)),
				MessageFragment.class, null);
		mFragmentTabHost.addTab(
				mFragmentTabHost.newTabSpec(Constants.TAB_ADD).setIndicator(
						View.inflate(this, R.layout.tab_indicator_add, null)),
				MessageFragment.class, null);

		mFragmentTabHost
				.addTab(mFragmentTabHost.newTabSpec(Constants.TAB_DISCOVER)
						.setIndicator(
								View.inflate(this,
										R.layout.tab_indicator_discover, null)),
						DiscoverFragment.class, null);
		mFragmentTabHost.addTab(
				mFragmentTabHost.newTabSpec(Constants.TAB_PROFILE)
						.setIndicator(
								View.inflate(this,
										R.layout.tab_indicator_profile, null)),
				ProfileFragment.class, null);
		mFragmentTabHost.setOnTabChangedListener(mOnTabChangeListener);
		mWeiboAdd = (ImageView) findViewById(R.id.weibo_add);
		mWeiboAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,WBAuthActivity.class));	
		}});

		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.actionbar_home);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		mActionbarProfileController = new ActionbarProfileController(this);
		mActionbarDiscover = new ActionbarDiscover(this);
		mActionbarHome = new ActionbarHome(this);
		mActionbarMessage = new ActionbarMessage(this);
	}

	private FragmentTabHost.OnTabChangeListener mOnTabChangeListener = new FragmentTabHost.OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {
			ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			if (tabId.equals(Constants.TAB_HOME)) {
				getActionBar().setCustomView(mActionbarHome.getContentView(),
						layoutParams);
			} else if (tabId.equals(Constants.TAB_MESSAGE)) {
				getActionBar().setCustomView(
						mActionbarMessage.getContentView(), layoutParams);
			} else if (tabId.equals(Constants.TAB_DISCOVER)) {
				getActionBar().setCustomView(
						mActionbarDiscover.getContentView(), layoutParams);

			} else if (tabId.equals(Constants.TAB_PROFILE)) {
				getActionBar().setCustomView(
						mActionbarProfileController.getContentView(),
						layoutParams);
			}
			getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			// getActionBar().getCustomView().invalidate();
		}
	};

	private class ActionbarProfileController {
		private View mContentView;
		private Context mContext;
		private TextView mSettingsText;

		public ActionbarProfileController(Context context) {
			mContext = context;
			mContentView = View.inflate(context, R.layout.actionbar_profile,
					null);
			mSettingsText = (TextView) mContentView
					.findViewById(R.id.actionbar_profile_setting);
			mSettingsText.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i(TAG, "mSettingsText##click");

				}
			});
		}

		public View getContentView() {
			return mContentView;
		}
	}

	private class ActionbarMessage {
		private Context mContext;
		private View mContentView;
		private TextView mWriteMessageText;

		public ActionbarMessage(Context context) {
			mContext = context;
			mContentView = View.inflate(context, R.layout.actionbar_message,
					null);
			mWriteMessageText = (TextView) mContentView
					.findViewById(R.id.actionbar_message_write);
			mWriteMessageText.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i(TAG, "mWriteMessageText#onClick");

				}
			});
		}

		public View getContentView() {
			return mContentView;

		}
	}

	private class ActionbarDiscover {
		private Context mContext;
		private View mContentView;
		private TextView mSearchText;
		private ImageView mVoiceRecognition;

		public ActionbarDiscover(Context context) {
			mContext = context;
			mContentView = View.inflate(context, R.layout.actionbar_discover,
					null);
			mSearchText = (TextView) mContentView.findViewById(R.id.seach_text);
			mSearchText.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i(TAG, "mSearchText##click");

				}
			});

			mVoiceRecognition = (ImageView) mContentView
					.findViewById(R.id.voice_recognition);
			mVoiceRecognition.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i(TAG, "mVoiceRecognition##click");

				}
			});

		}

		public View getContentView() {
			return mContentView;
		}

	}

	private class ActionbarHome {
		private Context mContext;
		private View mContentView;
		private ImageView mNavigationFriend;
		private TextView mUserInfoText;
		private ImageView mNavigationArrow;
		private ImageView mNavigationPop;

		public ActionbarHome(Context context) {
			mContext = context;
			mContentView = View.inflate(context, R.layout.actionbar_home, null);
			mNavigationFriend = (ImageView) mContentView
					.findViewById(R.id.navigationbar_friend);
			mNavigationFriend.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i(TAG, "mNavigationFriend##click");
				}
			});

			mUserInfoText = (TextView) mContentView
					.findViewById(R.id.user_name);

			mNavigationArrow = (ImageView) mContentView
					.findViewById(R.id.navigation_arrow);
			mNavigationArrow.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i(TAG, "mNavigationArrow##click");
				}
			});

			mNavigationPop = (ImageView) mContentView
					.findViewById(R.id.navigation_pop);
			mNavigationPop.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i(TAG, "mNavigationPop##clcik");

				}
			});
		}

		public View getContentView() {
			return mContentView;
		}

	}
	
	private class UserContextActionProvider extends ActionProvider{

		public UserContextActionProvider(Context context) {
			super(context);
		}

		@Override
		public View onCreateActionView() {
			return null;
		}
		
	}
	
	

}
