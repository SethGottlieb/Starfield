import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Starfield extends PApplet {

Particle[] Stars;
public void setup()
{
	size(1280, 1024);
	Stars = new Particle[5000];
	for (int i = 0; i < Stars.length; i++) 
	{
		Stars[i] = new NormalParticle();
	}
	Stars[1] = new OddballParticle();
	Stars[2] = new JumboParticle();
}
public void draw()
{
	if (mousePressed == false)
	{
		background(0);	
	}
	for (int i = 0; i < Stars.length; i++) 
	{
		Stars[i].move();
		Stars[i].wrap();
		Stars[i].show();
		Stars[i].blackhole();
	}
}
class NormalParticle implements Particle
{
	double pX, pY, pSize, pSpeed, pTheta;
	int pR, pG, pB;
	NormalParticle()
	{
		pX = width/2;
		pY = height/2;
		pSize = Math.random()*6+1;
		pSpeed = Math.random()*10;
		pTheta = Math.random()*360+1;
		pR = (int)(Math.random()*255+1);
		pG = (int)(Math.random()*255+1);
		pB = (int)(Math.random()*255+1);
	}
	public void move()
	{
		pX += Math.cos(pTheta) * pSpeed;
		pY += Math.sin(pTheta) * pSpeed;
	}
	public void wrap()
	{
		if(pX > width || pX < 0 || pY > height || pY < 0)
		{
			pX = width/2;
			pY = height/2;
			pSpeed = Math.random()*5;
			pTheta = Math.random()*360+1;
		}
	}
	public void show()
	{
		noStroke();
		fill(pR, pB, pG, 175);
		ellipse((float)pX, (float)pY, (float)pSize, (float)pSize);
	}
	public void blackhole()
	{
		if(get((int)pX + (int)pSize, (int)pY + (int)pSize) == color(5)) 
		{
			pR = 0; 
			pB = 0;
			pG = 0;
		}
	}
}
interface Particle
{
	public void move();
	public void wrap();
	public void show();
	public void blackhole();
}
class OddballParticle implements Particle // Oddball is a blackhole, sucks in stars
{
	double pX, pY, pSize, pSpeed, pTheta;
	OddballParticle()
	{
		pX = Math.random()*(width-50) + 50;
		pY = Math.random()*(height-50) + 50;
		pSize = Math.random()*100 + 30;
		pSpeed = Math.random()*50 + 5;
		pTheta = Math.random()*360 + 1;
	}
	public void move()
	{
		pX += Math.cos(pTheta) * pSpeed;
		pY += Math.sin(pTheta) * pSpeed;
	}
	public void wrap()
	{
		if((pX + pSize/2) > width || (pX - pSize/2) < 0) 
		{
			pSpeed = -pSpeed;
			pTheta = -pTheta;
		}
		if((pY + pSize/2) > height || (pY - pSize/2) < 0)
		{
			pSpeed = -pSpeed;
			pTheta = pTheta + 90;
		}
	}
	public void show()
	{
		fill(5);
		ellipse((float)pX, (float)pY, (float)pSize, (float)pSize);
	}
	public void blackhole()
	{

	}
}
class JumboParticle extends NormalParticle
{
	JumboParticle()
	{
		pSize = Math.random()*70 + 30;
	}
	public void wrap()
	{
		if(pX-pSize/2 > width || pX+pSize/2 < 0 || pY-pSize/2 > height || pY+pSize/2 < 0)
		{
			pSize += 10;
		}
	}
	public void show()
	{
		fill(pR, pB, pG, 150);
		ellipse((float)pX, (float)pY, (float)pSize, (float)pSize);
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Starfield" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
