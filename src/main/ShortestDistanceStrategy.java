package main;

import java.awt.Point;

import com.graphhopper.GHResponse;

import mainAPI.Simulator;
import mainAPI.Strategy;

public class ShortestDistanceStrategy extends Strategy {
	
	@Override
	public int setChargingTime(Point generationPoint) {
		// TODO Auto-generated method stub
		return 30;
	}

	public String chooseDestination(Point generationPoint){
		String zoneID=super.getZoneID(generationPoint);
		int carNo=super.getCarNo(zoneID);//real time car number within the zone
//		System.out.println("Zone ID "+ super.getZoneID(generationPoint)+" Car No "+carNo);
		GHResponse shortestDistanceRes=null;
		String selectedStation=null;
//		String[] closestTwo = new String[2];
//		int[] closestTwoDistance = new int[2]; 
//		for (String station : Simulator.getStations().keySet()) {
////			find the 2 closest stations
//			Point des = Simulator.getStations().get(station).getPoint();
//			int y2 = (int) des.getY();
//			int x2 = (int) des.getX();
//			int x1 = (int) generationPoint.getX();
//			int y1 = (int) generationPoint.getY();
//			int distance = Math.abs(x1-x2) + Math.abs(y1-y2); 
//			if (closestTwoDistance == null){
//				
//			}
//		}
		for (String myVal : Simulator.getStations().keySet()) {
//			for the 2 closest stations
			GHResponse res=super.getInfomration(generationPoint, myVal);
			if(res==null)
			{
				
			}
			else if(shortestDistanceRes==null)
			{
				double averageCapacity=super.getRouteCapacity(res);
				shortestDistanceRes=res;
				selectedStation=myVal;
				continue;
			}
			else
			{
				double averageCapacity=super.getRouteCapacity(res);
//				System.out.println(averageCapacity);

				if(res.getDistance()<shortestDistanceRes.getDistance())
				{
					shortestDistanceRes=res;
					selectedStation=myVal;
				}
			}
		}
		return selectedStation;
		
	}

}
