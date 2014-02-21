package ru.droogcompanii.application.ui.helpers;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ru.droogcompanii.application.data.db_util.hierarchy_of_partners.FavoriteDBUtils;

/**
 * Created by ls on 19.02.14.
 */
public class IsFavoriteViewUtils {
    private final FavoriteDBUtils favoriteDBUtils;

    public IsFavoriteViewUtils(Context context) {
        favoriteDBUtils = new FavoriteDBUtils(context);
    }

    public void init(CheckBox checkBox, final int partnerId) {
        boolean isFavorite = favoriteDBUtils.isFavorite(partnerId);
        checkBox.setChecked(isFavorite);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                favoriteDBUtils.setFavorite(partnerId, checked);
            }
        });
    }
}