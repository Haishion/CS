package mainAPI;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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

	public CarProductionLine(Strategy s,int producerSpeed, int noOfCarsGenerated, int expectedNo, String[] zoneArray)
	{
		this.noOfCarsGenerated = noOfCarsGenerated;
		this.lastGenerationTime = 0;
		this.producerSpeed = producerSpeed;
		this.zoneArray = zoneArray;

		this.type = RANDOM;
		this.expectedNo = expectedNo;
		this.strategy=s;
	}

	/**
	 * Put a car on the map
	 */
	public void setCar() {



		if(type ==RANDOM)
		{
			for (int i = 0; i < noOfCarsGenerated; i++) {
				Car c =generateRandomCar(zoneArray, Timer.getClock());
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
	private  Car generateRandomCar(String[] stationZoneNoArray, int generationTime) {
		Boolean error = true;
		int errorCount=0;
		while (error) {
//			Point source = CarFactory.randomPointWithinZone(stationZoneNoArray);
			String destination;			
			
			File shpFile = new File("testtest.shp");
		    Map map = new HashedMap();
		    ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
		    ShapefileDataStoreFactory.ShpFileStoreFactory shpFileStoreFactory = new ShapefileDataStoreFactory.ShpFileStoreFactory(dataStoreFactory, map);

	        ShapefileDataStore dataStore;
			try {
				dataStore = (ShapefileDataStore)shpFileStoreFactory.getDataStore(shpFile);
				FeatureReader<SimpleFeatureType,SimpleFeature> features = dataStore.getFeatureReader();
		        SimpleFeature firstFeature = features.next();
//		        System.out.println(firstFeature.getAttribute(0));
		        GeometryFactory geometryFactory = (GeometryFactory) JTSFactoryFinder.getGeometryFactory( null );
//		        Coordinate coord = new Coordinate( geo1.getLongitude(), geo1.getLatitude() );
//		        com.vividsolutions.jts.geom.Point point = geometryFactory.createPoint(coord);
		        Geometry g = (Geometry) firstFeature.getAttribute( "the_geom" );
		        features.close();
			    Envelope env = new Envelope(firstFeature.getBounds().getMinX(), firstFeature.getBounds().getMaxX(), firstFeature.getBounds().getMinY(), firstFeature.getBounds().getMaxY());
			    double x = env.getMinX() + env.getWidth() * Math.random();
				double y = env.getMinY() + env.getHeight() * Math.random();
				Coordinate pt = new Coordinate(x, y);
//				geometryFactory.getPrecisionModel().makePrecise(pt);
//				Coordinate p = pt;
				com.vividsolutions.jts.geom.Point point1 = geometryFactory.createPoint(pt);
				Point source = Simulator.geoToCor(y,x);
//				System.out.println(x);
//				System.out.println(y);
//				System.out.println("HELLO!");
				destination=strategy.chooseDestination(source);
		        
		        if(point1.isWithinDistance(g, 0)){
		        	System.out.println("shiets man");
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
						
						GHResponse res =Simulator.getRoute(geo1, geo2);
						Car c = new Car(geo1.getLatitude(), geo1.getLongitude(), destination, generationTime, chargingTime);
						c.saveRoute(res);
						return c;
					}
		        } else {
		        	System.out.println("gg");
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
