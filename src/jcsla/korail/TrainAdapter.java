package jcsla.korail;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TrainAdapter extends ArrayAdapter<Train>
{
	Context context;

	public TrainAdapter(Context context, int resource, ArrayList<Train> objects)
	{
		super(context, resource, objects);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row, null);
		}
		Train t = TrainList.trainList.get(position);
		
		if (t != null)
		{
			String delayString = t.getTrainDelayStatus();
			
			TextView trainType = (TextView) v.findViewById(R.id.itemTrainType);
			TextView trainNumber = (TextView) v.findViewById(R.id.itemTrainNumber);
			TextView departureStation = (TextView) v.findViewById(R.id.itemDepStation);
			TextView departureTime = (TextView) v.findViewById(R.id.itemDepTime);
			TextView arrivalStation = (TextView) v.findViewById(R.id.itemArrStation);
			TextView arrivalTime = (TextView) v.findViewById(R.id.itemArrTime);
			TextView location = (TextView) v.findViewById(R.id.itemStatus);
			TextView delay = (TextView) v.findViewById(R.id.itemDelayTime);
			
			if(delayString.equals("0Ка"))
				 highlightView(delay, R.color.green);
			else highlightView(delay, R.color.orange);

			trainType.setText(t.getTrainType());
			trainNumber.setText(t.getTrainNumber());
			departureStation.setText(t.getDepCode());
			departureTime.setText(t.getDepTime());
			arrivalStation.setText(t.getArrCode());
			arrivalTime.setText(t.getArrTime());
			location.setText(t.getTrainStatus());
			delay.setText(delayString);
		}
		return v;
	}
	
	private void highlightView(TextView v, int colorID)
	{
		int color = v.getResources().getColor(colorID);
		v.setTextColor(color);
	}
}
