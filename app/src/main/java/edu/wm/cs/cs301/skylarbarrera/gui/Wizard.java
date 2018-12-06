package edu.wm.cs.cs301.skylarbarrera.gui;


import edu.wm.cs.cs301.skylarbarrera.generation.CardinalDirection;
import edu.wm.cs.cs301.skylarbarrera.generation.Cells;
import edu.wm.cs.cs301.skylarbarrera.generation.Distance;
import edu.wm.cs.cs301.skylarbarrera.generation.MazeConfiguration;
import edu.wm.cs.cs301.skylarbarrera.gui.Constants.UserInput;
import edu.wm.cs.cs301.skylarbarrera.gui.Robot.Direction;
import edu.wm.cs.cs301.skylarbarrera.gui.Robot.Turn;

public class Wizard implements RobotDriver {
	
	private BasicRobot robot; 
	//private Cells cells;
	private PlayAnimationActivity control;
	private int width;
	
	private int height;
	private Distance distance;
	
	
	public Wizard(Robot r) {
		setRobot(r);		
	}
	
	public Wizard() {
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see gui.RobotDriver#setRobot(gui.Robot)
	 */
	@Override
	public void setRobot(Robot r) {
		robot = (BasicRobot)r;
		//use the robot to get the control and with it Maze data
		//control =  robot.getMaze();
		//cells = control.getMazeConfiguration().getMazecells();
	}



	/* (non-Javadoc)
	 * @see gui.RobotDriver#setDimensions(int, int)
	 */
	@Override
	public void setDimensions(int width, int height) {
		//make sure dimensions are in range
		assert( 0 <= width && 0<= height);
		//set them 
		this.width = width;
		this.height = height;

	}

	/* (non-Javadoc)
	 * @see gui.RobotDriver#setDistance(generation.Distance)
	 */
	@Override
	public void setDistance(Distance distance) {
		//make sure distance object exists
		assert(distance != null);
		//set distance
		this.distance = distance;

	}
	
	
	/**
	 * Makes more sense to have this method here rather than getting a reference to its original class and then calling it
	 * Gives a (x',y') neighbor for given (x,y) that is closer to exit
	 * if it exists. 
	 * @param x is on the horizontal axis, {@code 0 <= x < width}
	 * @param y is on the vertical axis, {@code 0 <= y < height}
	 * @return array with neighbor coordinates if neighbor exists, null otherwise
	 */
	private int[] getNeighborCloserToExit(int x, int y) {
		//assert isValidPosition(x,y) : "Invalid position";
		// corner case, (x,y) is exit position
		if (robot.isAtExit())
			return null;
		// find best candidate
		int dnext = control.getMazeConfiguration().getDistanceToExit(x, y) ;
		int[] result = new int[3] ;
		int[] dir;
		for (CardinalDirection cd: CardinalDirection.values()) {
			if (control.getMazeConfiguration().getMazecells().hasWall(x, y, cd)) 
				continue; // there is a wall
			// no wall, let's check the distance
			dir = cd.getDirection();
			int dn = control.getMazeConfiguration().getDistanceToExit(x+dir[0], y+dir[1]);
			System.out.println("diection - " + cd+ " distance to exit - "+ dn);
			
			if (dn < dnext) {
				// update neighbor position with min distance
				result[0] = x+dir[0] ;
				result[1] = y+dir[1] ;
				switch(cd) {
				case South:
					result[2] = 0;
					break;
				case North:
					result[2] = 1;
					break;
				case East:
					result[2] = 2;
					break;
				case West:
					result[2] = 3;
					break;
				
				}
				dnext = dn ;
			}	
		}
		// expectation: we found a neighbor that is closer
		assert(control.getMazeConfiguration().getDistanceToExit(x, y) > dnext) : 
			"cannot identify direction towards solution: stuck at: " + x + ", "+ y ;
		// since assert statements need not be executed, check it 
		// to avoid giving back wrong result
		return (control.getMazeConfiguration().getDistanceToExit(x, y) > dnext) ? result : null;
	}
	
	/**
	 * checks current direction and rotates to face in the direction of cd
	 * @param cd - Cardinal Direction we want the robot to face
	 * 
	 */
	private void rotateTillRight(CardinalDirection cd) {
		//get current direction so we know how to turn 
		CardinalDirection curD = robot.getCurrentDirection();
		
		//based on out cur direction, turn to face gice direction cd
		switch (curD) {
		case North:
			switch(cd) {
			case North:
				
				break;
			
			case South:
				robot.rotate(Turn.AROUND);
				break;
			case East:
				robot.rotate(Turn.LEFT);
				break;
				
			case West:
				robot.rotate(Turn.RIGHT);
				break;
			
			}
			break;
		case South:
			switch(cd) {
			case North:
				robot.rotate(Turn.AROUND);
				break;
			
			case South:
				
				break;
			case East:
				robot.rotate(Turn.RIGHT);
				break;
				
			case West:
				robot.rotate(Turn.LEFT);
				break;
			
			}
			break;
		case East:
			switch(cd) {
			case North:
				robot.rotate(Turn.RIGHT);
				break;
			
			case South:
				robot.rotate(Turn.LEFT);
				break;
			case East:
				
				break;
				
			case West:
				robot.rotate(Turn.AROUND);
				break;
			
			}
			break;
		case West:
			switch(cd) {
			case North:
				robot.rotate(Turn.LEFT);
				break;
			
			case South:
				robot.rotate(Turn.RIGHT);
				break;
			case East:
				robot.rotate(Turn.AROUND);
				break;
				
			case West:
				
				break;
			
			}
		}

		
	}
	
	@Override
	public boolean drive2Exit() throws Exception {
		System.out.println("Wizard is now driving");
	
		//get starting position and set variable to direction cd
		int[] sPos = robot.getCurrentPosition();
		CardinalDirection cd = null;
		
		//while robot is not at the exit continue moving around
		while (!robot.isAtExit()) {
			if (robot.hasStopped()) {
				return false;
			}
			//get next cell based on dist to exit
		int[] nPos = this.getNeighborCloserToExit(sPos[0], sPos[1]);
		System.out.println("sPos - " + sPos[0] + ", " + sPos[1]);
		System.out.println("nPos - " + nPos[0] + ", " + nPos[1]);
		if ( nPos[2] == 2) {
			cd = CardinalDirection.East;
		} else if (  nPos[2] == 3){
			cd = CardinalDirection.West;
		} else if ( nPos[2] == 0) {
			cd = CardinalDirection.South;
		}else if (  nPos[2] == 1) {
			cd = CardinalDirection.North;
		}
		System.out.println("rotating to - " + cd);
		
		//rotate till facing correct way 
		this.rotateTillRight(cd);
		
		
		//move to next position and then reset
		robot.move(1, false);
		
		//update starting position for next iteration
		sPos = robot.getCurrentPosition();
	
		
		

		
		
		}
		//if were at the exit we need to face the exit and then go through it
		if(robot.canSeeExit(Direction.FORWARD)) {
			robot.move(1,false);
		}else if (robot.canSeeExit(Direction.BACKWARD)) {
			robot.rotate(Turn.AROUND);
			robot.move(1, false);
		} else if (robot.canSeeExit(Direction.LEFT)) {
			robot.rotate(Turn.LEFT);
			robot.move(1, false);
			
		}else {
			robot.rotate(Turn.RIGHT);
			robot.move(1, false);
		}
	
		
		
		
		return true;
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
