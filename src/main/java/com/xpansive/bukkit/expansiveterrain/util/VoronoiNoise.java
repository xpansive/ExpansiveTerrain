package com.xpansive.bukkit.expansiveterrain.util;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class VoronoiNoise {
    private ArrayList<VoronoiChunk> voronoiChunks;
    private Random random;
    private ArrayList<Point> generatedChunks;
    private final int CALC_OFFSET = 3;
    private String dataDir;

    public VoronoiNoise(Random random, String dataDir) {
        this.random = random;
        this.dataDir = dataDir;
        load();
    }

    private void save() {
        try {
            OutputStream file = new FileOutputStream(dataDir + "/voronoiChunks.ser");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try {
                Stack<Object> s = new Stack<Object>();
                s.push(voronoiChunks);
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
        voronoiChunks = new ArrayList<VoronoiChunk>();

        try {
            InputStream file = new FileInputStream(dataDir + "/voronoiChunks.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try {
                Object readObject = input.readObject();
                if (readObject instanceof Stack<?>) {
                    Stack<Object> s = (Stack<Object>) readObject;

                    generatedChunks = (ArrayList<Point>) s.pop();
                    voronoiChunks = (ArrayList<VoronoiChunk>) s.pop();
                } else
                    System.out.println("ExpansiveTerrain's data file is corrupted or contains the wrong data!");
            } finally {
                input.close();
            }
        }

        catch (Exception ex) {
            System.out.println("Cannot load ExpansiveTerrain data!");
        }
    }

    public int[][] genChunks(int x, int y, int width, int height, int numPoints) {
        ArrayList<VoronoiChunk> currChunks = new ArrayList<VoronoiChunk>();
        int[][] buf = new int[width][height];

        for (int cx = (x >> 4) - CALC_OFFSET; cx < (x >> 4) + CALC_OFFSET; cx++) {
            for (int cy = (y >> 4) - CALC_OFFSET; cy < (y >> 4) + CALC_OFFSET; cy++) {

                Point p = new Point(cx, cy);
                if (generatedChunks.contains(p)) // already generated
                    continue;

                generatedChunks.add(p);

                ArrayList<Point3D> voronoiPoints = new ArrayList<Point3D>();
                int num = random.nextInt(numPoints);
                for (int index = 0; index < num; index++) {
                    voronoiPoints.add(new Point3D(random(cx * width, cx * width + width), random(cy * width, cy * width + height), random.nextInt(height)));
                }
                voronoiChunks.add(new VoronoiChunk(cx, cy, voronoiPoints));
            }
        }

        for (VoronoiChunk c : voronoiChunks) {
            if (c.x > (x >> 4) - CALC_OFFSET && c.x < (x >> 4) + CALC_OFFSET && c.y > (y >> 4) - CALC_OFFSET && c.y < (y >> 4) + CALC_OFFSET) {
                currChunks.add(c);
            }
        }

        for (int dx = x; dx < x + width; dx++) {
            for (int dy = y; dy < y + height; dy++) {
                int lowestDistance = Integer.MAX_VALUE;
                for (VoronoiChunk voronoiChunk : currChunks) {
                    for (Point3D point : voronoiChunk.points) {
                        int temp = value(new Point(dx, dy), point);

                        if (temp > lowestDistance)
                            continue;
                        lowestDistance = temp;
                    }
                }
                buf[dx - x][dy - y] = lowestDistance;
            }
        }
        save();

        return buf;
    }

    private int random(int min, int max) {
        return min + (int) (random.nextDouble() * ((max - min) + 1));
    }

    private static int value(Point p1, Point3D p2) {
        int x = (int) (p2.x - p1.x), y = (int) (p2.y - p1.y);
        return x * x + y * y + p2.z * 25;
    }
}