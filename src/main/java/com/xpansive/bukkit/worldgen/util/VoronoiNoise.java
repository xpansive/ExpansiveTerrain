package com.xpansive.bukkit.worldgen.util;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.xpansive.bukkit.worldgen.ExpansiveTerrain;

public class VoronoiNoise {
	private ArrayList<Point3D> voronoiPoints;
	private Random random;
	private ArrayList<Point> generatedChunks;
	private final int calcOffset = 3;

	public VoronoiNoise(Random random) {
		this.random = random;
		load();
	}

	private void save() {
		try {
			OutputStream file = new FileOutputStream(ExpansiveTerrain.WORLD_NAME + "/points.ser");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				Stack<Object> s = new Stack<Object>();
				s.push(voronoiPoints);
				s.push(generatedChunks);
				
				output.writeObject(s);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			System.out.println("Cannot save ExpansiveTerrain data!");
		}
	}

	@SuppressWarnings("unchecked")
	private void load() {
		generatedChunks = new ArrayList<Point>();
		voronoiPoints = new ArrayList<Point3D>();

		try {
			InputStream file = new FileInputStream(ExpansiveTerrain.WORLD_NAME + "/points.ser");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				Object readObject = input.readObject();
				if (readObject instanceof Stack<?>) {
					Stack<Object> s = (Stack<Object>) readObject;
					
					generatedChunks = (ArrayList<Point>)s.pop();
					voronoiPoints = (ArrayList<Point3D>)s.pop();
				}
				else
					System.out
							.println("ExpansiveTerrain's data file is corrupted or contains the wrong data!");
			} finally {
				input.close();
			}
		}

		catch (Exception ex) {
			System.out.println("Cannot load ExpansiveTerrain data!");
		}
	}

	public void genChunks(int x, int y, int width, int height, int numPoints) {
		
		for (int cx = x / width - calcOffset; cx < x / width + calcOffset; cx++)
			for (int cy = y / height - calcOffset; cy < y / height + calcOffset; cy++) {
				genVoronoi(width, height, random.nextInt(numPoints), cx, cy);
			}

		save();
	}

	private void genVoronoi(int width, int height, int numPoints, int xOff,
			int yOff) {
		Point p = new Point(xOff, yOff);
		if (generatedChunks.contains(p)) // already generated
			return;

		generatedChunks.add(p);

		for (int index = 0; index < numPoints; index++) {
			voronoiPoints.add(new Point3D(random(xOff * width, xOff * width
					+ width), random(yOff * width, yOff * width + height),
					random.nextInt(height)));
		}
	}

	private Stack<Point3D> closestPoints = new Stack<Point3D>();
	private boolean forceAllPoints;
	private boolean justCalculated;
	private int lastLowestDistance;

	private int random(int min, int max) {
		return min + (int) (random.nextDouble() * ((max - min) + 1));
	}

	public int get(int x, int y) {
		int lowestDistance = Integer.MAX_VALUE;
		if (!justCalculated || forceAllPoints) {
			forceAllPoints = false;
			closestPoints.clear();

			for (Point3D point : voronoiPoints) {
				int temp = distance(new Point(x, y), point);

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
			int temp = distance(new Point(x, y), point);

			if (temp > lowestDistance)
				continue;

			lowestDistance = temp;
		}
		justCalculated = !justCalculated;
		lastLowestDistance = lowestDistance;
		return lowestDistance;
	}

	private static int distance(Point p1, Point3D p2) {
		int x = (int) (p2.x - p1.x), y = (int) (p2.y - p1.y);
		// return (int)Math.Sqrt(x * x + y * y); //Length
		return x * x + y * y + p2.z * 25; // Length2
		// return (int)(Math.Abs(x) + Math.Abs(y)); //Manhattan
		// return (int)Math.Pow(x * x * x * x + y * y * y * y, 0.25);
		// //Minkowski4
	}
}