package com.idunnolol.sotm.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.idunnolol.utils.Log;

public class PointSyncAdapter extends AbstractThreadedSyncAdapter {

	public PointSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
	}

	public PointSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider,
			SyncResult syncResult) {
		Log.i("TODO: Syncing SotM data from server...");

		// TODO Synchronize your data between client and server
	}

}
