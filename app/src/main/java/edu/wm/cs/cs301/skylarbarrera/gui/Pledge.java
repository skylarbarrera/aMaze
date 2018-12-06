package edu.wm.cs.cs301.skylarbarrera.gui;

//import java.awt.event.KeyListener;

import edu.wm.cs.cs301.skylarbarrera.generation.CardinalDirection;
import edu.wm.cs.cs301.skylarbarrera.generation.Cells;
import edu.wm.cs.cs301.skylarbarrera.generation.Distance;
import edu.wm.cs.cs301.skylarbarrera.gui.Constants.UserInput;
import edu.wm.cs.cs301.skylarbarrera. gui.Robot.Direction;
import edu.wm.cs.cs301.skylarbarrera.gui.Robot.Turn;
//sPECIAL EFFECT
//one more
/**
 * @author skylarbarrera
 *
 */
public class Pledge implements RobotDriver {
	
    private BasicRobot robot; 
    private int width;
    private int height;
    private Distance distance;
    //private KeyListener kl;
    private Cells cells;
    //private Controller control;
    private Direction left = Direction.LEFT;
    private Direction forW = Direction.FORWARD;

	public Pledge(Robot r) {
		setRobot(r);
		//control = robot.getMaze();
	}
	
	public Pledge() {
		
	}

	@Override
	public void setRobot(Robot r) {
		robot = (BasicRobot)r;
		// TODO Auto-generated method stub

	}



	@Override
	public void setDimensions(int width, int height) {
		assert( 0 <= width && 0<= height);
		this.width = width;
		this.height = height;

	}

	@Override
	public void setDistance(Distance distance) {
		assert(distance != null);
		this.distance = distance;

	}
	
	/**
	 * Wall Follower Algorithm, to be called from Pledge Alg.
	 * Follows Wall and returns when sum of turns is = to 0
	 */
	public void WallFollowerTurnSum() {
		System.out.println("Starting Wall Follower");
		int angCount = 0;
		boolean equal = false;
		int[] sPos;
		try {
			sPos = robot.getCurrentPosition();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (!equal) {
			if (robot.hasStopped()) {
				return ;
			}
			if (angCount > 30 || angCount < -30) {
				return;
			}
			int distL = robot.distanceToObstacle(left);
			int distF;
			if (distL != 0) {
				robot.rotate(Turn.LEFT);
				angCount -= 1;
				System.out.println("angCount - " + angCount);
				if (angCount == 0) {
					return;
				}
				distF = robot.distanceToObstacle(forW);
				
				//move towards obstacle, checking if theres a turn of after every move
				for ( int i = 0; i < distF;i++) {
					robot.move(1, false);
					if (robot.isAtExit()) {
						return;
					}
					distL = robot.distanceToObstacle(left);
					if (angCount > 30) {
						return;
					}
					if (distL != 0) {
						System.out.println("breaking move");
						break;
					}
				}
				
				
				
			} else if ( distL == 0 ) {
				//possible assertion but would use needless energy 
				
				distF = robot.distanceToObstacle(forW);
				
				if ( distF == 0) {
					robot.rotate(Turn.RIGHT);
					angCount += 1;
					System.out.println("angCount - " + angCount);
					if (angCount == 0) {
						return;
					}
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
				robot.rotate(Turn.RIGHT);
				angCount += 1;
				System.out.println("angCount - " + angCount);
				if (angCount == 0) {
					return;
				}
				}
				
				
			}
			
			try {
				sPos = robot.getCurrentPosition();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(robot.canSeeExit(Direction.LEFT)) {
			robot.rotate(Turn.LEFT);
			robot.move(1,false);
		}else if (robot.canSeeExit(Direction.BACKWARD)) {
			robot.rotate(Turn.AROUND);
			robot.move(1, false);
		} else if (robot.canSeeExit(Direction.FORWARD)) {
			
			robot.move(1, false);
			
		}else  {
			robot.rotate(Turn.RIGHT);
			robot.move(1, false);
		}
		}
			
		
	

	/* (non-Javadoc)
	 * @see gui.RobotDriver#drive2Exit()
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		
		System.out.println("WallFollower is now driving");
		//cells = control.getMazeConfiguration().getMazecells();
		
		int[] sPos = robot.getCurrentPosition();
		//int angCount = 0;
		//boolean canEast = true;

		while (!robot.isAtExit()) {
			CardinalDirection cd = robot.getCurrentDirection();
			if (robot.hasStopped()) {
				return false;
			}
			
				switch(cd) {
				case North:
					int dirE = robot.distanceToObstacle(left);
					if ( dirE >0) {
					  robot.rotate(Turn.LEFT);
					  //angCount-=1;
					  robot.move(dirE, false);
					  //this.WallFollower();
					} 
					break;
				case South:
					robot.rotate(Turn.RIGHT);
					dirE = robot.distanceToObstacle(forW);
					if (dirE > 0 ) {
						
						 robot.move(dirE, false);
						 //this.WallFollower();
					}
					
					break;
				case East:
					dirE = robot.distanceToObstacle(forW);
					if (dirE > 0 ) {
						 robot.move(dirE, false);
						 //this.WallFollower();
					} 
					break;
				case West: 
					robot.rotate(Turn.AROUND);
					dirE = robot.distanceToObstacle(forW);
					if (dirE > 0 ) {
						 robot.move(dirE, false);
						 //this.WallFollower();
					}
					break;
				}
				
				if ( robot.distanceToObstacle(forW) == 0 ) {
					int distL = robot.distanceToObstacle(left);
					if(  distL != 0 ) {
						robot.rotate(Turn.LEFT);
						robot.move(distL, false);
						robot.rotate(Turn.RIGHT);
					}
				}
				this.WallFollowerTurnSum();
				if (robot.hasStopped()) {
					return false;
				}
				atExit();
				
			
			
			
		
		
	}
		return false;
	}
	
	
	/**
	 * 
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
			
		}else 
		{
			robot.rotate(Turn.RIGHT);
			robot.move(1, false);
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
