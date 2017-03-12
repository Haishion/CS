package mainAPI;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.map.HashedMap;
import org.geotools.data.FeatureReader;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.jxmapviewer.viewer.GeoPosition;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.graphhopper.GHResponse;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;


public class CarProductionLine {
	int lastGenerationTime;
	int producerSpeed;
	double latitude;
	double longitude;
	//Zones selected to randomly generate the car
	String[] zoneArray;

	//two types of generators
	int type;
	int noOfCarsGenerated;
	int expectedNo;
	int currentNo = 0;
	final int FIXED = 0;
	final int RANDOM = 1;
	final int RANDOMWITHSTRATEGY=2;
	Strategy strategy;
	int noWithinCircle;// when choose one pixel within a zone
	int[] stuff;
	ShapefileDataStore dataStore;
	

	public CarProductionLine(int producerSpeed, int noOfCarsGenerated, int expectedNo, double latitude,
			double longitude) {
		this.noOfCarsGenerated = noOfCarsGenerated;
		this.expectedNo = expectedNo;
		this.lastGenerationTime = 0;
		this.latitude = latitude;
		this.longitude = longitude;

		this.producerSpeed = producerSpeed;
		this.type = FIXED;
	}

	public CarProductionLine(Strategy s,int producerSpeed, int noOfCarsGenerated, int expectedNo, int[] stuff, ShapefileDataStore dataStore)
	{
		this.noOfCarsGenerated = noOfCarsGenerated;
		this.lastGenerationTime = 0;
		this.producerSpeed = producerSpeed;
		

		this.type = RANDOM;
		this.expectedNo = expectedNo;
		this.strategy=s;
		this.stuff = stuff;
		this.dataStore = dataStore;
	}

	/**
	 * Put a car on the map
	 */
	public void setCar() {

		int tries = 0;
		if(type ==RANDOM)
		{
			for (int i = 0; i < noOfCarsGenerated; i++) {
				Car c = generateRandomCar(Timer.getClock());
				while (c == null){
					tries++;
					System.out.println("i'm null somehow : " + tries);
					c = generateRandomCar(Timer.getClock());
				}
				Simulator.getStations().get(c.getDestination()).addMovingQueue(c);
				
			}
		}
		else if (type == FIXED) {
			
			Point source=Simulator.geoToCor(latitude, longitude);
			String destination=strategy.chooseDestination(source);

			for (int i = 0; i < noOfCarsGenerated; i++) {
				Car c = new Car(latitude, longitude, destination, Timer.getClock(), strategy.setChargingTime(source));
				c.saveRoute(c.getCarRoute());
				Simulator.getStations().get(c.getDestination()).addMovingQueue(c);
			}
		}

		currentNo += noOfCarsGenerated;

		Simulator.updateCarNolbl(noOfCarsGenerated);


	}
	/**
	 * Generate a car randomly within a zone
	 * 
	 * @param stationZoneNo
	 * @param destination
	 * @param generationTime
	 * @param chargingTime
	 * @return
	 */
	private  Car generateRandomCar(int generationTime) {
		Boolean error = true;
		int errorCount=0;
		while (error) {

			String destination;			
			
			
			try {
						        
		     // Compute the total weight of all items together
		        int totalWeight = 0;
		        int selection = -1;
		        for (int element : stuff)
		        {
		           totalWeight += element;
		           
		        }

		        int position = new Random().nextInt(totalWeight);
//		        System.out.println("totalweight:" + totalWeight);
//		        System.out.println("position:" + position);
		        int f = 0;
		        for (int element : stuff)
		        {
//		        	System.out.println("element:" + element);
		        	f++;
		        	if (position < element)
		        	{
		        	  
		               selection = f;
		               break;
		        	}
		        	position -= element;
		        }
//		        System.out.println("selection:" + selection);
		        
		        
		        FeatureReader<SimpleFeatureType,SimpleFeature> features1 = dataStore.getFeatureReader();
		        SimpleFeature selectedFeature= features1.next();
		        for (int j=1 ; j<selection ; j++) {
		        	selectedFeature = features1.next();        	
		        }         
		        	
//				System.out.println("id:" + selectedFeature.getAttribute(1));
//		    	System.out.println("name:" + selectedFeature.getAttribute(2));
//		    	System.out.println("population:" + selectedFeature.getAttribute(3));
		        features1.close();
//		        System.out.println(firstFeature.getAttribute(0));
		        GeometryFactory geometryFactory = (GeometryFactory) JTSFactoryFinder.getGeometryFactory( null );
//		        Coordinate coord = new Coordinate( geo1.getLongitude(), geo1.getLatitude() );
//		        com.vividsolutions.jts.geom.Point point = geometryFactory.createPoint(coord);
		        Geometry g = (Geometry) selectedFeature.getAttribute( "the_geom" );

			    Envelope env = new Envelope(selectedFeature.getBounds().getMinX(), selectedFeature.getBounds().getMaxX(), selectedFeature.getBounds().getMinY(), selectedFeature.getBounds().getMaxY());
			    double x = env.getMinX() + env.getWidth() * Math.random();
				double y = env.getMinY() + env.getHeight() * Math.random();
				Coordinate pt = new Coordinate(x, y);
//				geometryFactory.getPrecisionModel().makePrecise(pt);
//				Coordinate p = pt;
				com.vividsolutions.jts.geom.Point point1 = geometryFactory.createPoint(pt);
				Point source = Simulator.geoToCor(y,x);
//				System.out.println(x);
//				System.out.println(y);
				int tries = 0;
				destination=strategy.chooseDestination(source);

		        while(tries<5){
		        	if (point1.isWithinDistance(g, 0)){

						if(destination!=null)
						{
							error=false;
							
							//sDs.chooseDestination(source);
							int x1 = (int) source.getX();
							int y1 = (int) source.getY();
							GeoPosition geo1 = Simulator.corToGeo(x1, y1);
							Point des = Simulator.getStations().get(destination).getPoint();
							
							int chargingTime=strategy.setChargingTime(source);
							int y2 = (int) des.getY();
							int x2 = (int) des.getX();
	
							
							GeoPosition geo2 = Simulator.corToGeo(x2, y2);
							
							GHResponse res = Simulator.getRoute(geo1, geo2);
							if (res != null){
								Car c = new Car(geo1.getLatitude(), geo1.getLongitude(), destination, generationTime, chargingTime);
								c.saveRoute(res);
	//							System.out.println("success");
								return c;

								
							}
							
						}
		        	}
		        	tries++;
		        	x = env.getMinX() + env.getWidth() * Math.random();
					y = env.getMinY() + env.getHeight() * Math.random();
					pt = new Coordinate(x, y);
					point1 = geometryFactory.createPoint(pt);
					source = Simulator.geoToCor(y,x);
		        } 
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
			errorCount++;
			//can not successfully get the station destination
			if(errorCount==10)
			{
				System.out.println("System Error From Car Production Line: Can not get the destination with current strategy");
			}
					


		}
		return null;
	}
	
	/**
	 * Check if the generation ends
	 * @return
	 */
	public Boolean isEnd() {
		// keep running
		if (currentNo == -1) {
			return false;
		}
		
		// car production for this production line is done
		else if (currentNo >= expectedNo) {
//			System.out.println("isEnd of car Production line is ");
//			System.out.print(currentNo >= expectedNo);

			return true;
		} else {
//			System.out.println("isEnd of car Production line is ");
//			System.out.print(currentNo >= expectedNo);
			return false;
		}

	}
	
	/**
	 * Generate a car 
	 */
	public void run() {
		if (Timer.getClock() - lastGenerationTime >= producerSpeed) {
			setCar();
			lastGenerationTime = Timer.getClock();
		}
	}
}
