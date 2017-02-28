//package mainAPI;
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
//import com.vividsolutions.jts.geom.Envelope; ///C:/Users/EltonQ/Desktop/FYP/geotools-16.1-bin/geotools-16.1/jts-1.13.jar
//
//
//public class ShapeFileProcessor {
//			
//			boolean notout = true;
//		    File shpFile = new File("testtest.shp");
//		    Map map = new HashedMap();
//		    ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
//		    ShapefileDataStoreFactory.ShpFileStoreFactory shpFileStoreFactory = new ShapefileDataStoreFactory.ShpFileStoreFactory(dataStoreFactory, map);
//
//	        ShapefileDataStore dataStore = (ShapefileDataStore)shpFileStoreFactory.getDataStore(shpFile);
//	        FeatureReader<SimpleFeatureType,SimpleFeature> features = dataStore.getFeatureReader();
//	        SimpleFeature firstFeature = features.next();
//
////	        System.out.println(firstFeature.getAttribute(0));
//	        
//	        GeometryFactory geometryFactory = (GeometryFactory) JTSFactoryFinder.getGeometryFactory( null );
//	        Coordinate coord = new Coordinate( 103.90830379739039, 1.2982482869606657 );
////	        Point point = geometryFactory.createPoint(coord);
//	        Geometry g = (Geometry) firstFeature.getAttribute( "the_geom" );
//	        
////	        if(point.isWithinDistance(g, 0.01)){
////	        	System.out.println("shiets man");
////	        } else {
////	        	System.out.println("gg");
////	        }
//	       
//	       Envelope env = new Envelope(firstFeature.getBounds().getMinX(), firstFeature.getBounds().getMaxX(), firstFeature.getBounds().getMinY(), firstFeature.getBounds().getMaxY());
//	       
////	       System.out.println(env);
////	       RandomPointsBuilder rpb = new RandomPointsBuilder(geometryFactory);
////	       rpb.setExtent();
////	       System.out.println(rpb.getExtent());
//
//	       
////	       while (notout){
////	    	   double x = env.getMinX() + env.getWidth() * Math.random();
////	           double y = env.getMinY() + env.getHeight() * Math.random();
////	           Coordinate pt = new Coordinate(x, y);
////	           geometryFactory.getPrecisionModel().makePrecise(pt);
////	           Coordinate p = pt;
////	           Point point1 = geometryFactory.createPoint(p);
////
////	    	   if(point1.isWithinDistance(g, 0)){
////	    		   System.out.println(point1);
////	    		   System.out.println("shiets man");
////	    	   } else {
////	    		   System.out.println(point1);
////	    		   System.out.println("gg");
////	    		   notout = false;
////	    	   }
////	       }
////	       
//	       
////	        GeometryCollection geometry =
////	                geometryFactory.createGeometryCollection(new Geometry[] { randomPoint(srid), randomMultiPoint(srid),
////	                        randomLineString(srid), randomMultiLineString(srid), randomPolygon(srid),
////	                        randomMultiPolygon(srid) });
////	        geometry.setSRID(srid);
//
//	        
//	        
//	        
//	        
////	        URL location = ShapefileDataStoreFactory.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location.getPath());	
////	        URL location1 = ShapefileDataStore.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location1.getPath());	
////	        URL location2 = FeatureReader.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location2.getPath());	
////	        URL location3 = SimpleFeature.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location3.getPath());	
////	        URL location4 = GeometryFactory.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location4.getPath());	
////	        URL location5 = Coordinate.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location5.getPath());	
////	        URL location6 = Point.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location6.getPath());	
////	        URL location7 = Geometry.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location7.getPath());	
////	        URL location8 = JTSFactoryFinder.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location8.getPath());	
////	        URL location9 = SimpleFeatureType.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location9.getPath());	
////	        URL location0 = HashedMap.class.getProtectionDomain().getCodeSource().getLocation();
////	        System.out.println(location0.getPath());	
//
//	        
//		    
//		    
//
//}
