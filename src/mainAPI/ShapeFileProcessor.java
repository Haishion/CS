//package mainAPI;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//import org.apache.commons.collections.map.HashedMap;
//import org.geotools.data.FeatureReader;
//import org.geotools.data.shapefile.ShapefileDataStore;
//import org.geotools.data.shapefile.ShapefileDataStoreFactory;
//import org.geotools.geometry.jts.JTSFactoryFinder;
//import org.opengis.feature.simple.SimpleFeature;
//import org.opengis.feature.simple.SimpleFeatureType;
//
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.Point;
//
//import com.vividsolutions.jts.geom.Envelope;
//
//public class ShapeFileProcessor {
//
//	File shpFile = new File("PlanningAreaPopulation.shp");
//	Map map = new HashedMap();
//	ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();{
//	ShapefileDataStoreFactory.ShpFileStoreFactory shpFileStoreFactory = new ShapefileDataStoreFactory.ShpFileStoreFactory(dataStoreFactory, map);
//
//	try	{
//		ShapefileDataStore dataStore = (ShapefileDataStore) shpFileStoreFactory.getDataStore(shpFile);
//		FeatureReader<SimpleFeatureType, SimpleFeature> features = dataStore.getFeatureReader();
//
//		int i = 0;
//		String[][] shpdata = new String[55][3];
//		int[] stuff = new int[55];
//		while (features.hasNext()) {
//			SimpleFeature nextFeature = features.next();
//
//			shpdata[i][0] = nextFeature.getAttribute(1).toString();
//			shpdata[i][1] = nextFeature.getAttribute(2).toString();
//			shpdata[i][2] = nextFeature.getAttribute(3).toString();
//
//			stuff[i] = Integer.parseInt(shpdata[i][2]);
//			i++;
//
//			// System.out.println("id:" + shpdata[i][0]);
//			// System.out.println("name:" + shpdata[i][1]);
//			// System.out.println("population:" + shpdata[i][2]);
//		}
//		features.close();
//	}catch(
//	IOException e)
//	{
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//}
//	
