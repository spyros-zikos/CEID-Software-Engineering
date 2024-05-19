package com.mycompany.casheri;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.LMProfile;
import com.graphhopper.config.Profile;
import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.Translation;
import com.graphhopper.util.shapes.GHPoint;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class RoutingService {
    
    private static RoutingService instance;
    private final GraphHopper hopper;

    public static RoutingService getInstance() {//double start_lat, double start_long, double end_lat, double end_long
        if (instance == null) {
            instance = new RoutingService();//start_lat, start_long, end_lat, end_long
        }
        return instance;
    }

    private RoutingService() {//double start_lat, double start_long, double end_lat, double end_long     
        String ghLoc = "C:\\Users\\kalli\\Desktop\\github\\Software-Engineering-Project\\app\\casheri\\jar_files\\graphhopper_lib\\greece-latest.osm.pbf";
        hopper = createGraphHopperInstance(ghLoc);
//        customizableRouting(ghLoc, start_lat, start_long, end_lat, end_long);
    }

    private GraphHopper createGraphHopperInstance(String ghLoc) {
        GraphHopper grhopper = new GraphHopper();
        grhopper.setOSMFile(ghLoc);
        // specify where to store graphhopper files
        grhopper.setGraphHopperLocation("target/routing-graph-cache");

        // add all encoded values that are used in the custom model, these are also available as path details or for client-side custom models
        grhopper.setEncodedValuesString("car_access, car_average_speed");
        // see docs/core/profiles.md to learn more about profiles
        grhopper.setProfiles(new Profile("car").setCustomModel(GHUtility.loadCustomModelFromJar("car.json")));

        // this enables speed mode for the profile we called car
        grhopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));

        // now this can take minutes if it imports or a few seconds for loading of course this is dependent on the area you import
        grhopper.importOrLoad();
        return grhopper;
    }

    public List<RoutingData> routing(double fromLat, double fromLon, double toLat, double toLon) {
        // simple configuration of the request object
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon).

        // note that we have to specify which profile we are using even when there is only one like here
        setProfile("car").
        // define the language for the turn instructions
        setLocale(Locale.US);
        GHResponse rsp = hopper.route(req);

        // handle errors
        if (rsp.hasErrors()) {
            throw new RuntimeException(rsp.getErrors().toString());
        }

        // use the best path, see the GHResponse class for more possibilities.
        ResponsePath path = rsp.getBest();

        // points, distance in meters and time in millis of the full path
        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();

        Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);
        InstructionList il = path.getInstructions();
        // iterate over all turn instructions
        List<RoutingData> list = new ArrayList<>();
        for (Instruction instruction : il) {
            // System.out.println("distance " + instruction.getDistance() + " for instruction: " + instruction.getTurnDescription(tr));
            list.add(new RoutingData(instruction.getDistance(), instruction.getTurnDescription(tr), instruction.getPoints()));
        }
        return list;
    }
    
//    public static void customizableRouting(String ghLoc, double start_lat, double start_long, double end_lat, double end_long) {
//        GraphHopper hopper = new GraphHopper();
//        hopper.setOSMFile(ghLoc);
//        hopper.setGraphHopperLocation("target/routing-custom-graph-cache");
//        hopper.setEncodedValuesString("car_access, car_average_speed");
//        hopper.setProfiles(new Profile("car_custom").setCustomModel(GHUtility.loadCustomModelFromJar("car.json")));
//
//        // The hybrid mode uses the "landmark algorithm" and is up to 15x faster than the flexible mode (Dijkstra).
//        // Still it is slower than the speed mode ("contraction hierarchies algorithm") ...
//        hopper.getLMPreparationHandler().setLMProfiles(new LMProfile("car_custom"));
//        hopper.importOrLoad();
//
//        // ... but for the hybrid mode we can customize the route calculation even at request time:
//        // 1. a request with default preferences
//        GHRequest req = new GHRequest().setProfile("car_custom").
//                addPoint(new GHPoint(start_lat, start_long)).addPoint(new GHPoint(end_lat, end_long));
//
//        GHResponse res = hopper.route(req);
//        if (res.hasErrors())
//            throw new RuntimeException(res.getErrors().toString());
//
//        assert Math.round(res.getBest().getTime() / 1000d) == 94;
//
//        // 2. now avoid the secondary road and reduce the maximum speed, see docs/core/custom-models.md for an in-depth explanation
//        // and also the blog posts https://www.graphhopper.com/?s=customizable+routing
//        CustomModel model = new CustomModel();
//        model.addToPriority(If("road_class == SECONDARY", MULTIPLY, "0.5"));
//
//        // unconditional limit to 20km/h
//        model.addToSpeed(If("true", LIMIT, "30"));
//
//        req.setCustomModel(model);
//        res = hopper.route(req);
//        if (res.hasErrors())
//            throw new RuntimeException(res.getErrors().toString());
//
//        assert Math.round(res.getBest().getTime() / 1000d) == 184;
//    }
}
