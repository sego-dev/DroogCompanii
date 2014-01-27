package ru.droogcompanii.application.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;

import ru.droogcompanii.application.R;
import ru.droogcompanii.application.util.latlng_bounds_calculator.LatLngBoundsCalculator;
import ru.droogcompanii.application.view.helpers.ObserverOfViewWillBePlacedOnGlobalLayout;

/**
 * Created by ls on 10.01.14.
 */
public class BaseCustomMapFragment extends android.support.v4.app.Fragment {

    private boolean mapViewIsPlacedOnLayout;
    private Collection<Marker> markers;
    private GoogleMap map;

    public BaseCustomMapFragment() {
        mapViewIsPlacedOnLayout = false;
        markers = new ArrayList<Marker>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_map, container, false);
    }

    protected final GoogleMap getGoogleMap() {
        if (needToInitMap()) {
            initMap();
        }
        return map;
    }

    private boolean needToInitMap() {
        return (map == null) && (getActivity() != null) &&
                (getActivity().getSupportFragmentManager() != null);
    }

    private void initMap() {
        SupportMapFragment mapFragment = getNestedSupportMapFragment();
        if (mapFragment != null) {
            map = mapFragment.getMap();
        }
    }

    private SupportMapFragment getNestedSupportMapFragment() {
        return (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapView);
    }

    protected final View getMapView() {
        return getNestedSupportMapFragment().getView();
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

    protected final void fitVisibleMarkersOnScreenAfterMapViewWillBePlacedOnLayout() {
        if (mapViewIsPlacedOnLayout) {
            fitVisibleMarkersOnScreen();
        } else {
            ObserverOfViewWillBePlacedOnGlobalLayout.runAfterViewWillBePlacedOnLayout(getMapView(), new Runnable() {
                @Override
                public void run() {
                    fitVisibleMarkersOnScreen();
                    mapViewIsPlacedOnLayout = true;
                }
            });
        }
    }

    private void fitVisibleMarkersOnScreen() {
        if (noVisibleMarkers()) {
            return;
        }
        LatLngBounds bounds = LatLngBoundsCalculator.calculateBoundsOfVisibleMarkers(markers);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, getMapPadding());
        getGoogleMap().moveCamera(cameraUpdate);
    }

    private boolean noVisibleMarkers() {
        for (Marker each : markers) {
            if (each.isVisible()) {
                return false;
            }
        }
        return true;
    }

    private int getMapPadding() {
        return getResources().getInteger(R.integer.map_markers_padding);
    }

    protected Marker findMarkerByPosition(LatLng position) {
        for (Marker marker : markers) {
            if (position.equals(marker.getPosition())) {
                return marker;
            }
        }
        return null;
    }
}
