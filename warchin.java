package roborumble;

//import all the robocode package;
import robocode.*;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

//import java.awt.Color;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Warchin - a robot by (Jose Lo Mantilla)
 */
public class Warchin extends Robot
{
	/**
	 * run: Warchin's default behavior
	 */
	int count = 0; // Keeps track of how long we've
	// been searching for our target
	double atomic_gun; // How much to turn our gun when searching
	String trackName; // Name of the robot we're currently tracking
	double movement;
	int turnDirection = 1; // Clockwise or counterclockwise
	public void run() {
		// Initialization of the robot should be put here
		movement = Math.max(getBattleFieldWidth(),getBattleFieldHeight());

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar
		setBodyColor(new Color(255, 190, 11));
		setGunColor(new Color(57, 0,153));
		setRadarColor(new Color(57, 0, 153));
		setScanColor(Color.white);
		setBulletColor(Color.blue);

		// Robot main loop
		// Prepare gun
		trackName = null; // Initialize to not tracking anyone
		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		atomic_gun = 30; // Initialize gunTurn to 30
        
		
		
		turnLeft(getHeading()% 90);
		ahead(movement/2);
		turnRight(90);
		
		while(true) {
		//ahead(movement/2);
		//back(movement/2);
		turnGunRight(30);
		turnLeft(30);
		ahead(100);
		
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	
        // Calculate exact location of the robot
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
		
		// If it's close enough, fire!
		if (Math.abs(bearingFromGun) <= 3) {
			turnGunRight(bearingFromGun);
			// We check gun heat here, because calling fire()
			// uses a turn, which could cause us to lose track
			// of the other robot.
			if (getGunHeat() == 0) {
				fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
			}
		} // otherwise just set the gun to turn.
		// Note:  This will have no effect until we call scan()
		
		// If we don't have a target, well, now we do!
		if (trackName == null) {
			trackName = e.getName();
			out.println("Tracking " + trackName);
		}
		
		

		else {
			turnGunRight(bearingFromGun);
		}
		// Generates another scan event if we see a robot.
		// We only need to call this if the gun (and therefore radar)
		// are not turning.  Otherwise, scan is called automatically.
		if (bearingFromGun == 0) {
			scan();
		}
	}


	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitRobot(HitRobotEvent e) {
		 double bearing = e.getBearing(); //look where the hit come from
		 atomic_gun = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		 turnGunRight(atomic_gun);
		 fire(2);

	
	}
	/**
	 * onHitByBullet:  Turn perpendicular to the bullet, and move a bit.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		 double bearing = e.getBearing(); //look where the bullet come from
		 atomic_gun = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		 
		 turnGunRight(atomic_gun);
		 fire(3);
		 
		 if (Math.abs(bearing) < 45 && Math.abs(bearing) < 135){
			  turnRight(90 -  bearing); //turn to right or left depending the angle
			  ahead(200);
			  
		 }
		 
		 else{
			 
			 ahead(200);
			 back(200);
		 }
         //
         //ahead(200); // move ahead 200 
		 
		
	}
	

	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		 double hit_wall = e.getBearing(); //look where the wall is
		 double absoluteBearing = getHeading() + e.getBearing();
		 double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
    
         turnRight(90 - hit_wall); //turn to right or left depending the angle
         ahead(100); // move ahead 200
		 turnGunRight(bearingFromGun + 180);
		
	}	
	
	
 
	/**
	 * onWin:  Do a victory dance
	 */
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
		turnRight(360);
		turnGunLeft(360);
		
		}
	
	}
   

}

