package ru.droogcompanii.application.ui.activity.offer_list.offers_provider;

import android.content.Context;

import java.util.List;

import ru.droogcompanii.application.data.offers.Offer;

/**
 * Created by ls on 10.02.14.
 */
public interface OffersProvider {

    public static interface Result {
        boolean isAbsent();
        boolean isPresent();
        List<Offer> getOffers();
    }

    Result getOffers(Context context);
}
