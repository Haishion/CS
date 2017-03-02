package main;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;

import org.apache.commons.collections.map.HashedMap;
import org.geotools.data.FeatureReader;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import mainAPI.ReadExcelFile;
public class Simulator {
	public static void main(String[] args) {
		double latitude=1.34982018;
		double longitude=103.80929946;
		int zoomLevel=7;
		mainAPI.Simulator frame= new mainAPI.Simulator(latitude,longitude ,zoomLevel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTimeMapping(1,900);//1min=20miliseconds
		//https://mapzen.com/data/metro-extracts can get OSM data here, noted: first time using the OSM file it needs more time for extracting the map data
		
		frame.setMapOSM("import\\mapData"+"\\"+"singapore.osm");
//		frame.integrateTraffic("import\\trafficData.json");
		
		frame.setStations("import\\s.xlsx");
		String zoneArrayCenter[]={"Raffles Place","Chinatown","Queenstown","Keppel","Dover","City Hall","Bugis","Farrer Park","Orchard","Tanglin","Bukit Timah","Toa Payoh","Macpherson","Kembangan","Katong","Bayshore","Changi","Pasir Ris","Punggol","Bishan","Clementi","Boon Lay","Bukit Batok","Lim Chu Kang","Admiralty","Tagore","Sembawang","Seletar"
};
		ShortestDistanceStrategy sDs=new ShortestDistanceStrategy();
		
		
		File shpFile = new File("PlanningAreaPopulation.shp");

	    Map map = new HashedMap();
	    ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
	    ShapefileDataStoreFactory.ShpFileStoreFactory shpFileStoreFactory = new ShapefileDataStoreFactory.ShpFileStoreFactory(dataStoreFactory, map);

        ShapefileDataStore dataStore = null;
        int[] stuff = new int[55];
		try {
			dataStore = (ShapefileDataStore)shpFileStoreFactory.getDataStore(shpFile);
			FeatureReader<SimpleFeatureType,SimpleFeature> features = dataStore.getFeatureReader();
			
	        int i = 0;
	        String[][] shpdata = new String[55][3];
	        
	        while (features.hasNext()){
	        	SimpleFeature nextFeature = features.next();

	            shpdata[i][0]= nextFeature.getAttribute(1).toString();
	            shpdata[i][1]= nextFeature.getAttribute(2).toString();
	            shpdata[i][2]= nextFeature.getAttribute(3).toString();
	 
	            stuff[i] =  Integer.parseInt(shpdata[i][2]);
	            i++;
	            
//	            System.out.println("id:" + shpdata[i][0]);
//	            System.out.println("name:" + shpdata[i][1]);
//	            System.out.println("population:" + shpdata[i][2]);
	        }
	        features.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		
		
		
		frame.addRandomCarGenerater(sDs,5, 20, 200, zoneArrayCenter , stuff , dataStore);
//		s.addRandomCarGenerater(sDs,5,4,1120, zoneArrayCenter);
		frame.draw();
	}
}

//String zoneArrayCenter[]={"Tanglin","Orchard","Novena","CityHall"};
//String zoneArray[]={"ChoaChuKang","BukitBatok","Seragoon","AngMokio","Clementi","ToaPayoh","ChinaTown"};
	
//s.addFixedCarGenerater(5,10,50, 1.36094, 103.82432, destinationStationArray, 2);//mins
	
//s.addCar("Car_3",1.336434280186183, 103.809814453125,"1",2,5);
//s.addCar(1.31042, 103.75406,destinationStation,5);
//s.addCar(1.31042, 103.75406,destinationStation,2);
//
//for(int i=0;i<50;i++)
//{
//	int no=randInt(0,2);
//	int des=randInt(0,2);
//	s.generateCar("haha", "haha", 5);
//
//}