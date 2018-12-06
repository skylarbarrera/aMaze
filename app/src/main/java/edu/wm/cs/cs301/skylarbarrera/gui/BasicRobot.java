/**
 * @author skylarbarrera
 *
 */
package edu.wm.cs.cs301.skylarbarrera.gui;

import android.util.Log;

import edu.wm.cs.cs301.skylarbarrera.generation.CardinalDirection;
import edu.wm.cs.cs301.skylarbarrera.generation.Cells;
import edu.wm.cs.cs301.skylarbarrera.gui.Constants.UserInput;

import static java.lang.Thread.*;


public class BasicRobot  implements Robot {
	
	
	
	
	private PlayAnimationActivity control;
	private boolean sensorRoom = true;
	private boolean sensorForward = true;
	private boolean sensorBackward = true;
	private boolean sensorLeft = true;
	private boolean sensorRight = true;;
	private float BatteryLevel;
	private int Odometer;
	private float energyForTurn = 3;
	private float energyForMove = 5;
	private float energyForSensor = 1;
	private boolean Stopped = false;
	


	
	
	
	

	
	/**
	 * init
	 */
	public BasicRobot(PlayAnimationActivity controller) {
		BatteryLevel = 3000;
		control = controller;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Resets stopped to false after end of game
	 */
	@Override
	public void resetStopped() {
		Stopped = false;
	}
	
	/** 
	 * rotate - rotates robot and checks everything
	 * @param
	 */
	@Override
	public void rotate(Turn turn) {
		//System.out.println("rotate called ");
		//System.out.println("turn is  " + turn);
		if ( !hasStopped()) {
		if ( BatteryLevel >= energyForTurn) {
			switch(turn) {
			case LEFT:
				control.rotateWrap(1);
				BatteryLevel -= getEnergyForRotation();
				break;
			case RIGHT:
				control.rotateWrap(-1);
				System.out.println("afterTURN - " +control.getCurrentDirection());
				BatteryLevel -= getEnergyForRotation();
				break;
			case AROUND:
				if ( BatteryLevel >= energyForTurn*2) {
				control.rotateWrap(-1);
				control.rotateWrap(-1);
				BatteryLevel -= getEnergyForFullRotation();
				break;
				}
			}
		}
		
		//make sure battery level is still > 0 
		if (BatteryLevel <= 0) {
			Stopped = true;
		}
		//otherwise go to end Game screen
		} else {
			control.switchState2Winning();
		}
		Log.v("BasicRobot", "Finished Rotate");
	}

	
	/**
	 * Moves robot forward a given number of steps. A step matches a single cell.
	 * If the robot runs out of energy somewhere on its way, it stops, 
	 * which can be checked by hasStopped() == true and by checking the battery level. 
	 * If the robot hits an obstacle like a wall, it depends on the mode of operation
	 * what happens. If an algorithm drives the robot, it remains at the position in front 
	 * of the obstacle and also hasStopped() == true as this is not supposed to happen.
	 * This is also helpful to recognize if the robot implementation and the actual maze
	 * do not share a consistent view on where walls are and where not.
	 * If a user manually operates the robot, this behavior is inconvenient for a user,
	 * such that in case of a manual operation the robot remains at the position in front
	 * of the obstacle but hasStopped() == false and the game can continue.
	 * @param distance is the number of cells to move in the robot's current forward direction 
	 * @param manual is true if robot is operated manually by user, false otherwise
	 * @precondition distance >= 0
	 */
	@Override
	public void move(int distance, boolean manual) {
		//distance should be positive 
		try {sleep(500);}
		catch(Exception e){
			e.printStackTrace();
		}
		assert distance >= 0 : "distance is 0 or negative";
		//System.out.println("move called & intitiating");
		
		//make sure robot is still operating 		
		
		//negatively increment distance until 0, break if wall is found
		while (distance > 0 ) {
			//pull in new position and check for wall			//System.out.println("distance > 0");

			int[] curPosition;
			try {
				curPosition = getCurrentPosition();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Move Canceled");
				break;
			}
			boolean hasNoWall = control.getMazeConfiguration().getMazecells().hasNoWall(curPosition[0], curPosition[1], getCurrentDirection());
			if (!hasNoWall) {
				//if manual then just break, if robot - stop and break
				if (manual) {
					break;
				}else {
					Stopped = true;
					break;
				}
			}
			
			
			//check that we have enough battery
			//click input up, adjust battery level & odometer
			if ( BatteryLevel >= energyForMove) {
				control.walkWrap(1);
				BatteryLevel -= energyForMove;
				Odometer++;
			} else {
				//ran out of battery
				Stopped = true;
				break;
			}
			//increment distance after move
			distance--;
		}

		Log.v("BasicRobot", "Finished Move");
	}
	
	

	/**
	 * Provides the current position as (x,y) coordinates for the maze cell as an array of length 2 with [x,y].
	 * @postcondition 0 <= x < width, 0 <= y < height of the maze. 
	 * @return array of length 2, x = array[0], y=array[1]
	 * @throws Exception if position is outside of the maze
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		//pull in width & height from control
		
		int width = control.getMazeConfiguration().getWidth();
		int height = control.getMazeConfiguration().getHeight();
		int [] currentPosition = control.getCurrentPosition();
		if ( currentPosition[0] < 0 | currentPosition[0] >= width ) {
			throw new IndexOutOfBoundsException("Current x Position is outside of Maze");
			
		}
		if ( currentPosition[1] < 0 | currentPosition[1] >= height ) {
			throw new IndexOutOfBoundsException("Current y Position is outside of Maze");
		}
		return currentPosition;
	}

	
	/**
	 * Get Method to return Controller and all references 
	 * @return Controller

	public Controller getMaze() {
		assert(control != null);
		return control;
	}
	 */
	
	
	/**
	 * Provides the robot with a reference to the controller to cooperate with.
	 * The robot memorizes the controller such that this method is most likely called only once
	 * and for initialization purposes. The controller serves as the main source of information
	 * for the robot about the current position, the presence of walls, the reaching of an exit.
	 * The controller is assumed to be in the playing state.
	 * @param controller is the communication partner for robot
	 * @precondition controller != null, controller is in playing state and has a maze
	 */
	
	@Override
	public void setMaze(PlayAnimationActivity controller) {
		//check this assertion
		assert(controller != null);
		//System.out.println("setting reference of controller for robot");
		control = controller;
		// TODO Auto-generated method stub

	}

	/** 
	 * Checks if robot is at the exit location
	 **/
	@Override
	public boolean isAtExit() {
		
		
		
		int[] curPosition;
		try {
			curPosition = getCurrentPosition();
			int dist2Exit = control.getMazeConfiguration().getDistanceToExit(curPosition[0], curPosition[1]);
			
			
			
			//if dist is 0 we are at exit
			if (dist2Exit == 1) {
				return true;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("isAtExit invalid ");
			
		}
		//get distance to exit from current Position 
		
		//else no
		return false;
	}

	/** 
	 * checks if robot can see exit from current location 
	 */
	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		//distanceToObstacle will return MAX_VALUE if it can see the exit in the given direction
		int dist = distanceToObstacle(direction);
		
		//if we can see exit return true
		if (dist == Integer.MAX_VALUE) {
			System.out.println("Robot an see Exit");
			return true;
		} 
		return false;
	}

	
	/* (non-Javadoc)
	 * @see gui.Robot#isInsideRoom()
	 */
	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		
		//Throw exception if sensor doesnt exist 
		if (!hasRoomSensor()) {
			throw new UnsupportedOperationException("Robot does not have a room sensor");
		
		}
		//else  get curPosition and check if inside room 
		int[] curPosition;
		try {
			curPosition = getCurrentPosition();
			//let the controller do all the lifting 
			boolean isInside = control.getMazeConfiguration().getMazecells().isInRoom(curPosition[0], curPosition[1]);
			return isInside;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("isInsideRoom invalid");
		}
		
		//if we get to this point we have bigger  problems
		
		return false;
	}

	/* (non-Javadoc)
	 * @see gui.Robot#hasRoomSensor()
	 */
	@Override
	public boolean hasRoomSensor() {
		return sensorRoom;
	}

	/* (non-Javadoc)
	 * @see gui.Robot#getCurrentDirection()
	 */
	@Override
	public CardinalDirection getCurrentDirection() {

		return control.getCurrentDirection();
	}

	/* (non-Javadoc)
	 * @see gui.Robot#getBatteryLevel()
	 */
	@Override
	public float getBatteryLevel() {
		
		return BatteryLevel;
	}


	
	/* (non-Javadoc)
	 * @see gui.Robot#setBatteryLevel(float)
	 */
	@Override
	public void setBatteryLevel(float level) {
		 BatteryLevel  = level;
		 

	}

	/* (non-Javadoc)
	 * @see gui.Robot#getOdometerReading()
	 */
	@Override
	public int getOdometerReading() {
		
		return Odometer;
	}

	/* (non-Javadoc)
	 * @see gui.Robot#resetOdometer()
	 */
	@Override
	public void resetOdometer() {
		Odometer = 0;

	}

	/* (non-Javadoc)
	 * @see gui.Robot#getEnergyForFullRotation()
	 */
	@Override
	public float getEnergyForFullRotation() {
		
		return energyForTurn*2;
	}
	/**
	 * Extra Method added for readability
	 * 
	 * @return - energy needed for a turn 
	 */
	public float getEnergyForRotation() {
		
		return energyForTurn;
	}
	
	/**
	 * Extra Method added for readability
	 * @return - energy needed for a turn
	 */
	public float getEnergyForSensor() {
	
		return energyForSensor;
	}

	/* (non-Javadoc)
	 * @see gui.Robot#getEnergyForStepForward()
	 */
	@Override
	public float getEnergyForStepForward() {
		
		return energyForMove;
	}
	

	/* (non-Javadoc)
	 * @see gui.Robot#hasStopped()
	 */
	@Override
	public boolean hasStopped() {
		
		return Stopped;
	}

	/* (non-Javadoc)
	 * @see gui.Robot#distanceToObstacle(gui.Robot.Direction)
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		
		//Throw exception if sensor doesnt exist 
		if (!hasDistanceSensor(direction)) {
			throw new UnsupportedOperationException("Robot does not have a " + direction + " sensor");
		
		}
		
		// cells most likely control.mazeConfigureation.cells
		Cells cells = control.getMazeConfiguration().getMazecells();
		
		
		CardinalDirection curDirection = control.getCurrentDirection();
		CardinalDirection checkDirection = null;
		int[] calc = new int[2];
		int[] curPosition = null;
		try {
			curPosition = getCurrentPosition();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Use current Direction to calculate checkDirection and calc(used late for iteration purposes)
		//North & South are flipped
		switch (curDirection) {
		case North:
			switch(direction) {
			case LEFT:
				checkDirection = CardinalDirection.East;
				calc[0] = 1; 
				calc[1] = 0;
				break;
			
			case RIGHT:
				checkDirection = CardinalDirection.West;
				calc[0] = -1; 
				calc[1] = 0;
				break;
			case FORWARD:
				checkDirection = CardinalDirection.North;
				calc[0] = 0; 
				calc[1] = -1;
				break;
				
			case BACKWARD:
				checkDirection = CardinalDirection.South;
				calc[0] = 0; 
				calc[1] = 1;
				break;
			
			
			}
			break;
		case South:
			switch(direction) {
			case LEFT:
				checkDirection = CardinalDirection.West;
				calc[0] = -1; 
				calc[1] = 0;
				break;
			
			case RIGHT:
				checkDirection = CardinalDirection.East;
				calc[0] = 1; 
				calc[1] = 0;
				break;
			
			case FORWARD:
				checkDirection = CardinalDirection.South;
				calc[0] = 0; 
				calc[1] = 1;
				break;
				
			case BACKWARD:
				checkDirection = CardinalDirection.North;
				calc[0] = 0; 
				calc[1] = -1;
				break;
				
			
			}
			break;
		case East:
			switch(direction) {
			case LEFT:
				checkDirection = CardinalDirection.South;
				calc[0] = 0; 
				calc[1] = 1;
				break;
			
			case RIGHT:
				checkDirection = CardinalDirection.North;
				calc[0] = 0; 
				calc[1] = -1;
				break;
			
			case FORWARD:
				checkDirection = CardinalDirection.East;
				calc[0] = 1; 
				calc[1] = 0;
				break;
				
			case BACKWARD:
				checkDirection = CardinalDirection.West;
				calc[0] = -1; 
				calc[1] = 0;
				break;
			
			}
			break;
		case West:
			switch(direction) {
			case LEFT:
				checkDirection = CardinalDirection.North;
				calc[0] = 0; 
				calc[1] = -1;
				break;
			
			case RIGHT:
				checkDirection = CardinalDirection.South;
				calc[0] = 0; 
				calc[1] = 1;
				break;
			
			case FORWARD:
				checkDirection = CardinalDirection.West;
				calc[0] = -1; 
				calc[1] = 0;
				break;
				
				
			case BACKWARD:
				checkDirection = CardinalDirection.East;
				calc[0] = 1; 
				calc[1] = 0;
				break;
			
			
				
			
			}
			break;
		}
		//end of switch
		
		int dist = 0;
		
		try {
			curPosition = this.getCurrentPosition();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//While current position does not have a wall, continue iterating in that direction
		while(!cells.hasWall(curPosition[0], curPosition[1], checkDirection)) {
			
			//if dist is > width or height than we can see the exit
			//??? - May be smarter to check for border first and then do a simple check for wall
			if (checkDirection == CardinalDirection.East | checkDirection == CardinalDirection.West ) {
				if (dist > control.getMazeConfiguration().getWidth()) {
					return Integer.MAX_VALUE;
				}
			}
			
			if (checkDirection == CardinalDirection.North | checkDirection == CardinalDirection.South ) {
				if (dist > control.getMazeConfiguration().getHeight()) {
					return Integer.MAX_VALUE;
				}
			}
			//increment current position & distance variable 
			curPosition[0] += calc[0];
			curPosition[1] += calc[1];
			dist+= 1;
			
			//check if outside of borders
			if (curPosition[0] < 0 | curPosition[1] < 0 ) {
				return Integer.MAX_VALUE;
			}
			
		}
		
	
		return dist;
	}

	
	
	/* (non-Javadoc)
	 * @see gui.Robot#hasDistanceSensor(gui.Robot.Direction)
	 */
	@Override
	public boolean hasDistanceSensor(Direction direction) {
		boolean cur = false;
		switch (direction) {
		case LEFT:
			cur = sensorLeft;
		case RIGHT:
			cur =  sensorRight;
		case FORWARD:
			cur = sensorForward;
		case BACKWARD:
			cur =  sensorBackward;
		
	}
		return cur;	
	}
	

}
