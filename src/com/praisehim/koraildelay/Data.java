package com.praisehim.koraildelay;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class Data
{
	public static ArrayList<TrainInfo> TRAINLIST = new ArrayList<TrainInfo>();
	public static InterestObservableArray INTEREST_LIST;
	public static XYInformationProcess xyip;
	public static ArrayList<StationInfo> STATIONLIST = new ArrayList<StationInfo>();
	private static String userInputDate;
	private static String userInputTime;
	private static String userInputDep;// = "동대구";
	private static String userInputArr;// = "구미";
	private static String userInputType;
	
	public static String getUserInputDate(){return userInputDate;}
	public static String getUserInputTime(){return userInputTime;}
	public static String getUserInputDep(){return userInputDep;}
	public static String getUserInputArr(){return userInputArr;}
	public static String getUserInputType(){return userInputType;}
	public static void setUserInputDate(String userInputDate){Data.userInputDate = userInputDate;}
	public static void setUserInputTime(String userInputTime){Data.userInputTime = userInputTime;}
	public static void setUserInputDep(String userInputDep){Data.userInputDep = userInputDep;}
	public static void setUserInputArr(String userInputArr){Data.userInputArr = userInputArr;}
	public static void setUserInputType(String userInputType){Data.userInputType = userInputType;}

	public Data(){}
	
	static class PresentDate
	{
		public static PresentDate getInstance()
		{
			PresentDate now = new PresentDate();
			return now;
		}
		String fourTime;
		String eightDate;
		PresentDate()
		{
			Calendar calendar = Calendar.getInstance();
			eightDate = String.format("%d%02d%02d", calendar.get(Calendar.YEAR),
										calendar.get(Calendar.MONTH)+1,
										calendar.get(Calendar.DAY_OF_MONTH));
			fourTime = String.format("%02d%02d", calendar.get(Calendar.HOUR_OF_DAY),
												  calendar.get(Calendar.MINUTE));
		};
	}
	
	static class ShortCutKorailTalk
	{
		public static void goKorailTalk(Context context, PackageManager pm, String strAppPackage)
		{
	        PackageInfo pi;
	       
	        try
	        {
	            pi = pm.getPackageInfo(strAppPackage, PackageManager.GET_ACTIVITIES);
	            Intent intent = pm.getLaunchIntentForPackage("com.korail.korail");
	            context.startActivity(intent); 
	        }
	        catch (NameNotFoundException e)
	        {
	        	korailTalkInstallDialog(context);
	        }
	       
	    }
		
		public static void korailTalkInstallDialog(final Context context)
		{
			String alertTitle = "마켓이동확인";
			String buttonMessage = "현재 코레일톡어플이 없습니다. 마켓 페이지로 이동하시겠습니까?";
			String buttonYes = "네";// getResources().getString(R.string.button_yes);
			String buttonNo = "아니오";// getResources().getString(R.string.button_no);

			new AlertDialog.Builder(context)
					.setTitle(alertTitle)
					.setMessage(buttonMessage)
					.setPositiveButton(buttonYes, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							Intent intent = new Intent(Intent.ACTION_VIEW,
									Uri.parse("market://details?id=com.korail.korail"));
							context.startActivity(intent);
						}
					})
					.setNegativeButton(buttonNo, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{

						}
					}).show();
		}
	}
}
