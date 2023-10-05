package com.example.maps;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.SearchManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.annotations.AnnotationLanguage;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.AnnotationSchemeID;
import com.yandex.mapkit.directions.driving.Checkpoint;
import com.yandex.mapkit.directions.driving.ConditionsListener;
import com.yandex.mapkit.directions.driving.DirectionSign;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouteMetadata;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSection;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.directions.driving.Event;
import com.yandex.mapkit.directions.driving.Ferry;
import com.yandex.mapkit.directions.driving.FordCrossing;
import com.yandex.mapkit.directions.driving.JamSegment;
import com.yandex.mapkit.directions.driving.LaneSign;
import com.yandex.mapkit.directions.driving.ManoeuvreVehicleRestriction;
import com.yandex.mapkit.directions.driving.PedestrianCrossing;
import com.yandex.mapkit.directions.driving.RailwayCrossing;
import com.yandex.mapkit.directions.driving.RestrictedEntry;
import com.yandex.mapkit.directions.driving.RestrictedTurn;
import com.yandex.mapkit.directions.driving.RoadVehicleRestriction;
import com.yandex.mapkit.directions.driving.RuggedRoad;
import com.yandex.mapkit.directions.driving.SpeedBump;
import com.yandex.mapkit.directions.driving.StandingSegment;
import com.yandex.mapkit.directions.driving.TollRoad;
import com.yandex.mapkit.directions.driving.TrafficLight;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.PolylinePosition;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.navigation.RoutePosition;
import com.yandex.mapkit.navigation.internal.RoutePositionBinding;
import com.yandex.mapkit.places.panorama.PanoramaService;
import com.yandex.mapkit.transport.bicycle.Route;
import com.yandex.runtime.Error;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;
import com.yandex.runtime.ui_view.ViewProvider;
import android.Manifest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener{

    MapView mapView;
    Point myLocation;
    MapKit mapKit;
    View view;
    Button MapButton, Back;

    EditText longs, latitudes, longsSecond, latitudesSecond;

    MapObjectCollection mapObjectCollection;
    DrivingRouter drivingRouter;
    DrivingSession drivingSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("ef56f5fe-4057-4606-98f5-35c2653ff429");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapview);
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},1200);
        view = new View(MainActivity.this);
        view.setBackground(getApplicationContext().getDrawable(com.yandex.maps.mobile.R.drawable.search_layer_advert_pin_dust_default));

        longs = findViewById(R.id.longs);
        latitudes = findViewById(R.id.latitudes);

        longsSecond = findViewById(R.id.longsSecond);
        latitudesSecond = findViewById(R.id.latitudesSecond);

        MapButton = findViewById(R.id.MapButton);
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place1 != null)
                    mapView.getMap().getMapObjects().remove(place1);
                if (place2 != null)
                    mapView.getMap().getMapObjects().remove(place2);
                if (latitudes.getText().length() != 0 && longs.getText().length() != 0 && latitudesSecond.getText().length() != 0 && longsSecond.getText().length() != 0)
                    submitRequest(Double.parseDouble(latitudes.getText().toString()), Double.parseDouble(longs.getText().toString()), Double.parseDouble(latitudesSecond.getText().toString()), Double.parseDouble(longsSecond.getText().toString()));
                else if (latitudes.getText().length() != 0 && longs.getText().length() != 0 && latitudesSecond.getText().length() == 0 && longsSecond.getText().length() == 0)
                    if (myLocation != null)
                    submitRequest(myLocation.getLatitude(), myLocation.getLongitude(), Double.parseDouble(latitudes.getText().toString()), Double.parseDouble(longs.getText().toString()));
                else
                    Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });

        Back = findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Desirealize();
            }
        });

        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        mapObjectCollection = mapView.getMap().getMapObjects().addCollection();
    }

    private void GetLocation(Consumer consumer){
        mapKit.createLocationManager().requestSingleUpdate(new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                myLocation = location.getPosition();
                consumer.accept(myLocation);
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
            }
        });
    }

    private void Desirealize(){
        Object[] Locations = new Object[1];

        GetLocation(Location ->{
            Locations[0] = Location;
            GetLocationOnMap(Locations);
                });
    }

    private void GetLocationOnMap(Object[] Locations){
        if (Arrays.stream(Locations).noneMatch(Location -> Location == null)) {
            mapView.getMap().move(new CameraPosition(myLocation, 11.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 1), null);
            mapView.getMap().getMapObjects().addPlacemark(myLocation, new ViewProvider(view));
        }
    }

    @Override
    protected void onStop(){
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart(){
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapKit = MapKitFactory.getInstance();
        mapView.onStart();
    }

    @Override
    public void onDrivingRoutes(List<DrivingRoute> routes) {
        for (DrivingRoute route : routes) {
            mapObjectCollection.addPolyline(route.getGeometry());
        }
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {
        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    PlacemarkMapObject place1;
    PlacemarkMapObject place2;

    private void submitRequest(double firstPointLatitude, double firstPointLong, double secondPointLatitude, double secondPointLong) {
        DrivingOptions drivingOptions = new DrivingOptions();
        VehicleOptions vehicleOptions = new VehicleOptions();

        List<RequestPoint> requestPoints = new ArrayList<>();

        requestPoints.add(new RequestPoint(
                new Point(firstPointLatitude, firstPointLong),
                RequestPointType.WAYPOINT, null));
        place1 = mapView.getMap().getMapObjects().addPlacemark(new Point(firstPointLatitude, firstPointLong), new ViewProvider(view));
        requestPoints.add(new RequestPoint(
                new Point(secondPointLatitude, secondPointLong),
                RequestPointType.WAYPOINT, null));
        place2 = mapView.getMap().getMapObjects().addPlacemark(new Point(secondPointLatitude, secondPointLong), new ViewProvider(view));
        mapView.getMap().move(new CameraPosition(
                new Point(firstPointLatitude, firstPointLong), 11, 0, 0));
        drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this);
    }
}