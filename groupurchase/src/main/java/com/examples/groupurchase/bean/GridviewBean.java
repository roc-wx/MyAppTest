package com.examples.groupurchase.bean;

public class GridviewBean {
    private int gridview_image;
    private String gridview_text;

    public GridviewBean() {
    }

    public GridviewBean(int gridview_image, String gridview_text) {
        this.gridview_image = gridview_image;
        this.gridview_text = gridview_text;
    }

    public int getGridview_image() {
        return gridview_image;
    }

    public void setGridview_image(int gridview_image) {
        this.gridview_image = gridview_image;
    }

    public String getGridview_text() {
        return gridview_text;
    }

    public void setGridview_text(String gridview_text) {
        this.gridview_text = gridview_text;
    }
}
