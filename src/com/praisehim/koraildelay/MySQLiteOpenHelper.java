package com.praisehim.koraildelay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "Korail_Delay.db";
	private static final String PACKAGE_DIR = "/data/data/com.praisehim.koraildelay/databases";

	public MySQLiteOpenHelper(Context context)
	{
		super(context, PACKAGE_DIR + "/" + DATABASE_NAME, null, 1);
		initialize(context);
	}

	public static void initialize(Context ctx)
	{
		File folder = new File(PACKAGE_DIR);
		folder.mkdirs();

		File outfile = new File(PACKAGE_DIR + "/" + DATABASE_NAME);
		System.out.println(PACKAGE_DIR + "/" + DATABASE_NAME);
		if (outfile.length() <= 0)
		{
			AssetManager assetManager = ctx.getResources().getAssets();
			try
			{
				InputStream is = assetManager.open(DATABASE_NAME,
						AssetManager.ACCESS_BUFFER);
				long filesize = is.available();
				byte[] tempdata = new byte[(int) filesize];
				is.read(tempdata);
				is.close();
				outfile.createNewFile();
				FileOutputStream fo = new FileOutputStream(outfile);
				fo.write(tempdata);
				fo.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase arg0)
	{
		// TODO Auto-generated method stub
		System.out.println("¼³¸¶?");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{
		// TODO Auto-generated method stub

	}
}