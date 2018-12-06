package edu.wm.cs.cs301.skylarbarrera.gui;

//import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wm.cs.cs301.skylarbarrera.generation.CardinalDirection;
import edu.wm.cs.cs301.skylarbarrera.generation.Cells;
import edu.wm.cs.cs301.skylarbarrera.generation.Distance;
import edu.wm.cs.cs301.skylarbarrera.generation.SingleRandom;
import edu.wm.cs.cs301.skylarbarrera.gui.Constants.UserInput;
import edu.wm.cs.cs301.skylarbarrera.gui.Robot.Direction;
import edu.wm.cs.cs301.skylarbarrera.gui.Robot.Turn;

/**
 * @author skylarbarrera
 *
 */
public class Explorer implements RobotDriver {

	private BasicRobot robot; 
	private int width;
	private int height;
	private Distance distance;
	private Direction left = Direction.LEFT;
	private Direction right = Direction.RIGHT;
	private Direction forW = Direction.FORWARD;
	private Direction back = Direction.BACKWARD;
	private int[][] visit;
	
	
	/**
	 * INIT 
	 *
	 */
	public Explorer(Robot r) {
		setRobot(r);
		
		
	}
	
	public Explorer() {
		//better set robot before you do anything 
	}
		
	

	@Override
	public void setRobot(Robot r) {
		//Make sure robot is not null
		assert(r != null);
		//set robot
		robot = (BasicRobot)r;

	}



	@Override
	public void setDimensions(int width, int height) {
		//Make sure dimensions are in bounds
		assert( 0 <= width && 0<= height);
		//set dimensions
		this.width = width;
		this.height = height;

	}

	@Override
	public void setDistance(Distance distance) {
		//Make sure distance object is not Null
		assert(distance != null);
		//set distance
		this.distance = distance;


	}
	
	/**
	 * @param cd - Cardinal Direction we want the robot to face
	 * checks current direction and rotates to face in the direction of cd
	 */
	private void rotateTillRight(CardinalDirection cd) {
		//Get current direction so we know what turns to make to face the right way
		CardinalDirection curD = robot.getCurrentDirection();
		//switch to check cases of which direction and then rotates as needed
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
		System.out.println("WallFollower is now driving");
		
		
		visit = new int[100][100];
		boolean curCounted = false;
		
		//record starting position and mark it as visited
		int[] sPos = robot.getCurrentPosition();
		visit[sPos[0]][sPos[1]] += 1;
		curCounted = true;
		int[] possMoves = new int[4];
		
		
		
		//while were not at exit
		while (!robot.isAtExit()) {
			//while we arent inside room we will do certain things
		while (!robot.isInsideRoom()) {
			//if robot stopped return false and fail 
			if (robot.hasStopped()) {
				return false;
			}
			sPos = robot.getCurrentPosition();
			//checks if were at exit and breaks to reset loop and recheck condition 
			if (robot.isAtExit()) {
				break;
			}
			//check surround directions
			CardinalDirection cd = robot.getCurrentDirection();
			int distF = robot.distanceToObstacle(forW);
			int distB = robot.distanceToObstacle(back);
			int distL = robot.distanceToObstacle(left);
			int distR = robot.distanceToObstacle(right);
			
			//set distances in directions to be high so we can do less than them 
			
			int visF = 999;
			int visB = 999;
			int visL = 999;
			int visR = 999;
			int picked = 999;
			int[] chosen = new int[2];
			CardinalDirection chosenD = null;
			//action depends on current direction
			//based on direction robot is facing, will set chosen cell values and compare the amount of visits
			//keep track of the least visited while comparing all new references
			switch(cd) {
			case North: 
			
				if ( distF > 0 ) {
					possMoves[0] = 1;
					visF = visit[sPos[0]][sPos[1]-1];
					chosen[0] = sPos[0];
					chosen[1] = sPos[1]-1;
					picked =  visF ;
					chosenD = CardinalDirection.North;
	
					
				}
				if ( distB > 0 ) {
					possMoves[1] = 1;
					visB = visit[sPos[0]][sPos[1]+1];
					if (visB < picked) {
						picked = visB;
						chosen[0] = sPos[0] ;
						chosen[1] =  sPos[1]+1;
						chosenD = CardinalDirection.South;

					} else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visB;
							chosen[0] = sPos[0] ;
							chosen[1] =  sPos[1] +1;
							chosenD = CardinalDirection.South;

 						} 
					}
			
				}
				if ( distL > 0 ) {
					possMoves[2] = 1;
					visL = visit[sPos[0]+1][sPos[1]];
					if (visL < picked) {
						picked = visL;
						chosen[0] = sPos[0]+1;
						chosen[1] = sPos[1];
						chosenD = CardinalDirection.East;

					}else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visL;
							chosen[0] = sPos[0] +1;
							chosen[1] =  sPos[1] ;
							chosenD = CardinalDirection.East;
 						} 
						
					}
					
				}
				if ( distR > 0 ) {
					possMoves[3] = 1;
					visR = visit[sPos[0]-1][sPos[1]];
					if ( visR < picked) {
						picked = visR;
						chosen[0] = sPos[0]-1;
						chosen[1]= sPos[1] ;
						chosenD = CardinalDirection.West;
					} else if( visR == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visR;
							chosen[0] = sPos[0] -1;
							chosen[1] =  sPos[1] ;
							chosenD = CardinalDirection.West;
 						} 
						
					}
					
				}
				
				break;
				
			case South:
				
				if ( distF > 0 ) {
					possMoves[0] = 1;
					visF = visit[sPos[0]][sPos[1]+1];
					chosen[0] = sPos[0];
					chosen[1] = sPos[1]+1;
					picked =  visF ;
					chosenD = CardinalDirection.South;
					

				}
				if ( distB > 0 ) {
					possMoves[1] = 1;
					visB = visit[sPos[0]][sPos[1]-1];
					if (visB < picked) {
						picked = visB;
						chosen[0] = sPos[0] ;
						chosen[1] =  sPos[1]- 1;
						chosenD = CardinalDirection.North;
					} else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visB;
							chosen[0] = sPos[0];
							chosen[1] =  sPos[1] - 1 ;
							chosenD = CardinalDirection.North;
 						} 
					}
					
				}
				if ( distL > 0 ) {
					possMoves[2] = 1;
					visL = visit[sPos[0]-1][sPos[1]];
					if (visL < picked) {
						picked = visL;
						chosen[0] = sPos[0]-1;
						chosen[1] = sPos[1];
						chosenD = CardinalDirection.West;
					}else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visL;
							chosen[0] = sPos[0]-1 ;
							chosen[1] =  sPos[1] ;
							chosenD = CardinalDirection.West;
 						} 
						
					}
					
				}
				if ( distR > 0 ) {
					possMoves[3] = 1;
					visR = visit[sPos[0]+1][sPos[1]];
					if ( visR < picked) {
						picked = visR;
						chosen[0] = sPos[0]+1;
						chosen[1]= sPos[1] ;
						chosenD = CardinalDirection.East;
					} else if( visR == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visR;
							chosen[0] = sPos[0] +1;
							chosen[1] =  sPos[1] ;
							chosenD = CardinalDirection.East; 						} 
						
					}
				}
				break;
				
			case East:
				if ( distF > 0 ) {
					possMoves[0] = 1;
					visF = visit[sPos[0]+1][sPos[1]];
					chosen[0] = sPos[0]+1;
					chosen[1] = sPos[1];
					picked =  visF ;
					chosenD = CardinalDirection.East;
				}
				if ( distB > 0 ) {
					//System.out.println("facing East - distB = " + distB);
					possMoves[1] = 1;
					visB = visit[sPos[0]-1][sPos[1]];
					if (visB < picked) {
						picked = visB;
						chosen[0] = sPos[0] -1;
						chosen[1] =  sPos[1];
						chosenD = CardinalDirection.West;
					} else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visB;
							chosen[0] = sPos[0] -1;
							chosen[1] =  sPos[1] ;
							chosenD = CardinalDirection.West;
 						} 
					}
				}
				if ( distL > 0 ) {
					possMoves[2] = 1;
					visL = visit[sPos[0]][sPos[1]+1];
					if (visL < picked) {
						picked = visL;
						chosen[0] = sPos[0];
						chosen[1] = sPos[1]+1;
						chosenD = CardinalDirection.South;
						
					}else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visL;
							chosen[0] = sPos[0] ;
							chosen[1] =  sPos[1] +1;
							chosenD = CardinalDirection.South;

 						} 
						
					}
				}
				if ( distR > 0 ) {
					possMoves[3] = 1;
					visR = visit[sPos[0]][sPos[1]-1];
					if ( visR < picked) {
						picked = visR;
						chosen[0] = sPos[0];
						chosen[1]= sPos[1]-1 ;
						chosenD = CardinalDirection.North;
					} else if( visR == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						//System.out.println("random called - " + rand);
						if ( rand == 0) {
							picked = visR;
							chosen[0] = sPos[0] ;
							chosen[1] =  sPos[1]-1 ;
							chosenD = CardinalDirection.North;
 						} 
						
					}
					
				}
				break;
				
			case West:
				
				if ( distF > 0 ) {
					possMoves[0] = 1;
					visF = visit[sPos[0]-1][sPos[1]];
					chosen[0] = sPos[0]-1;
					chosen[1] = sPos[1];
					picked =  visF ;
					chosenD = CardinalDirection.West;
				}
				if ( distB > 0 ) {
					possMoves[1] = 1;
					visB = visit[sPos[0]+1][sPos[1]];
					if (visB < picked) {
						picked = visB;
						chosen[0] = sPos[0] +1;
						chosen[1] =  sPos[1];
						chosenD = CardinalDirection.East;
					} else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						if ( rand == 0) {
							picked = visB;
							chosen[0] = sPos[0]+1 ;
							chosen[1] =  sPos[1] ;
							chosenD = CardinalDirection.East;
 						} 
					}
				}
				if ( distL > 0 ) {
					possMoves[2] = 1;
					visL = visit[sPos[0]][sPos[1]-1];
					if (visL < picked) {
						picked = visL;
						chosen[0] = sPos[0];
						chosen[1] = sPos[1]-1;
						chosenD = CardinalDirection.North;
					}else if (visB == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						if ( rand == 0) {
							picked = visL;
							chosen[0] = sPos[0] ;
							chosen[1] =  sPos[1]-1 ;
							chosenD = CardinalDirection.North;
 						} 
						
					}
				}
				if ( distR > 0 ) {
					possMoves[2] = 1;
					visR = visit[sPos[0]][sPos[1]+1];
					if ( visR < picked) {
						picked = visR;
						chosen[0] = sPos[0];
						chosen[1]= sPos[1] +1;
						chosenD = CardinalDirection.South;
					} else if( visR == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						if ( rand == 0) {
							picked = visR;
							chosen[0] = sPos[0] ;
							chosen[1] =  sPos[1] +1;
							chosenD = CardinalDirection.South;

 						} 
						
					}
				}
				break;
			}
			//end switch
			//make sure we have a selection
			assert(chosenD != null);
			//rotate towards selection and then move to that cell
			this.rotateTillRight(chosenD);
			robot.move(1, false);
			//mark is and mark new cell as visited 
			chosen = robot.getCurrentPosition();
			visit[chosen[0]][chosen[1]] += 1;
			
			
		}	
		//end not in room while loop
		//while loop must have ended because we are now in a room 
		int[] start = robot.getCurrentPosition();	
		ArrayList<int[]> rooms = new ArrayList<int[]>();
		boolean first = true;
		//just in case push it 
		int pushIT = 0;
		boolean atEntrance = false;
		//loop for inside room conditions
		while ( robot.isInsideRoom()) {
			if (robot.hasStopped()) {
				return false;
			}
			int distL = robot.distanceToObstacle(left);
			//if this is the first time we entering room we need to act accordingly , turn left and wall follow
			if ( first) {
				if (distL > 0 ) {
					robot.rotate(Turn.LEFT);
					//first room i alrady added so we can ignore rotate
					//move until obstacle is hit 
					for (int i = 0; i < distL; i++) {
					robot.move(1,false);
					
					//check if robot left room and move back in if you did 
					if (!robot.isInsideRoom()) {
						robot.rotate(Turn.AROUND);
						robot.move(1,  false);
						int[] tmps = new int[3];
						int[] temp = robot.getCurrentPosition();
						tmps[0] = temp[0];
						tmps[1] = temp[1];
						CardinalDirection cd = robot.getCurrentDirection();
						int dir = 99;
						switch(cd) {
						case North:
							dir = 2;
							break;
						case South:
							dir = 3;
							break;
						case East:
							dir = 1;
							break;
						case West:
							dir = 0;
							break;
						}
						tmps[2] = dir;
						//add newly founded door
						rooms.add(tmps);
						robot.rotate(Turn.LEFT);
						break;
					}
					// once robot is back in room we can check for doors again
					//
					int dirL = robot.distanceToObstacle(left);
					if (dirL > 0) {
						int[] tmp = new int[3];
						int[] temp = robot.getCurrentPosition();
						tmp[0] = temp[0];
						tmp[1] = temp[1];
						CardinalDirection cd = robot.getCurrentDirection();
						int dir = 99;
						switch(cd) {
						case North:
							dir = 2;
							break;
						case South:
							dir = 3;
							break;
						case East:
							dir = 1;
							break;
						case West:
							dir = 0;
							break;
						}
						tmp[2] = dir;
						rooms.add(tmp);
					}
					
					}
					
					
				}
				first = false;
			}
			
			//update position reference
			int[] tmp = robot.getCurrentPosition();
			
				
			
			//if were at the starting door location we can select a door to take 
			if (atEntrance) {
				//init some vars
				int picked = 999;
				int[] pickedLoc = null;
				//iterate through doors andpick the one with the least visits
				for(int[] cur : rooms) {
					int curVal = visit[cur[0]][cur[1]] ;
					if (curVal< picked) {
						picked =  curVal;
						pickedLoc = cur;
					} else if ( curVal == picked) {
						int rand = SingleRandom.getRandom().nextIntWithinInterval(0, 1);
						if ( rand == 0) {
							picked = curVal;
							pickedLoc = cur;
						}
					}
				}
				
				//get current positon and compare to picked
				int[] curpossy = robot.getCurrentPosition();
				
				//just checking 
				assert(pickedLoc != null);
				//move to selected door and then go through it 
				while ( curpossy[0] != pickedLoc[0]) {
					if (curpossy[0] < pickedLoc[0]) {
						//rotate and move
						this.rotateTillRight(CardinalDirection.East);
						robot.move(1, false);
						curpossy = robot.getCurrentPosition();
						visit[curpossy[0]][curpossy[1]] ++ ;
					} else {
						//rotate and move
						this.rotateTillRight(CardinalDirection.West);
						robot.move(1, false);
						curpossy = robot.getCurrentPosition();
						visit[curpossy[0]][curpossy[1]] ++ ;
					}
				}
				//same thing but y instead of x
				while ( curpossy[1] != pickedLoc[1]) {
					//System.out.println("moving up or down");
					if (curpossy[1] < pickedLoc[1]) {
						this.rotateTillRight(CardinalDirection.South);
						robot.move(1, false);
						curpossy = robot.getCurrentPosition();
						visit[curpossy[0]][curpossy[1]] ++ ;
					} else {
						this.rotateTillRight(CardinalDirection.North);
						robot.move(1, false);
						curpossy = robot.getCurrentPosition();
						visit[curpossy[0]][curpossy[1]] ++ ;
					}
				}
				//we are at chosen location, rotate to face door and then go thru
				int pickedD = pickedLoc[2];
				CardinalDirection cde = null ;
				switch(pickedD) {
				case 0: 
					cde = CardinalDirection.North;
					break;
				case 1: 
					cde = CardinalDirection.South;
					break;
				case 2: 
					cde = CardinalDirection.East;
					break;
				case 3: 
					cde = CardinalDirection.West;
					break;
				
				}
				int[] newCur = robot.getCurrentPosition();
			
				rotateTillRight(cde);
				
				
				visit[newCur[0]][newCur[1]] += 1;
				int disF = robot.distanceToObstacle(forW);
				if (disF != 0) {
				robot.move(1, false);
				curpossy = robot.getCurrentPosition();
				visit[curpossy[0]][curpossy[1]] ++ ;
				}
				
				break;
			}
			
			
			//SCANNING FOR DOORS
			//add entry door to list of doors
			int[] tmps = new int[3];
			int[] temp = robot.getCurrentPosition();
			tmps[0] = temp[0];
			tmps[1] = temp[1];
			CardinalDirection cd = robot.getCurrentDirection();
			int dir = 99;
			switch(cd) {
			case North:
				dir = 2;
				break;
			case South:
				dir = 3;
				break;
			case East:
				dir = 1;
				break;
			case West:
				dir = 0;
				break;
			}
			tmps[2] = dir;
			rooms.add(tmps);
			//rooms.add(robot.getCurrentPosition());
			
			//keep checking
			int distF = robot.distanceToObstacle(forW);
			 if (distF > 0){
				
				for (int i = 0; i< distF; i++) {
					robot.move(1, false);
					
					int[] tempr = robot.getCurrentPosition();
			

					if (tempr[0] == start[0] && tempr[1]== start[1]) {
						atEntrance = true;
						break;
				
					}
					
					
					//check if robot left room
					//if it did we need to turn around and check some stuff out 
					if (!robot.isInsideRoom()) {
						robot.rotate(Turn.AROUND);
						robot.move(1,  false);
						
						int[] tmpt = new int[3];
						int[] tempd = robot.getCurrentPosition();
						//visit[tempd[0]][tempd[1]] ++ ;
						tmpt[0] = tempd[0];
						tmpt[1] = tempd[1];
						CardinalDirection cdq = robot.getCurrentDirection();
						int dirr = 99;
						switch(cdq) {
						case North:
							dirr = 2;
							break;
						case South:
							dirr = 3;
							break;
						case East:
							dirr = 1;
							break;
						case West:
							dirr = 0;
							break;
						}
						tmpt[2] = dirr;
						rooms.add(tmpt);
						//rooms.add(robot.getCurrentPosition());
						robot.rotate(Turn.LEFT);
						break;
					}
					//compare
					int dirL = robot.distanceToObstacle(left);
					if (dirL > 0) {
						int[] tmpp = new int[3];
						int[] tempp = robot.getCurrentPosition();
						tmpp[0] = tempp[0];
						tmpp[1] = tempp[1];
						CardinalDirection cdp = robot.getCurrentDirection();
						int dirp = 99;
						switch(cdp) {
						case North:
							dirp = 2;
							break;
						case South:
							dirp = 3;
							break;
						case East:
							dirp = 1;
							break;
						case West:
							dirp = 0;
							break;
						}
						tmpp[2] = dirp;
						rooms.add(tmpp);
						//rooms.add(robot.getCurrentPosition());
					}
					
				}
				
				
				//corner case
			} else if (distF == 0 ) {
				robot.rotate(Turn.RIGHT);
				distF = robot.distanceToObstacle(forW);
				for (int i = 0; i< distF; i++) {
					robot.move(1, false);
					
					int[] tempy = robot.getCurrentPosition();
					visit[tempy[0]][tempy[1]] ++ ;
			
					//make sure were not at the entrance, if we are reset loop so we can choose
					if (tempy[0] == start[0] && tempy[1]== start[1]) {
						atEntrance = true;
						break;	
				
					}
					
					//check if robot left room
					if (!robot.isInsideRoom()) {
						robot.rotate(Turn.AROUND);
						robot.move(1,  false);
						int[] tmpq = new int[3];
						int[] tempq = robot.getCurrentPosition();
						tmpq[0] = tempq[0];
						tmpq[1] = tempq[1];
						CardinalDirection cdq = robot.getCurrentDirection();
						int dirq = 99;
						
						switch(cdq) {
						case North:
							dirq = 2;
							break;
						case South:
							dirq = 3;
							break;
						case East:
							dirq = 1;
							break;
						case West:
							dirq = 0;
							break;
						}
						
						tmpq[2] = dirq;
						rooms.add(tmpq);
						
						robot.rotate(Turn.LEFT);
						break;
					}
					
					int dirL = robot.distanceToObstacle(left);
					if (dirL > 0) {
						int[] tmpw = new int[3];
						int[] tempw = robot.getCurrentPosition();
						tmpw[0] = tempw[0];
						tmpw[1] = tempw[1];
						CardinalDirection cdw = robot.getCurrentDirection();
						int dirw = 99;
						switch(cdw) {
						case North:
							dirw = 2;
							break;
						case South:
							dirw = 3;
							break;
						case East:
							dirw = 1;
							break;
						case West:
							dirw = 0;
							break;
						}
						tmpw[2] = dirw;
						rooms.add(tmpw);
					}
				}
				
				
			//move loop failsafe added at last minute due to lack of time 
			//forces program to pick a door to take
			pushIT ++;	
			if ( pushIT > 10) {
				atEntrance = true;
			}
			}
			
			
		
			//end scan for doors
			
			
		}
		}
		
		atExit();
		return true;
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
			
		}else {
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
