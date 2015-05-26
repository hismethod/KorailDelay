package com.praisehim.koraildelay.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.praisehim.koraildelay.TrainInfo;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class WebExtract
{
	public static final int MAX_TRAIN_COUNT = 10;
	public static final String SHES_GONE = "������ ���������ϴ�";
	
	public static ArrayList<TrainInfo> searchTrainInfo(String depDate, String depTime, String depName, String arrName,
			String trainType)
	{
		String trainJsonArrayStr = getTrainJSONArray(depDate, depTime, depName, arrName, trainType);
		//String trainHTML = getTrainHTML(depDate, depTime, depName, arrName, trainType);
		ArrayList<TrainInfo> trainInfo = getTrainInfo(trainJsonArrayStr);
		
		return trainInfo;
	}

	
	/**
	 * @param date : yyyyMMdd ���� ��¥
	 * @param trainNumber : �˻��� ������ȣ
	 * @return	html�ҽ��ڵ� ���ڿ�
	 */
	public static String getDelayHTML(String date, String trainNumber)
	{
		StringBuilder html = null;
		try	{ // Construct data
		
		StringBuilder data = new StringBuilder("ops_dd=");
		data.append(date);
		data.append("&trn_no=");
		data.append(trainNumber);
		
		// Send data
		URL url = new URL("http://m.logis.korail.com/ft_smarttrainmaininfo.do");
		URLConnection conn = url.openConnection();
		// If you invoke the method setDoOutput(true) on the URLConnection,
		// it will always use the POST method.
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data.toString());
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		rd.skip(17500);
		
		html = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null)
		{
			html.append(line);
			html.append("\n");
		}
		wr.close();
		rd.close();

		} catch (Exception e){}
		
		return html.toString();
	}
	

	private static String getTrainHTML(String depDate, String depTime, String depCode, String arrCode,
			String trainType)
	{
		StringBuilder html = null;
		try	{ // Construct data
		
		StringBuilder data = new StringBuilder("txtGoAbrdDt=");
		data.append(depDate);
		data.append("&txtGoHour=");			data.append(depTime);
		data.append("&txtGoStartCode=");	data.append(depCode);
		data.append("&txtGoEndCode=");		data.append(arrCode);
		data.append("&selGoTrain=");		data.append(trainType);
		data.append("&checkStnNm=N&radJobId=1&txtPsgCnt1=1");
		
		// Send data
//		URL url = new URL("http://www.korail.com/servlets/pr.pr21100.sw_pr21111_i1Svt");
		URL url = new URL("http://www.letskorail.com/ebizprd/EbizPrdTicketPr21111_i1.do");
		URLConnection conn = url.openConnection();
		// If you invoke the method setDoOutput(true) on the URLConnection,
		// it will always use the POST method.
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data.toString());
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

		html = new StringBuilder();
		String line;
		rd.skip(109308);
		while ((line = rd.readLine()) != null)
		{
			html.append(line);
			html.append("\n");
		}
		wr.close();
		rd.close();

		} catch (Exception e){}
		
		return html.toString();
	}
	
	/**
	 * @param delayInfoHTMLurl : ���ڿ����� HTML�ּ�
	 * @return : ������ data�迭(����, �����ð�)
	 */
	public static String[] getDelayInfo(String delayInfoHTML)
	{
		Source source = new Source(delayInfoHTML);

		// ã�� �� �ִ� Ű Ŭ�������� "txt_fs24b_da" �̴�.
		List<Element> subSet = source.getAllElementsByClass("txt_fs24b_da");

		String status = subSet.get(2).getContent().toString().trim();
		String delayTime = subSet.get(5).getContent().toString().trim();
		
		return new String[]{status, delayTime};
	}
	
	
	/**
	 * @param delayInfoHTML : ���ڿ����� HTML
	 * @return : ��������
	 */
	static String getDelayStatus(String delayInfoHTML)
	{
		String firstKey = "x;color:#FFFFFF;letter-spacing:-2px; text-align:center;\">";
		String lastKey = "x;color:#FFFFFF;letter-spacing:-2px; text-align:center;\">|</td>";

		Scanner parser = new Scanner(delayInfoHTML);

		parser.useDelimiter(firstKey);
		parser.next();
		parser.useDelimiter(lastKey);
		String status = parser.next().trim();
		
		parser.close();

		return status;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getTrainJSONArray(String depDate, String depTime, String depName, String arrName,
			String trainType)
	{
		StringBuilder jsonArrayStr = null;
		try	{ // Construct data
		
		//&version=140806001
		String mainUrl = "https://smart.letskorail.com/classes/com.korail.mobile.seatMovie.ScheduleView?Device=AD&Version=140801001";
		StringBuilder data = new StringBuilder("&txtGoAbrdDt=");
		data.append(depDate);
		data.append("&txtGoHour=");			data.append(depTime);
		data.append("&txtGoStart=");	data.append(URLEncoder.encode(depName, "utf-8"));
		data.append("&txtGoEnd=");		data.append(URLEncoder.encode(arrName, "utf-8"));
		data.append("&selGoTrain=");		data.append(trainType);
		data.append("&checkStnNm=N&radJobId=1&txtPsgFlg_1=1");
		
		System.out.println(mainUrl + data);
		
		URL url = new URL(mainUrl + data);
		URLConnection conn = url.openConnection();

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

		jsonArrayStr = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null)
		{
			jsonArrayStr.append(line);
		}
		rd.close();

		} catch (Exception e){}
		
		return jsonArrayStr.toString();
	}
	
	/**
	 * GetTrainInfo �޼��� ���� �߿��� �Լ�!!!!
	 * ���⼭ ��� ������ ������ �� ���´�.
	 * �׷��� �Լ��� �� ���.
	 * 
	 * @param trainHTML : ������������ html String
	 * @param date : ������߳�¥
	 * @return �������������� ArrayList
	 */
	private static final String OK_MESSAGE = "IRG000000";
	private static final String NK_MESSAGE = "WRG000000";
	
	private static final String VERIFY_KEY = "h_msg_cd";
	private static final String ARRSIZE_KEY = "h_rslt_cnt";
//	private final String DIRECT_ROOT = "h_chg_trn_dv_nm";		//����, ȯ��
//	private final String DEP_CODE = "h_dpt_rs_stn_cd";			//�������ڵ� abcd
//	private final String DEP_NAME = "h_dpt_rs_stn_nm";			//�������̸�
//	private final String ARR_CODE = "h_arv_rs_stn_cd";			//�������ڵ�
//	private final String ARR_NAME = "h_arv_rs_stn_nm";			//�������̸�
//	private final String TRAIN_NO = "h_trn_no";					//������ȣ
//	private final String DEP_DATE = "h_dpt_dt";					//������¥ yyyymmdd
//	private final String ARR_DATE = "h_arv_dt";					//������¥
//	private final String DEP_TIME = "h_dpt_tm_qb";				//�����ð� hh:mm
//	private final String ARR_TIME = "h_arv_tm_qb";				//�����ð�
//	private final String DELAY_TIME = "h_expct_dlay_hr";		//�����ð� 00xy
//	private final String TICKET_STATUS = "h_gen_rsv_nm";		//�Ϲݼ� ���߸���:����
//	private final String TICKET_FREE_STATUS = "h_rsv_psb_nm";	//�Լ�	 �Լ�\n���߸���:����
//	private final String TRAIN_TYPE = "h_trn_clsf_nm";			//�������� ITX-������
	
	/*
	
	private static ArrayList<TrainInfo> getTrainInfo(String trainHTML, String date)
	{
		Source source = new Source(trainHTML);
		ArrayList<TrainInfo> trainInfo = new ArrayList<TrainInfo>();
		List<Element> trSet = source.getAllElements("tr");
		
		try{
		for (Element tr : trSet)
		{
			TrainInfo info = new TrainInfo();
			List<Element> subSet = tr.getAllElements("td");
	
			String way = subSet.get(0).getContent().toString().trim();
			String type = subSet.get(1).getAttributeValue("title");
			if(type.equals("")) type = "������-ITX";
			String trainNo = subSet.get(1).getTextExtractor().toString();
			String start = subSet.get(2).getTextExtractor().toString();
			String end = subSet.get(3).getTextExtractor().toString();
			String spendTime = subSet.get(11).getContent().toString().trim();
			String[] delayInfo = getDelayInfo(getDelayHTML(date, trainNo));
			int delay = Integer.valueOf(delayInfo[1].replace("��", ""));
			String[] estimateDepInfo = calEstimateTime(date, start.split(" ")[1], delay);
			String[] estimateArrInfo = calEstimateTime(date, end.split(" ")[1], delay);

			info.setDepDate(date);
			info.setWay(way);
			info.setType(type);
			info.setTrainNo(trainNo);
			info.setDepName(start.split(" ")[0]);
			info.setDepTime(start.split(" ")[1]);
			info.setArrName(end.split(" ")[0]);
			info.setArrTime(end.split(" ")[1]);
			info.setSpendTime(spendTime);
			info.setStatus(delayInfo[0]);
			info.setDelayTime(delayInfo[1]);
			info.setEstimateDepTime(estimateDepInfo[0]);
			info.setEstimateArrTime(estimateArrInfo[0]);
			info.setRemainTime(estimateDepInfo[1]);
			info.setIcon(type);
			
			trainInfo.add(info);	
		}}catch(IndexOutOfBoundsException e){}

		return trainInfo;	
	}
	
	*/
	
	
	public static ArrayList<TrainInfo> getTrainInfo(String jsonArrayStr)
	{
		ArrayList<TrainInfo> trainList = null;
		try
		{
			JSONObject response = new JSONObject(jsonArrayStr);
			
			if(response.get(VERIFY_KEY).equals(OK_MESSAGE) ==  false)
				return null;

			int arrSize = Integer.valueOf((String) response.get(ARRSIZE_KEY));

			JSONObject trn_infos = new JSONObject(response.get("trn_infos").toString());
			JSONArray jsonTrainList = new JSONArray(trn_infos.get("trn_info").toString());
			
			trainList = new ArrayList<TrainInfo>();
			
			for(int i=0; i<arrSize; i++)
			{
				JSONObject jsonTrain = jsonTrainList.getJSONObject(i);
				TrainInfo train = new TrainInfo();
				
				String way = jsonTrain.getString("h_chg_trn_dv_nm");
				String depCode = jsonTrain.getString("h_dpt_rs_stn_cd");
				String depName = jsonTrain.getString("h_dpt_rs_stn_nm");
				String arrCode = jsonTrain.getString("h_arv_rs_stn_cd");
				String arrName = jsonTrain.getString("h_arv_rs_stn_nm");
				String trainNo = jsonTrain.getString("h_trn_no");
				String depDate = jsonTrain.getString("h_dpt_dt");
				String arrDate = jsonTrain.getString("h_arv_dt");
				String depTime = jsonTrain.getString("h_dpt_tm_qb");
				String arrTime = jsonTrain.getString("h_arv_tm_qb");
				String delayTime = jsonTrain.getString("h_expct_dlay_hr");
				String ticketStatus = jsonTrain.getString("h_gen_rsv_nm");
				String ticketFreeStatus = jsonTrain.getString("h_rsv_psb_nm");
				String trainType = jsonTrain.getString("h_trn_clsf_nm");
				
				int delay = Integer.valueOf(delayTime);
				String[] delayInfo = {"", ""};// = getDelayInfo(getDelayHTML(depDate, trainNo));		//�������, �����ð�
				delayInfo[0] = "";
				delayInfo[1] = delay + "��";
				
				String[] estimateDepInfo = calEstimateTime(depDate, depTime, delay);
				String[] estimateArrInfo = calEstimateTime(depDate, arrTime, delay);
				
				train.setWay(way);
				train.setDepDate(depDate);
				train.setDepName(depName);
				train.setDepTime(depTime);
				train.setDepCode(depCode);
				train.setArrDate(arrDate);
				train.setArrName(arrName);
				train.setArrTime(arrTime);
				train.setArrCode(arrCode);
				train.setTrainNo(trainNo);
				train.setType(trainType);
				train.setDelayTime(delayInfo[1]);
				train.setTicketStatus(ticketStatus);
				train.setTicketFreeStatus(ticketFreeStatus);
				
				train.setStatus(delayInfo[0]);
				train.setEstimateDepTime(estimateDepInfo[0]);
				train.setEstimateArrTime(estimateArrInfo[0]);
				train.setRemainTime(estimateDepInfo[1]);
				train.setIcon(trainType);
				
				trainList.add(train);
			}
		}
		catch (JSONException e){e.printStackTrace();}
		
		return trainList;
	}
	

	/**
	 * 
	 * @param depDate - yyyymmdd���� ���ó�¥
	 * @param time - HH:MM���� ���������ð�
	 * @param delay - m�� ���� ���������ð�
	 * @return HH:MM���� �������������ð�, x�� y�ð� z�� ���� �����ð�
	 */
	public static String[] calEstimateTime(String depDate, String time, int delay)
	{
		final int ONE_MINUTE = 1000 * 60;
		final int ONE_HOUR = ONE_MINUTE * 60;
		final int ONE_DAY = ONE_HOUR * 24;
		
		StringBuilder remainTime = new StringBuilder();
		String estimateTime = null;
		String originTime[] = time.split(":");

		int delayTime = delay;
		int originHour = Integer.valueOf(originTime[0]);
		int originMin = Integer.valueOf(originTime[1]);
		
		int year = Integer.valueOf(depDate.substring(0,4));
		int month = Integer.valueOf(depDate.substring(4,6));
		int day = Integer.valueOf(depDate.substring(6));

		Calendar cur = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day);
		cal.set(Calendar.HOUR_OF_DAY, originHour);
		cal.set(Calendar.MINUTE, originMin + delayTime);

		long currentTime = cur.getTimeInMillis();
		long elapsedTime = cal.getTimeInMillis() - currentTime;
		
		if(elapsedTime > ONE_DAY)
		{
			long d = elapsedTime / (ONE_DAY);
			System.out.println("d = " + d);
			remainTime.append(d);
			remainTime.append("�� ");
			
			elapsedTime -= d * ONE_DAY;
		}
		if(elapsedTime > ONE_HOUR)
		{
			long h = elapsedTime / (ONE_HOUR);
			System.out.println("h = " + h);
			remainTime.append(h);
			remainTime.append("�ð� ");
			
			elapsedTime -= h * ONE_HOUR;
		}
		if(elapsedTime >= 0)
		{
			long m = elapsedTime / ONE_MINUTE;
			remainTime.append(m);
			remainTime.append("��");	
		}
		else
		{
			remainTime.append(SHES_GONE);	
		}
		
		estimateTime = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		
		return new String[]{estimateTime, remainTime.toString()};
	}

	private static boolean isNumeric(String s)
	{ 
		return s.matches("\\d+");
	}
}
