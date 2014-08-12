package com.guangoon.weibo;

import com.guangoon.weibo.fragment.PersonHomePage;
import com.guangoon.weibo.fragment.PersonPhotoPage;
import com.guangoon.weibo.fragment.PersonWeiboPage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

@SuppressLint("NewApi")
public class PersonDetailActivty extends Activity {
	private static final String TAG = "PersonDetailActivty";
	private PersonHomePage mPersonHomePage;
	private PersonWeiboPage mPersonWeiboPage;
	private PersonPhotoPage mPersonPhotoPage;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_detail);
		getActionBar().hide();
		setupFragmentTab();
	}

	private void setupFragmentTab() {
		findViewById(R.id.fragment_first_page_tab).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mPersonHomePage == null) {
							mPersonHomePage = new PersonHomePage();
							PersonDetailActivty.this
									.getFragmentManager()
									.beginTransaction()
									.add(R.id.framelayout_person,
											mPersonHomePage).commit();
						} else {
							PersonDetailActivty.this.getFragmentManager()
									.beginTransaction().attach(mPersonHomePage)
									.commit();
							
						}
						
						if(mPersonWeiboPage != null){
							PersonDetailActivty.this.getFragmentManager()
							.beginTransaction().detach(mPersonWeiboPage).commit();
						}
						
						if(mPersonPhotoPage != null){
							PersonDetailActivty.this.getFragmentManager()
							.beginTransaction().detach(mPersonPhotoPage).commit();
						}
					}
				});

		findViewById(R.id.fragment_weibo_tab).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mPersonWeiboPage == null) {
							mPersonWeiboPage = new PersonWeiboPage();
							PersonDetailActivty.this
									.getFragmentManager()
									.beginTransaction()
									.add(R.id.framelayout_person,
											mPersonWeiboPage).commit();
						} else {
							PersonDetailActivty.this.getFragmentManager()
									.beginTransaction()
									.attach(mPersonWeiboPage).commit();

						}
						
						if(mPersonHomePage != null){
							PersonDetailActivty.this.getFragmentManager()
							.beginTransaction().detach(mPersonHomePage).commit();
						}
						
						if(mPersonPhotoPage != null){
							PersonDetailActivty.this.getFragmentManager()
							.beginTransaction().detach(mPersonPhotoPage).commit();
						}
					}
				});

		findViewById(R.id.fragment_photo_tab).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mPersonPhotoPage == null) {
							mPersonPhotoPage = new PersonPhotoPage();
							PersonDetailActivty.this
									.getFragmentManager()
									.beginTransaction()
									.add(R.id.framelayout_person,
											mPersonPhotoPage).commit();
						} else {
							PersonDetailActivty.this.getFragmentManager()
									.beginTransaction()
									.attach(mPersonPhotoPage).commit();

						}
						
						if(mPersonHomePage != null){
							PersonDetailActivty.this.getFragmentManager()
							.beginTransaction().detach(mPersonHomePage).commit();
						}
						
						if(mPersonWeiboPage != null){
							PersonDetailActivty.this.getFragmentManager()
							.beginTransaction().detach(mPersonWeiboPage).commit();
						}
					}
				});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
