package jcsla.korail;

public class Train {
	private String trainType;
	private String trainNumber;
	private String depCode;
	private String depDate;
	private String depTime;
	private String arrCode;
	private String arrDate;
	private String arrTime;
	private String trainStatus;
	private String trainDelayStatus;
	
	public Train(String trainType, String trainNumber, String depCode, String depDate, String depTime,
			String arrCode, String arrDate, String arrTime, String trainStatus, String trainDelayStatus) {
		setTrainType(trainType);
		setTrainNumber(trainNumber);
		setDepCode(depCode);
		setDepDate(depDate);
		setDepTime(depTime);
		setArrCode(arrCode);
		setArrDate(arrDate);
		setArrTime(arrTime);
		setTrainStatus(trainStatus);
		setTrainDelayStatus(trainDelayStatus);
	}
	
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public String getTrainType() {
		String result = "";
		
		if(trainType.compareTo("00") == 0 || trainType.compareTo("07") == 0)
			result = "KTX";
		else if(trainType.compareTo("01") == 0)
			result = "새마을호";
		else if(trainType.compareTo("02") == 0)
			result = "무궁화호";
		else if(trainType.compareTo("03") == 0)
			result = "통근열차";
		else if(trainType.compareTo("04") == 0)
			result = "누리로";
		else if(trainType.compareTo("06") == 0)
			result = "공항철도";
		else if(trainType.compareTo("08") == 0)
			result = "ITX-새마을";
		else if(trainType.compareTo("09") == 0)
			result = "ITX-청춘";
		
		return result.trim();
	}
	
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	public String getTrainNumber() {
		return trainNumber.trim();
	}
	
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public String getDepCode() {
		String result = null;
		
		result = Stations.number_name_stations.get(depCode);
		
		return result.trim();
	}
	
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}
	public String getDepDate() {
		return depDate;
	}
	
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getDepTime() {
		String result;
		
		String exceptSeconds = depTime.substring(0, 4);
		StringBuffer sb = new StringBuffer(exceptSeconds);
		sb.insert(2, ":");
		result = sb.toString();
		
		return result.trim();
	}
	
	public void setArrCode(String arrCode) {
		this.arrCode = arrCode;
	}
	public String getArrCode() {
		String result = null;
		
		result = Stations.number_name_stations.get(arrCode);
		
		return result.trim();
	}
	
	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}
	public String getArrDate() {
		return arrDate;
	}
	
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getArrTime() {
		String result;
		
		String exceptSeconds = arrTime.substring(0, 4);
		StringBuffer sb = new StringBuffer(exceptSeconds);
		sb.insert(2, ":");
		result = sb.toString();
		
		return result.trim();
	}
	
	public void setTrainStatus(String trainStatus) {
		this.trainStatus = trainStatus;
	}
	public String getTrainStatus() {
		return trainStatus.trim();
	}
	
	public void setTrainDelayStatus(String trainDelayStatus) {
		this.trainDelayStatus = trainDelayStatus;
	}
	public String getTrainDelayStatus() {
		return trainDelayStatus.trim();
	}
}
