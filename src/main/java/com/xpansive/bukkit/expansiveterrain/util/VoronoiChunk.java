package com.xpansive.bukkit.expansiveterrain.util;

import java.io.Serializable;
import java.util.ArrayList;

public class VoronoiChunk implements Serializable {
    private static final long serialVersionUID = 1L;

    public ArrayList<Point3D> points;
    public int x;
    public int y;

    public VoronoiChunk(int x, int y, ArrayList<Point3D> points) {
        this.points = points;
        this.x = x;
        this.y = y;
    }
}
