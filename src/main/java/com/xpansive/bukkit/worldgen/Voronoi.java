package com.xpansive.bukkit.worldgen;

import java.util.ArrayList;
import java.util.Random;

public class Voronoi {
	//private grid[][] g;
	private grid g;
	private Random r;
	private int count = 0;
	private int total = 0;
	private float size;
	public Voronoi(int size, long seed, int density){
		//g = new grid[size][size];
		g = new grid();
		r = new Random(seed);
		this.size = size;
		while(total < density){
			if(count ==10){
				r = new Random(r.nextLong());
				count = 0;
			}
			assign(new location(r.nextFloat()*size, r.nextFloat()*size));
			count++; total++;
		}
	}
	
	private void assign(location a){
		int x = (int) Math.floor(a.x);
		int y = (int) Math.floor(a.y);
//		if(g[x][y]==null){
//			g[x][y] = new grid();
//		}	
		//g[x][y].add(a);
		g.add(a);
	}
	
	private double distance(location a, location b){
		float x =b.x - a.x;
		float y =b.y - a.y;
		//double d =  Math.sqrt((x*x)+(y*y));		//Linear
		double d =  (x*x)+(y*y);		//Linear Squared (needs special care of gain) try .05f
		//double d = Math.abs(x)+Math.abs(y);   //Manhattan
		//if (Math.abs(x) == Math.abs(y) || Math.abs(x) < Math.abs(y)){return  Math.abs(x);}	if (Math.abs(x) > Math.abs(y)){return Math.abs(y);} // chebyshev
		//double d = ((x*x) + (x*y) + (y*y)); //quadratic try .08f for gain
		//float m = 15; //1 = manhattan 2 = linear squared 3 = more wiggly #2 4 = 
		//double d = Math.pow((Math.pow(Math.abs(x),m) + Math.pow(Math.abs(y),m)), (1 / m));
		return d;
	}
	
	private double check(int x, int y,double c,boolean f, int octave,int totaloctave){
		double distance = c;
		//find closest point
		x = Math.max(x, 0);
		y = Math.max(y, 0);
		x = (int) Math.min(x, size-1);
		y = (int) Math.min(y, size-1);
		//System.out.println(g[x][y]);
//		if(g[x][y]==null){
//			return c;
//		}else{		
			for(int i = 0; i < (octave*g.a.size()/totaloctave); i++){
				if(i == 0 && f){
					distance = distance(new location(x,y),g.a.get(i));
				}else{
					//distance =Math.min(distance(new location(x,y),g.a.get(i)),distance);
					distance =Math.min(distance(new location(x,y),g.a.get(i)),distance);
				}
			}
			return distance;
		//}
	}
	
	double get(int xin, int yin, int octaves, float gain){
		int x = (int) Math.floor(xin);
		int y = (int) Math.floor(yin);
		double distance = 10000000;
		double result = 0;
		gain = gain/octaves;
		for(int i = 1; i < octaves+1; i++){
			result+=(((check(x,y,distance,true,i,octaves)/(size/gain))*2)-1)/octaves;
			gain += gain/octaves;
		}


//		distance = check(x+1,y+1,distance,false);
//		distance = check(x+1,y,distance,false);
//		distance = check(x+1,y-1,distance,false);
//		distance = check(x,y+1,distance,false);
//		distance = check(x,y-1,distance,false);
//		distance = check(x-1,y+1,distance,false);
//		distance = check(x-1,y+1,distance,false);
//		distance = check(x-1,y+1,distance,false);
		//System.out.println(distance);
		return result;
		//return Math.min(1,Math.max(30/distance,-1));
		//return  Math.abs(((distance/(size/4))*2)-1);
	}
}

class grid{
	ArrayList<location> a = new ArrayList<location>();
	public grid(){
		
	}
	
	public void add(location a2){
		a.add(a2);
	}

}

class location{
	float x;
	float y;
	public location(float x, float y){
		this.x=x;
		this.y=y;
	}
}

//NOTES
//void voronoi(float3 position, out float f1, out float3 pos1, out float f2, out float3 pos2, float jitter=.9, bool manhattanDistance = false )
//{
//  float3 thiscell = floor(position)+.5;
//  f1 = f2 = 1000;
//  float i, j, k;
//  
//  float3 c;
//  for(i = -1; i <= 1; i += 1)
//  {
//    for(j = -1; j <= 1; j += 1)
//    {
//      for(k = -1; k <= 1; k += 1)
//      {
//        float3 testcell = thiscell  + float3(i,j,k);
//        float3 randomUVW = testcell * float3(0.037, 0.119, .093);
//        float3 cellnoise = perm(perm2d(randomUVW.xy)+randomUVW.z);
//        float3 pos = testcell + jitter*(cellnoise-.5);
//        float3 offset = pos - position;
//        float dist;
//        if(manhattanDistance)
//          dist = abs(offset.x)+abs(offset.y) + abs(offset.z); 
//        else
//          dist = dot(offset, offset);
//        if(dist < f1)
//        {
//          f2 = f1;
//          pos2 = pos1;
//          f1 = dist;
//          pos1 = pos;
//        }
//        else if(dist < f2)
//        {
//          f2 = dist;
//          pos2 = pos;
//        }
//      }
//    }
//  }
//  if(!manhattanDistance)
//  {
//    f1 = sqrt(f1);
//    f2 = sqrt(f2);
//  }
//}
//http://www.gamedev.net/community/forums/mod/journal/journal.asp?jn=339903&reply_id=3089065

