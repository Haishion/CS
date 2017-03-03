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
		GHResponse shortestDistanceRes=null;
		String selectedStation=null;
		
		String[] closestTwo = {"",""};
		int[] closestTwoDistance = {9999,9999}; 
		for (String station : Simulator.getStations().keySet()) {
//			find the 2 closest stations
			Point des = Simulator.getStations().get(station).getPoint();
			int y2 = (int) des.getY();
			int x2 = (int) des.getX();
			int x1 = (int) generationPoint.getX();
			int y1 = (int) generationPoint.getY();
			int distance = Math.abs(x1-x2) + Math.abs(y1-y2); 

			if (distance <= closestTwoDistance[0]){

				closestTwoDistance[1] = closestTwoDistance[0];
				closestTwo[1] = closestTwo[0];
				closestTwoDistance[0] = distance;
				closestTwo[0] = station;
			} else if (distance <= closestTwoDistance[1]) {

				closestTwoDistance[1] = distance;
				closestTwo[1] = station;
			}
		}
		
//		int tries = 0;
		for (String myVal : Simulator.getStations().keySet()) {
			// for the two closest stations only
			if (myVal == closestTwo[0] || myVal == closestTwo[1]){
//				tries++;
//				System.out.println("i run through this part " + tries + " times!"); // somehow the result is always only 1 time =((
//				System.out.println(myVal);
				GHResponse res=super.getInformation(generationPoint, myVal);
				if(res==null)
				{
					
				}
				else if(shortestDistanceRes==null)
				{
					shortestDistanceRes=res;
					selectedStation=myVal;
					continue;
				}
				else
				{
					if(res.getDistance()<shortestDistanceRes.getDistance())
					{
						shortestDistanceRes=res;
						selectedStation=myVal;
					}
				}

			}

		}
		return selectedStation;		
	}
}
