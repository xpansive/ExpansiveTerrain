package com.xpansive.bukkit.worldgen;

import java.awt.Point;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="data")
public class Data {   
	@Id
	private int id;
	
    private static ArrayList<Point> generatedChunks;
    private static ArrayList<Point3D> voronoiPoints;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public ArrayList<Point> getChunks() {
        return generatedChunks;
    }
    public void setChunks(ArrayList<Point> chunks) {
    	generatedChunks = chunks;
    }
    
    public ArrayList<Point3D> getPoints() {
        return voronoiPoints;
    }
    public void setPoints(ArrayList<Point3D> points) {
    	voronoiPoints = points;
    }
}