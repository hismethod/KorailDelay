package com.praisehim.koraildelay;

import java.io.Serializable;

import android.view.View;

public class TrainInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8661910820468603966L;
	private String type = "";
	private String way  = "";
	private String depDate = "";
	private String depCode = "";
	private String depName = "";
	private String depTime = "";
	private String arrDate = "";
	private String arrCode = "";
	private String arrName = "";
	private String arrTime = "";
	private String trainNo = "";
	private String status = "";
	private int delayTime = 0;
	private String spendTime = "";
	private String ticketStatus = "";
	private String ticketFreeStatus = "";
	private String estimateDepTime = "";
	private String estimateArrTime = "";
	private String remainTime = "";
	private int icon;
	public boolean isOpen = false;
	
	public TrainInfo(){};
	public TrainInfo(String depName, String arrName)
	{
		this.depName = depName;
		this.arrName = arrName;
	}
	
	public TrainInfo(String type, String depName, String depTime, String arrName, String arrTime)
	{
		this.type = type;
		this.depName = depName;
		this.depTime = depTime;
		this.arrName = arrName;
		this.arrTime = arrTime;
	}
	
	public TrainInfo(String date, String type, String depName, String depTime, String arrName, String arrTime, String trainNo)
	{
		this.type = type;
		this.depDate = date;
		this.depName = depName;
		this.depTime = depTime;
		this.arrName = arrName;
		this.arrTime = arrTime;
		this.trainNo = trainNo;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("�������� : "); sb.append(type); sb.append("\n");
		sb.append("������ȣ : "); sb.append(trainNo); sb.append("\n");
//		sb.append("ȯ�¿��� : "); sb.append(way); sb.append("\n");
		sb.append("��߿��� : "); sb.append(depName); sb.append("\n");
		sb.append("������ġ : "); sb.append(status); sb.append("\n");
		sb.append("�������� : "); sb.append(arrName); sb.append("\n");
		sb.append("��߽ð� : "); sb.append(depTime); sb.append("\n");
		sb.append("������� : "); sb.append(estimateDepTime); sb.append("\n");
		sb.append("�����ð� : "); sb.append(arrTime); sb.append("\n");
		sb.append("�����ð� : "); sb.append(delayTime); sb.append("\n");
		sb.append("�����ð� : "); sb.append(remainTime); sb.append("\n");
		sb.append("\n");
		
		return sb.toString();
	}
	
	public String getType(){return type;}
	public String getWay(){return way;}
	public String getDepName(){return depName;}
	public String getDepTime(){return depTime;}
	public String getDepCode(){return depCode;}
	public String getArrName(){return arrName;}
	public String getArrTime(){return arrTime;}
	public String getArrCode(){return arrCode;}
	public String getTrainNo(){return trainNo;}
	public String getStatus(){return status;}
	public int getDelayTime(){return delayTime;}
	public String getSpendTime(){return spendTime;}
	public String getDepDate(){return depDate;}
	public String getArrDate(){return arrDate;}
	public String getTicketStatus(){return ticketStatus;}
	public String getTicketFreeStatus(){return ticketFreeStatus;}
	public String getEstimateDepTime(){return estimateDepTime;}
	public String getEstimateArrTime(){return estimateArrTime;}
	public String getRemainTime(){return remainTime;}
	public int getIcon(){return icon;}

	public void setType(String type){this.type = type;}
	public void setWay(String way){this.way = way;}
	public void setDepName(String depName){this.depName = depName;}
	public void setDepTime(String depTime){this.depTime = depTime;}
	public void setDepCode(String depCode){this.depCode = depCode;}
	public void setArrName(String arrName){this.arrName = arrName;}
	public void setArrTime(String arrTime){this.arrTime = arrTime;}
	public void setArrCode(String arrCode){this.arrCode = arrCode;}
	public void setTrainNo(String trainNo){this.trainNo = trainNo;}
	public void setStatus(String status){this.status = status;}
	public void setDepDate(String depDate){this.depDate = depDate;}
	public void setArrDate(String arrDate){this.arrDate = arrDate;}
	public void setTicketStatus(String ticketStatus){this.ticketStatus = ticketStatus;}
	public void setTicketFreeStatus(String ticketFreeStatus){this.ticketFreeStatus = ticketFreeStatus;}
	public void setDelayTime(String delayTime)
	{
		int delay = Integer.valueOf(delayTime.replace("��", ""));
		this.delayTime = delay;
	}
	public void setSpendTime(String spendTime){this.spendTime = spendTime;}
	public void setEstimateDepTime(String estimateDepTime){this.estimateDepTime = estimateDepTime;}
	public void setEstimateArrTime(String estimateArrTime){this.estimateArrTime = estimateArrTime;}
	public void setRemainTime(String remainTime){this.remainTime = remainTime;}
	public static int getIconId(String trainType)
	{
		if(trainType.equals("����ȭȣ"))
			return R.drawable.mu;
		else if(trainType.equals("������ȣ"))
			return R.drawable.sae;
		else if(trainType.equals("KTX"))
			return R.drawable.ktx;
		else if(trainType.equals("ITX-������"))
			return R.drawable.itx;
		else if(trainType.equals("������"))
			return R.drawable.nu;
		else if(trainType.equals("KTX-��õ"))
			return R.drawable.san;
		else
			return R.drawable.etc;
	}
	public void setIcon(String trainType)
	{
		if(trainType.equals("����ȭȣ"))
			icon = R.drawable.mu;
		else if(trainType.equals("������ȣ"))
			icon = R.drawable.sae;
		else if(trainType.equals("KTX"))
			icon = R.drawable.ktx;
		else if(trainType.equals("ITX-������"))
			icon = R.drawable.itx;
		else if(trainType.equals("������"))
			icon = R.drawable.nu;
		else if(trainType.equals("KTX-��õ"))
			icon = R.drawable.san;
		else
			icon = R.drawable.etc;
	}
}
