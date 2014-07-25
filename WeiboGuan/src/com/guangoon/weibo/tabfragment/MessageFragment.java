package com.guangoon.weibo.tabfragment;

import java.util.ArrayList;
import java.util.List;

import com.guangoon.weibo.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageFragment extends ListFragment {
	private MessageAdapter mMessageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMessageAdapter = new MessageAdapter();
		mMessageAdapter.addItem(new MessageItem(R.drawable.messagescenter_at,
				getString(R.string.at_me)));
		mMessageAdapter
				.addItem(new MessageItem(R.drawable.messagescenter_comments,
						getString(R.string.comment)));
		mMessageAdapter.addItem(new MessageItem(R.drawable.messagescenter_good,
				getString(R.string.praise)));
		mMessageAdapter.addItem(new MessageItem("ahh", "新浪新闻", "央视主播王梁改名为送别西",
				R.drawable.message_news));
		mMessageAdapter.addItem(new MessageItem("ahh", "围观世界杯", "中国加油",
				R.drawable.message_football));
		mMessageAdapter
		.addItem(new MessageItem(R.drawable.messagescenter_messagebox,
				getString(R.string.no_attention_private_message)));
		setListAdapter(mMessageAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_message, null);
	}

	private class MessageAdapter extends BaseAdapter {
		private List<MessageItem> mList = new ArrayList<MessageItem>();

		@Override
		public int getCount() {
			return mList.size();
		}

		public boolean addItem(MessageItem item) {
			return mList.add(item);
		}

		@Override
		public MessageItem getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				if (getItem(position).getType() == MessageItem.MESSAGE_ITEM_TYPE_LOCAL) {
					convertView = View.inflate(getActivity(),
							R.layout.message_item_local, null);
				} else {
					convertView = View.inflate(getActivity(),
							R.layout.message_item_remote, null);
				}
			}

			if (getItem(position).getType() == MessageItem.MESSAGE_ITEM_TYPE_LOCAL) {
				TextView title = (TextView) convertView
						.findViewById(R.id.local_message_title);
				ImageView icon = (ImageView) convertView
						.findViewById(R.id.local_message_icon);
				title.setText(getItem(position).getTitle());
				icon.setImageResource(getItem(position).getIconResId());
			} else {
				TextView title = (TextView) convertView
						.findViewById(R.id.remote_message_title);
				TextView message = (TextView) convertView
						.findViewById(R.id.remote_message);
				ImageView icon = (ImageView) convertView
						.findViewById(R.id.remote_message_icon);
				title.setText(getItem(position).getTitle());
				message.setText(getItem(position).getMessage());
				icon.setImageResource(getItem(position).getIconResId());

			}

			return convertView;
		}

	}

	public class MessageItem {
		public static final int MESSAGE_ITEM_TYPE_LOCAL = 1;
		public static final int MESSAGE_ITEM_TYPE_REMOTE = 2;

		private int mType;
		private String mTitle;
		private String mMessage;
		private int mIconResId;
		public String mIconUri;

		public MessageItem(int iconId, String title) {

			mType = MESSAGE_ITEM_TYPE_LOCAL;
			mTitle = title;
			mIconResId = iconId;
		}

		public MessageItem(String iconUri, String title, String message,
				int iconId) {
			mType = MESSAGE_ITEM_TYPE_REMOTE;
			mIconUri = iconUri;
			mTitle = title;
			mMessage = message;
			mIconResId = iconId;
		}

		public String getTitle() {
			return mTitle;
		}

		public String getMessage() {
			return mMessage;
		}

		public int getIconResId() {
			return mIconResId;
		}

		public String getIconUri() {
			return mIconUri;
		}

		public int getType() {
			return mType;

		}

	}

}
