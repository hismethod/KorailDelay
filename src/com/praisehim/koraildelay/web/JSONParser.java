package com.praisehim.koraildelay.web;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.praisehim.koraildelay.TrainInfo;

public class JSONParser
{
	private final String OK_MESSAGE = "IRG000000";
	private final String NK_MESSAGE = "WRG000000";
	
	private final String VERIFY_KEY = "h_msg_cd";
	private final String ARRSIZE_KEY = "h_rslt_cnt";
//	private final String DIRECT_ROOT = "h_chg_trn_dv_nm";		//직통, 환승
//	private final String DEP_CODE = "h_dpt_rs_stn_cd";			//승차역코드 abcd
//	private final String DEP_NAME = "h_dpt_rs_stn_nm";			//승차역이름
//	private final String ARR_CODE = "h_arv_rs_stn_cd";			//하차역코드
//	private final String ARR_NAME = "h_arv_rs_stn_nm";			//하차역이름
//	private final String TRAIN_NO = "h_trn_no";					//기차번호
//	private final String DEP_DATE = "h_dpt_dt";					//승차날짜 yyyymmdd
//	private final String ARR_DATE = "h_arv_dt";					//하차날짜
//	private final String DEP_TIME = "h_dpt_tm_qb";				//승차시간 hh:mm
//	private final String ARR_TIME = "h_arv_tm_qb";				//하차시간
//	private final String DELAY_TIME = "h_expct_dlay_hr";		//지연시간 00xy
//	private final String TICKET_STATUS = "h_gen_rsv_nm";		//일반석 역발매중:매진
//	private final String TICKET_FREE_STATUS = "h_rsv_psb_nm";	//입석	 입석\n역발매중:매진
//	private final String TRAIN_TYPE = "h_trn_clsf_nm";			//기차종류 ITX-새마을
	
	public ArrayList<TrainInfo> parse(String jsonArrayStr)
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
				train.setDelayTime(delayTime);
				train.setTicketStatus(ticketStatus);
				train.setTicketFreeStatus(ticketFreeStatus);
				
				trainList.add(train);
			}
		}
		catch (JSONException e){e.printStackTrace();}
		
		return trainList;
	}
}
