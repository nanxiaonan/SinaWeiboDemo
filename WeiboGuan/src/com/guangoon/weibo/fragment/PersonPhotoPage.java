package com.guangoon.weibo.fragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.guangoon.weibo.AccessTokenKeeper;
import com.guangoon.weibo.R;
import com.guangoon.weibo.loader.WeiboLoader;
import com.guangoon.weibo.models.ModelUtil;
import com.guangoon.weibo.models.WeiboInfo;
import com.guangoon.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class PersonPhotoPage extends Fragment implements
		LoaderCallbacks<List<WeiboInfo>> {
	private static final String TAG = "PersonPhotoPage";
	private Oauth2AccessToken mAccessToken;
	private ImageAdapter mImageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG,"==============onCreate============");
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		getLoaderManager().initLoader(1, null, this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mImageAdapter = new ImageAdapter();
		View view = inflater.inflate(R.layout.fragment_person_photo_page, null);
		GridView gridView = (GridView) view.findViewById(R.id.photo_grid);
		gridView.setAdapter(mImageAdapter);
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public Loader<List<WeiboInfo>> onCreateLoader(int id, Bundle args) {
		Log.i(TAG, "onCreateLoader");
		if (id == 1) {
			WeiboParameters params = new WeiboParameters();
			params.put("access_token", mAccessToken.getToken());
			params.put("feature", 2);
			params.put("trim_user", 1);
			params.put("count", 100);
			return new WeiboLoader(getActivity(), params);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<List<WeiboInfo>> loader,
			List<WeiboInfo> data) {
		Log.i(TAG, "onLoadFinished");
		Thread.dumpStack();
		List<String> mThumbnalUrls = new ArrayList<String>();
		for (WeiboInfo weiboInfo : data) {
			mThumbnalUrls.addAll(ModelUtil.getBmiddle_pic(weiboInfo));
		}
		mImageAdapter.addUris(mThumbnalUrls);
		Log.i(TAG, "mImageAdapter.Size()====" + mImageAdapter.getCount());
	}

	@Override
	public void onLoaderReset(Loader<List<WeiboInfo>> loader) {
		Log.i(TAG, "onLoaderReset");
	}

	private class ImageAdapter extends BaseAdapter {
		private List<String> mImageUris = new ArrayList<String>();
		private LruCache<String, Bitmap> mImageCaches;
		public ImageAdapter(){
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
			final int cacheSize = maxMemory / 8;
			Log.i(TAG,"cacheSize===" + cacheSize);
			mImageCaches = new LruCache<String, Bitmap>(cacheSize){

				@Override
				protected int sizeOf(String key, Bitmap value) {
					return value.getByteCount()/1024;
				}
				
			};
		}
		
		public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		    if (getBitmapFromMemCache(key) == null) {
		    	mImageCaches.put(key, bitmap);
		    }
		}
		
		public Bitmap getBitmapFromMemCache(String key) {
		    return mImageCaches.get(key);
		}
		@Override
		public int getCount() {
			return mImageUris.size();
		}

		public void addUris(List<String> uris) {
			mImageUris.addAll(uris);
			notifyDataSetChanged();
		}

		@Override
		public String getItem(int position) {
			return mImageUris.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i(TAG,"getView=======position==" + position);
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.weibo_photo_item, null);
			}
			ImageView imageView = (ImageView)convertView.findViewById(R.id.photo_item);
			imageView.setBackgroundResource(R.drawable.compose_pic_background);
			Bitmap bitmap = getBitmapFromMemCache(getItem(position));
			if(bitmap != null){
				imageView.setImageBitmap(bitmap);
			}else{
				new ImageAsyncTask(imageView).execute(getItem(position));
			}
			return convertView;
		}
	}

	private class ImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
		private WeakReference<ImageView> mImageWeakReference = null;

		public ImageAsyncTask(ImageView imageView) {
			mImageWeakReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if(mImageWeakReference.get() != null)
			{
				mImageWeakReference.get().setImageBitmap(result);
			}
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap  = decodeBitmapFromUri(params[0], 120, 120);
			mImageAdapter.addBitmapToMemoryCache(params[0], bitmap);
			return bitmap;
		}
	}

	public static Bitmap decodeBitmapFromUri(String urls, int reqWidth,
			int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		URL url;
		try {
			url = new URL(urls);
			BitmapFactory.decodeStream(url.openStream(), null, options);
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
			//options.
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeStream(url.openStream(), null, options);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
}
