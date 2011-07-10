package com.xpansive.bukkit.worldgen;

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.lennardf1989.bukkitex.MyDatabase;

class VoronoiNoise {
	private static ArrayList<Point3D> voronoiPoints;
	public static Random random;
	private static ArrayList<Point> generatedChunks;
	public static MyDatabase database;

	public static void Save() {
		Data d = new Data();
		d.setChunks(generatedChunks);
		d.setPoints(voronoiPoints);

		database.getDatabase().save(d);
	}

	public static void Load() {
		Data d = database.getDatabase().find(Data.class, 0);
		if (d != null) {
			generatedChunks = d.getChunks();
			voronoiPoints = d.getPoints();
		}
		else {
			 generatedChunks = new ArrayList<Point>();
			 voronoiPoints = new ArrayList<Point3D>();
		}
	}

	public static void GenChunks(int x, int y, int width, int height,
			int numPoints) {
		
		if (generatedChunks == null || voronoiPoints == null)
			Load();
		
		int offset = 1;
		for (int cx = x / width - offset; cx < x / width + offset; cx++)
			for (int cy = y / height - offset; cy < y / height + offset; cy++) {
				GenVoronoi(width, height, numPoints, cx, cy);
			}
	}

	private static void GenVoronoi(int width, int height, int numPoints,
			int xOff, int yOff) {
		Point p = new Point(xOff, yOff);
		if (generatedChunks.contains(p)) // already generated
			return;

		generatedChunks.add(p);

		for (int index = 0; index < numPoints; index++) {
			voronoiPoints.add(new Point3D(random(xOff * width, xOff * width
					+ width), random(yOff * width, yOff * width + height),
					random.nextInt(height)));
		}
		// Collections.sort(voronoiPoints);
	}

	private static Stack<Point3D> closestPoints = new Stack<Point3D>();
	private static boolean forceAllPoints;
	private static boolean justCalculated;
	private static int lastLowestDistance;

	private static int random(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	public static int Voronoi(int x, int y) {
		int lowestDistance = Integer.MAX_VALUE;
		if (!justCalculated || forceAllPoints) {
			forceAllPoints = false;
			closestPoints.clear();

			for (Point3D point : voronoiPoints) {
				int temp = Distance(new Point(x, y), point);

				if (temp > lowestDistance)
					continue;
				lowestDistance = temp;
				closestPoints.push(point);
			}

			justCalculated = true; // prevent going through the whole list again
									// for the next pixel
			lastLowestDistance = 0;
			return lowestDistance;
		}

		for (Point3D point : closestPoints) {
			int temp = Distance(new Point(x, y), point);

			if (temp > lowestDistance)
				continue;

			lowestDistance = temp;
		}
		justCalculated = !justCalculated;
		lastLowestDistance = lowestDistance;
		return lowestDistance;
	}

	private static int Distance(Point p1, Point3D p2) {
		int x = (int) (p2.x - p1.x), y = (int) (p2.y - p1.y);
		// return (int)Math.Sqrt(x * x + y * y); //Length
		return x * x + y * y + p2.z * 25; // Length2
		// return (int)(Math.Abs(x) + Math.Abs(y)); //Manhattan
		// return (int)Math.Pow(x * x * x * x + y * y * y * y, 0.25);
		// //Minkowski4
	}
}

class Point3D implements Serializable {
	private static final long serialVersionUID = 1L;
	int x, y, z;

	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}