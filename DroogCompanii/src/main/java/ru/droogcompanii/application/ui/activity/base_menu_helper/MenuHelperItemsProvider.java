package ru.droogcompanii.application.ui.activity.base_menu_helper;

import android.app.Activity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

import ru.droogcompanii.application.ui.activity.base_menu_helper.menu_item_helper.MenuItemHelper;

/**
 * Created by ls on 14.02.14.
 */
public abstract class MenuHelperItemsProvider implements MenuHelper {

    private final Activity activity;
    private Menu menu;

    public MenuHelperItemsProvider(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void init(Menu menu) {
        this.menu = menu;
        for (MenuItemHelper each : getMenuItemHelpers()) {
            addItem(each);
        }
    }

    private void addItem(final MenuItemHelper menuItemHelper) {
        String title = activity.getString(menuItemHelper.getTitleId());
        MenuItem item = menu.add(Menu.NONE, menuItemHelper.getId(), menuItemHelper.getOrder(), title);
        item.setIcon(menuItemHelper.getIconId());
        MenuItemCompat.setShowAsAction(item, menuItemHelper.getShowAsAction());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MenuItemHelper.Action actionOnClickMenuItem = menuItemHelper.getAction();
                actionOnClickMenuItem.run(activity);
                return true;
            }
        });
    }

    protected abstract MenuItemHelper[] getMenuItemHelpers();
}