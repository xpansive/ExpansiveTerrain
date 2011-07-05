package com.xpansive.bukkit.worldgen;


public class Perlin {
	public Perlin(){
		
	}
	
	public double Perlin_Noise(float x, float y,float zoom,int octaves,float frequency, float persistence){
		double getnoise=0;
		   for(int a=0;a<octaves-1;a++)//This loops trough the octaves.
		   {
			 double frequency2 = Math.pow(frequency,a);//This increases the frequency with every loop of the octave.
			 double amplitude = Math.pow(persistence,a);//This decreases the amplitude with every loop of the octave.
			 getnoise += noise(((double)x)*frequency2/zoom,((double)y)*frequency2/zoom)*amplitude;//*r.nextFloat();//This uses our perlin noise functions. It calculates all our zoom and frequency and amplitude
		   }

//		   int color= (int)((getnoise*128.0)+128.0);//Convert to 0-256 values.
//		   if(color>255){
//			color=255;
//		   }
//		   if(color<0){
//			color=0;
//		   }
//		 return color;
		 return getnoise;
	}
	

	public double interpolate(double a,double b,double x)
	{
	 double ft=x * 3.1415927;
	 double f=(1.0-Math.cos(ft))* 0.5;
	 return a*(1.0-f)+b*f;
	}


	
	public double noise(double x,double y)
	{
	 double floorx=(double)((int)x);//This is kinda a cheap way to floor a double integer.
	 double floory=(double)((int)y);
	 double s,t,u,v;//Integer declaration
	 s=findnoise2(floorx,floory); 
	 t=findnoise2(floorx+1,floory);
	 u=findnoise2(floorx,floory+1);//Get the surrounding pixels to calculate the transition.
	 v=findnoise2(floorx+1,floory+1);
	 double int1=interpolate(s,t,x-floorx);//Interpolate between the values.
	 double int2=interpolate(u,v,x-floorx);//Here we use x-floorx, to get 1st dimension. Don't mind the x-floorx thingie, it's part of the cosine formula.
	 return interpolate(int1,int2,y-floory);//Here we use y-floory, to get the 2nd dimension.
	}

	public double findnoise2(double x,double y)
	{
	 int n=(int)x+(int)y*57;
	 n=(n<<13)^n;
	 int nn=(n*(n*n*60493+19990303)+1376312589)&0x7fffffff;
	 return 1.0-(((double)nn/1073741824.0));
	}
}
