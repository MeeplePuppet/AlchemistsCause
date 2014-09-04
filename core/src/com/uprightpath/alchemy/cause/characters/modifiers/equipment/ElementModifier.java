package com.uprightpath.alchemy.cause.characters.modifiers.equipment;

import com.uprightpath.alchemy.cause.Element;

/**
 * Created by Geo on 9/2/2014.
 */
public class ElementModifier extends EquipmentModifier {
    private Element element;

    public ElementModifier() {
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public boolean isRandomModifier() {
        return false;
    }
}
