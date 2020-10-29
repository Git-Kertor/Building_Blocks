package com.example.buildingblocks.ui.home;

public class list_element {

    String _text;
    boolean checked;
    int order = 0;

    public list_element(String _text, boolean checked, int order)
    {
        this._text = _text;
        this.checked = checked;
        this.order = order;
    }

    public int getOrder()
    {
        return order;
    }

    public boolean getBool()
    {
        return checked;
    }

    public String getString()
    {
        return _text;
    }

}
