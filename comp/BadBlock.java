package comp;
import robocode.*;
import java.awt.Color;

/**
 * BadBlock - a robot by Alexander Diniz dos Santos
 */
public class BadBlock extends AdvancedRobot
{
	private byte direction = 1, rad=1;
	public void run(){
		setColors(Color.black,Color.blue,Color.green);
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		while(true) {
			turnRadarRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (getVelocity() == 0)
			direction*=-1;
		turnRadarRight((getHeading() - getRadarHeading()
						+ e.getBearing())*rad);
		turnGunRight(getHeading() - getGunHeading() + e.getBearing());
		if(getOthers() > 7)		
		{
			turnRight(e.getBearing() + 90);
			ahead(1000+direction);
			if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10)
				fire(Math.min(400 / e.getDistance(), 3));
		}		
		else{
			if (getTime() % 20 == 0) {
				direction *= -1;
				ahead(150 * direction);
			}
			turnRight(normalizeBearing(e.getBearing() + 90 - (15 * direction)));
			ahead(100*direction);
			if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 7)
				fire(Math.min(400 / e.getDistance(), 3));
		}
		rad *= -1;
	}

	double normalizeBearing(double angle) {
		while (angle >  180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		setAllColors(Color.red);
		direction *= -1;
	}
	
	public void onHitRobot(HitRobotEvent e) {
		setAllColors(Color.green);
		direction*=-1;
	}

	public void onHitWall(HitWallEvent e) {
		setAllColors(Color.red);
		direction *= -1;
	}	
}
