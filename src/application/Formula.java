package application;

public class Formula
{
	private double initHeight;
	private double initVelocity;
	private final int acceleration;

	public Formula(double initHeight, double initVelocity)
	{
		this.initHeight = initHeight;
		this.initVelocity = initVelocity;
		this.acceleration = -32;
	}
	
	public double getInitHeight()
	{
		return this.initHeight;
	}
	
	public double getInitVelocity()
	{
		return this.initVelocity;
	}
	
	public double getAcceleration()
	{
		return this.acceleration;
	}

	public String getHeightFormula()
	{
		return (this.initHeight == (int) this.initHeight ? String.format("%d",(int) this.initHeight) : String.format("%s",this.initHeight)) + " + " + 
			   (this.initVelocity == (int) this.initVelocity ? String.format("%d",(int) this.initVelocity) : String.format("%s",this.initVelocity)) + "t - 16t²";
	}
	
	public String getVelocityFormula()
	{
		return (this.initVelocity == (int) this.initVelocity ? String.format("%d",(int) this.initVelocity) : String.format("%s",this.initVelocity)) + " - 32t";
	}
	
	public String getHeightFormula(double time)
	{
		return (this.initHeight == (int) this.initHeight ? String.format("%d",(int) this.initHeight) : String.format("%s",this.initHeight)) + " + " + 
			   (this.initVelocity == (int) this.initVelocity ? String.format("%d",(int) this.initVelocity) : String.format("%s",this.initVelocity)) + "(" + time + ") - 16(" + time + ")²";
	}
	
	public String getVelocityFormula(double time)
	{
		return (this.initVelocity == (int) this.initVelocity ? String.format("%d",(int) this.initVelocity) : String.format("%s",this.initVelocity)) + " - 32(" + time + ")";
	}
	
	public String getTimeFormula(double a)
	{
		return null;
	}
	
	public double getHeightAtTime(double time)
	{
		return this.initHeight + (this.initVelocity * time) + (-16 * time * time);
	}
	
	public double getHeightAtVelocity(double velocity)
	{
		double time = getTimeAtVelocity(velocity);
		System.out.println(time);
		return getHeightAtTime(time);
	}
	
	public double getVelocityAtTime(double time)
	{
		return this.initVelocity + (this.acceleration * time);
	}
	
	public double[] getVelocityAtHeight(double height)
	{
		double times[] = getTimeAtHeight(height);

		double[] velocities = new double[times.length];
		
		for (int i = 0; i < velocities.length; i++)
			velocities[i] = getVelocityAtTime(times[i]);
		
		return velocities;
	}
	
	public double[] getTimeAtHeight(double height)
	{
		double time1;
		time1 = Math.sqrt(Math.pow(this.initVelocity, 2) - 4 * -16 * (this.initHeight - height));
		time1 = -this.initVelocity - time1;
		time1 /= this.acceleration;
		time1 = Math.round(time1 * 100) / 100.0;
		
		double time2;
		time2 = Math.sqrt(Math.pow(this.initVelocity, 2) - 4 * -16 * (this.initHeight - height));
		time2 = -this.initVelocity + time2;
		time2 /= this.acceleration;
		time2 = Math.round(time2 * 100) / 100.0;
		
		double[] times;
		if (time1 == time2)
			times = new double[]{time1};
		else
			times = new double[]{time1, time2};
		
		return times;
	}
	
	public double getTimeAtVelocity(double velocity)
	{
		return (velocity - this.initVelocity) / this.acceleration;
	}
	
	public double getMaxHeight()
	{
		return getHeightAtVelocity(0);
	}
	
	public double getTimeToMaxHeight()
	{
		return getTimeAtVelocity(0);
	}
	
	public double getTimeToStartPosition()
	{
		return 2 * getTimeToMaxHeight();
	}
	
	public double getTimeToGround()
	{
		return Math.round(getTimeAtHeight(0)[0] * 100) / 100.0;
	}
	
	public double getVelocityAtGround()
	{
		return Math.round(getVelocityAtHeight(0)[0] * 100) / 100.0;
	}
}
