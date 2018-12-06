package edu.wm.cs.cs301.skylarbarrera.gui;

//import java.awt.event.KeyListener;

import edu.wm.cs.cs301.skylarbarrera.generation.Cells;
import edu.wm.cs.cs301.skylarbarrera.generation.Distance;
import edu.wm.cs.cs301.skylarbarrera.gui.Constants.UserInput;
import edu.wm.cs.cs301.skylarbarrera.gui.Robot.Direction;
import edu.wm.cs.cs301.skylarbarrera.gui.Robot.Turn;

/**
 * @author skylarbarrera
 *
 */
public class WallFollower implements RobotDriver {

	
	private BasicRobot robot; 
	private int width;
	private int height;
	private Distance distance;
	

	private Direction left = Direction.LEFT;
	private Direction forW = Direction.FORWARD;
	
	/**
	 * @param r - for setting robot
	 */
	public WallFollower(Robot r) {
		setRobot(r);
		
	}
	
	public WallFollower(){
		
	}
	
	@Override
	public void setRobot(Robot r) {
		robot = (BasicRobot)r;
	}



	@Override
	public void setDimensions(int width, int height) {
		//make sure dimensions are in bounds 
		assert( 0 <= width && 0<= height);
		//set dimensions
		this.width = width;
		this.height = height;

	}

	@Override
	public void setDistance(Distance distance) {
		//Make sure distance object is not null
		assert(distance != null);
		//set distance
		this.distance = distance;

	}

	@Override
	public boolean drive2Exit() throws Exception {
		System.out.println("WallFollower is now driving");
		
		//record starting position for comparison 
		int[] sPos = robot.getCurrentPosition();
		
		//while we are not at exit 
		while (!robot.isAtExit()) {
			//if stopped end alg and fail
			if (robot.hasStopped()) {
				return false;
			}
			
			int distL = robot.distanceToObstacle(left);
			int distF;
			//if we can go left, do it so we can follow wall
			if (distL != 0) {
				robot.rotate(Turn.LEFT);
				distF = robot.distanceToObstacle(forW);
				
				//move towards obstacle, checking if theres a turn of after every move
				for ( int i = 0; i < distF;i++) {
					robot.move(1, false);
					distL = robot.distanceToObstacle(left);
					//checks if we pass a door, if so stop so we can reasses
					if (distL != 0) {
						System.out.println("breaking move");
						break;
					}
				}
				
				
				//if were on the wall we have to go forward
			} else if ( distL == 0 ) {
				//possible assertion but would use needless energy 
				//sets distance F
				distF = robot.distanceToObstacle(forW);
				//if both forward anf left are 0 we should turn right and reasses
				if ( distF == 0) {
					robot.rotate(Turn.RIGHT);
				}else 	if (distF != 0 ) {
					//move towards obstacle, checking if theres a turn of after every move
					for ( int i = 0; i < distF;i++) {
						robot.move(1, false);
						distL = robot.distanceToObstacle(left);
						if (distL != 0) {
							System.out.println("breaking move");
							break;
						}
					}
				} else { 
					//just in case rotate right
				robot.rotate(Turn.RIGHT);
				}
				
				
			}
			//reset after move to keep track 
			sPos = robot.getCurrentPosition();
		}
		
		//checks directions around robot and then moves into exit door
		atExit();
		
		return true;
	}
	
	/** 
	 * Called when robot is at exit
	 * checks directions around robot and then moves into exit door
	 */
	private void atExit() {
		if(robot.canSeeExit(Direction.LEFT)) {
			robot.rotate(Turn.LEFT);
			robot.move(1,false);
		}else if (robot.canSeeExit(Direction.BACKWARD)) {
			robot.rotate(Turn.AROUND);
			robot.move(1, false);
		} else if (robot.canSeeExit(Direction.FORWARD)) {
			
			robot.move(1, false);
			
		}else if (robot.canSeeExit(Direction.RIGHT)) {
			robot.rotate(Turn.RIGHT);
			robot.move(1, false);
		} else {
			System.out.println("ERROR: ROBOT IS NOT AT EXIT");
		}
		
	}

	@Override
	public float getEnergyConsumption() {
		return robot.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		return robot.getOdometerReading();
	}

}
