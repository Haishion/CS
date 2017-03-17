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
		int waitingTime1 = 0;
		int waitingTime2 = 0;
		
		String[] closestThree = {"","",""};
		int[] closestThreeDistance = {9999,9999,9999}; 
		for (String station : Simulator.getStations().keySet()) {
//			find the 2 closest stations
			Point destination = Simulator.getStations().get(station).getPoint();
			int y2 = (int) destination.getY();
			int x2 = (int) destination.getX();
			int x1 = (int) generationPoint.getX();
			int y1 = (int) generationPoint.getY();
			int distance = Math.abs(x1-x2) + Math.abs(y1-y2); 

			if (distance <= closestThreeDistance[0]){

				closestThreeDistance[2] = closestThreeDistance[1];
				closestThree[2] = closestThree[1];
				closestThreeDistance[1] = closestThreeDistance[0];
				closestThree[1] = closestThree[0];
				closestThreeDistance[0] = distance;
				closestThree[0] = station;
			} else if (distance <= closestThreeDistance[1]) {
				closestThreeDistance[2] = closestThreeDistance[1];
				closestThree[2] = closestThree[1];
				closestThreeDistance[1] = distance;
				closestThree[1] = station;
			} else if (distance <= closestThreeDistance[2]) {

				closestThreeDistance[2] = distance;
				closestThree[2] = station;
			}
		}
		

		for (String myVal : Simulator.getStations().keySet()) {
			// for the two closest stations only
			if (myVal == closestThree[0] || myVal == closestThree[1] || myVal == closestThree[1]){
				
				GHResponse res=super.getInformation(generationPoint, myVal);
				if(res==null)
				{
					
				}
				else if(shortestDistanceRes==null)
				{
					waitingTime1 =  Simulator.getStations().get(myVal).getWaitingSize() * 15;
					
					shortestDistanceRes=res;
					selectedStation=myVal;
					continue;
				}
				else
				{
					waitingTime2 =  Simulator.getStations().get(myVal).getWaitingSize() * 15;
//					if(res.getTime()<shortestDistanceRes.getTime())
					if((res.getTime()/1000/60+waitingTime2)<(shortestDistanceRes.getTime()/1000/60+waitingTime1))
					{
//						System.out.println("Station: " + myVal);
//						System.out.println("change destination: "+ res.getTime()/1000/60 + waitingTime2 +"is less than " + shortestDistanceRes.getTime()/1000/60 + waitingTime1);
						shortestDistanceRes=res;
						selectedStation=myVal;
						waitingTime1 = waitingTime2;
					}
				}

			}
			
		}
		
		return selectedStation;		
	}
}
