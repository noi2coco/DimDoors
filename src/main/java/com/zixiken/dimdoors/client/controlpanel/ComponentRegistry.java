package com.zixiken.dimdoors.client.controlpanel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ComponentRegistry {

    private static final ComponentRegistry INSTANCE = new ComponentRegistry();

    private final UseOrderList<Component> componentList;

    private ComponentRegistry() {
        this.componentList = new UseOrderList<>();
    }

    public static ComponentRegistry getInstance() {
        return INSTANCE;
    }

    @Nonnull
    public UseOrderList<Component> getComponentList() {
        return this.componentList;
    }

    @Nonnull
    public ComponentRegistry addComponents(@Nonnull final Component... components) {
        for (final Component component : components) {
            this.componentList.use(component);
        }
        return this;
    }

    @Nullable
    public Component getComponentUnder(int x, int y) {
        for (final Component component : this.componentList.itemList) {
            if (x >= component.getPositionX() && x <= component.getPositionX() + component.getWidth() && y >= component.getPositionY() && y <= component.getPositionY() + component.getHeight()) {
                return component;
            }
        }
        return null;
    }

    public void removeComponent(@Nonnull final Component component) {
        this.componentList.remove(component);
    }
}
