package com.xpansive.bukkit.worldgen.util;

import java.io.Serializable;

public class Point3D implements Serializable {
	private static final long serialVersionUID = 1L;
	int x, y, z;

	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}