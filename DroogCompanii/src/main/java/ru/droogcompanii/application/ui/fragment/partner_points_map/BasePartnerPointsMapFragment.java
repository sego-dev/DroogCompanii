package ru.droogcompanii.application.ui.fragment.partner_points_map;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ru.droogcompanii.application.DroogCompaniiSettings;
import ru.droogcompanii.application.R;
import ru.droogcompanii.application.ui.util.ActualBaseLocationProvider;
import ru.droogcompanii.application.ui.util.LocationUtils;
import ru.droogcompanii.application.ui.util.ObserverOfViewWillBePlacedOnGlobalLayout;

/**
 * Created by ls on 10.01.14.
 */
public class BasePartnerPointsMapFragment extends CustomMapFragment
        implements MarkersFinder, LocationSource.OnLocationChangedListener {

    private static final LocationSource.OnLocationChangedListener
            DUMMY_ON_LOCATION_CHANGED_LISTENER = new LocationSource.OnLocationChangedListener() {
        @Override
        public void onLocationChanged(Location location) {
            // do nothing
        }
    };

    private boolean isFirstUpdatingMapCamera;
    private boolean isMapViewPlacedOnLayout;
    private List<Marker> markers;
    private LocationSource.OnLocationChangedListener onLocationChangedListener;


    public BasePartnerPointsMapFragment() {
        isMapViewPlacedOnLayout = false;
        markers = new ArrayList<Marker>();
        onLocationChangedListener = DUMMY_ON_LOCATION_CHANGED_LISTENER;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLocationChangedListener = DUMMY_ON_LOCATION_CHANGED_LISTENER;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstUpdatingMapCamera = (savedInstanceState == null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initShowingCurrentLocation();
    }

    private void initShowingCurrentLocation() {
        GoogleMap googleMap = getGoogleMap();
        if (googleMap == null) {
            return;
        }

        googleMap.setMyLocationEnabled(true);

        googleMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                BasePartnerPointsMapFragment.this.onLocationChangedListener = onLocationChangedListener;
                LocationUtils.updateCurrentLocation();
            }

            @Override
            public void deactivate() {
                BasePartnerPointsMapFragment.this.onLocationChangedListener = DUMMY_ON_LOCATION_CHANGED_LISTENER;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        onLocationChangedListener.onLocationChanged(location);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationUtils.addOnLocationChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationUtils.removeOnLocationChangedListener(this);
    }

    public final Marker addMarker(MarkerOptions markerOptions) {
        Marker marker = getGoogleMap().addMarker(markerOptions);
        markers.add(marker);
        return marker;
    }

    protected final void removeAllMarkers() {
        getGoogleMap().clear();
        markers.clear();
    }

    protected final void updateMapCameraAfterMapViewWillBePlacedOnLayout(
            final ClickedMarkerHolder clickedMarker) {
        if (isMapViewPlacedOnLayout) {
            updateMapCamera(clickedMarker);
        } else {
            ObserverOfViewWillBePlacedOnGlobalLayout.runAfterViewWillBePlacedOnLayout(getMapView(), new Runnable() {
                @Override
                public void run() {
                    updateMapCamera(clickedMarker);
                    isMapViewPlacedOnLayout = true;
                }
            });
        }
    }

    protected void updateMapCamera(ClickedMarkerHolder clickedMarker) {
        if (clickedMarker.isShowing()) {
            moveCamera(clickedMarker.getPosition());
        } else {
            updateMapCameraIfThereIsNoClickedMarker();
        }
        isFirstUpdatingMapCamera = false;
    }

    private void moveCamera(LatLng center) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(center, defineZoom());
        getGoogleMap().animateCamera(cameraUpdate);
    }

    private float defineZoom() {
        if (isFirstUpdatingMapCamera) {
            return DroogCompaniiSettings.getDefaultZoom();
        } else {
            return getCurrentZoom();
        }
    }

    private void updateMapCameraIfThereIsNoClickedMarker() {
        tryMoveCameraToCurrentLocation();
    }

    private void tryMoveCameraToCurrentLocation() {
        Location baseLocation = ActualBaseLocationProvider.getActualBaseLocation();
        moveCamera(positionOf(baseLocation));
    }

    private static LatLng positionOf(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    private void fitVisibleMarkersOnScreen() {
        MapCameraUpdater.fitVisibleMarkers(getGoogleMap(), markers, getMapPadding());
    }

    private int getMapPadding() {
        return getResources().getInteger(R.integer.map_markers_padding);
    }

    @Override
    public Marker findMarkerByPosition(LatLng position) {
        for (Marker marker : markers) {
            if (position.equals(marker.getPosition())) {
                return marker;
            }
        }
        return null;
    }

    @Override
    public boolean isMarkerPlacedOnMap(Marker marker) {
        return markers.contains(marker);
    }
}