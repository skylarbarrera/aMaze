package edu.wm.cs.cs301.skylarbarrera.generation;

import  edu.wm.cs.cs301.skylarbarrera.generation.DisjointSets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import edu.wm.cs.cs301.skylarbarrera.generation.Wall;

public class MazeBuilderEller extends MazeBuilder implements Runnable{

	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	
	
	
	
	/* 
	 * Generates pathways; Uses DisjointSets.java as a data structure and for heavy lifting
	 */
	@Override
	protected void generatePathways() {	
		
		//System.out.println();
		//gang gang 
		//System.out.print(cells.toString());
		//System.out.println("GENERATING PATHWAYS");
		//System.out.println("width = " + width);
		//System.out.println("height = " + height);
		DisjointSets map = new DisjointSets(width, height);
		//int[][] store = new [][]
		
		boolean finalRow = false;
		
		int element = 0; 
		//iterate through rows columns
		
		for (int x = 0; x< width; x++) {
			if (x == width-1) {
				//System.out.println("FINAL ROW FINAL ROW" );
				finalRow = true;
			}
			//System.out.println("Starting new row   " + x +" with element "+ element );
			if ( finalRow == true) {
				//System.out.println("FINAL ROW Cock blocked" );
				for (int y = 0; y< height; y++) {
					
					if (map.findSet(element)   == -1) {
						//System.out.println("creating =  "+ element );
						//System.out.println("creating =  "+ x+  " , " + y);
						
						map.create(element);
						//System.out.println("marking element " + element + " as visited");
						
					}else {
						//System.out.println("NOT creating =  "+ x+  " , " + y);
						//System.out.println("Cant create =  "+ element + "Already been visited");
					}
					
					
				
				element += 1;	
					
				}
				element -=2;
				//System.out.println("Element before FINAL INNERS" + element);
				for (int y = height-2; y>=0; y--) {
					
					//random call - if 1 remove left wall
					//System.out.println("FINALLY elementIN Y LOOP =  "+ element );
					if (map.findSet(element)   == -1) {
						map.create(element);
					}
					if (true) {
						int set1 = map.findSet(element);
						int set2 = map.findSet(element +1);
						//System.out.println("FINALLY sets - " + set1 + " " + set2 );
						if ( set1 !=  set2 && (set1 != -1 && set2 != -1) ) {
							//System.out.println("Union of " + element + " "+ (element-1));	

							
							//System.out.println("TOTAL SETS AFTER" + map.getNumberofDisjointSets());	
							Wall wall = new Wall(x, y, CardinalDirection.South) ;
							//System.out.println("x, y " + x+ ", " + y);
							
							//System.out.println(" FINALLY deleting wall between " + x+ ", " + y + " & " +(x)+ ", " + (y+1) );
							cells.deleteWall(wall);
							map.union(set1, set2);
							//System.out.print(" FINALLY cycles exist ? " + map.checkCycle(element));
							
							
							
						}
					}
					element -=1;
				}
				
			} else {
				
			//NOT FINAL
				//NOT FINAL
				//NOT FINAL
				//NOT FINAL
				//NOT FINAL
				//NOT FINAL
				//NOT FINAL
				//NOT FINAL
			for (int y = 0; y< height; y++) {
				//System.out.println(x + " " + y);
				if (map.findSet(element)   == -1) {
					//System.out.println("pls dont creating =  "+ element );
					//System.out.println("creating =  "+ x+  " , " + y);
					
					map.create(element);
					//System.out.println("marking element " + element + " as visited");
					//cells.setCellAsVisited(x, y);
					
				}else {
					//System.out.println(" First NOT creating =  "+ x+  " , " + y);
					//System.out.println("Cant create =  "+ element + " Already been visited");
				}
				
				
			
			element += 1;	
				
			}
			//System.out.println();
			
			//System.out.println("/n Row "+ x + " created");
			int elements = element;
			
			//System.out.println("ELEMENT - " + element);
			element -= 2;
			//System.out.println("width =  "+ width );
			//System.out.println("elementbefore loop =  "+ element );	
			for (int y = height-2; y>=0; y--) {
				
				//random call - if 1 remove left wall
				//System.out.println("elementIN Y LOOP =  "+ element );
				if (random.nextIntWithinInterval(0, 1) == 1) {
					int set1 = map.findSet(element);
					int set2 = map.findSet(element +1);
						
					//System.out.println("Union of " + element + " "+ (element-1));	
					//map.printData();
					if ( (set1 !=  set2) && (set1 != -1 && set2 != -1)) {
						//System.out.println("Union of " + element + " "+ (element+1));
						//System.out.println("set 1 = " +set1 + " set 2 = "+ (set2));

						
						//System.out.println("TOTAL SETS AFTER" + map.getNumberofDisjointSets());	
						Wall wall = new Wall(x, y, CardinalDirection.South) ;
						//System.out.println("width = " + width);
						//System.out.println("height = " + height);


						//System.out.println("x, y " + x+ ", " + y);
						if (cells.canGo(wall) ) {
							//System.out.println("DDDdeleting wall between " + x+ ", " + y + " & " +(x)+ ", " + (y+1) );
							
							
							//System.out.println("check MY SHIIIIIIT");
							DisjointSets copy = map.copyReturn();
							//copy.printData();
							map.union(set1, set2);
							cells.deleteWall(wall);
							//System.out.print(" rrrrrrr cycles exist ? " + map.checkCycle(element));
							/*if (copy.checkCycle(element)) {
								//System.out.println("CYCLE DETECTED NOT DOING ANYTHING");
								//cells.addWall(wall, true);		
										
							} else {
							
							
							}*/
						
						} else {
							if ( x == 0) {
								//System.out.println("Sets");
								map.printSet(set1);
								map.printSet(set2);
								map.union(set1, set2);
								cells.deleteWall(wall);
								//System.out.println("Sorry deleted it anyaways u WHORE");
							} else {
							//System.out.println("Wall Cant Go SOUTH - Inner Walls");
							}
						}
						
					}
				}
				element -=1;
			}
			//System.out.println("Row "+ x + " Inner Walles Removed");	
			//map.checkCycle(element);
			element+=1;
			//System.out.println("element after LOOP =  "+ element );
			//Add sets to next row
			ArrayList<Integer> subSets = new ArrayList<>();
			
			//System.out.println("ADDING TO NEXT ROW");

			
			
				
				ArrayList<ArrayList<Integer>> Array = new ArrayList<ArrayList<Integer>>();
				for (int y = 0; y< height; y++) {
					//System.out.println("Total sets = " + map.getNumberofDisjointSets() + " curSubSets = " +subSets.size() );	
					//here
					
					int curSet = map.findSet(element);
					
					
					//if array is empty add the first element in a new arraylist
					if (Array.size()== 0) {
						ArrayList<Integer> tmp = new ArrayList<Integer>();
						tmp.add(element);
						Array.add(tmp);
					} else {
						boolean conditional = false;
						for (int i = 0; i<Array.size(); i++) {
							ArrayList<Integer> arrSet = Array.get(i);
							int nxtSet = map.findSet(arrSet.get(0));
							if ( curSet == nxtSet) {
								arrSet.add(element);
								conditional = true;
							}
							
						}
						if (conditional == false) {
						ArrayList<Integer> tmp = new ArrayList<Integer>();
						tmp.add(element);
						Array.add(tmp);
						}
					}
					
				
					
				element +=1;	
				}
				
				//System.out.println("Post Generation = " + element);
				//map.printBfsArr(0, Array);
				
				for ( int i = 0; i < Array.size(); i++) {
					//System.out.println("i = " + i);
					ArrayList<Integer> arrSet = Array.get(i);
					//System.out.print(" arrSet -" );
					for (int index = 0; index < arrSet.size(); index++) {
						//System.out.print(" " + arrSet.get(index));
					}
					//System.out.println();
					int chosen;
					if (arrSet.size()== 1) {
						chosen = arrSet.get(0);
					}else {
					chosen = arrSet.get(random.nextIntWithinInterval(0, arrSet.size()-1));
					}
					//System.out.println("Chosen element = " + chosen);
					if (map.findSet(chosen)   == -1) {
						map.create(chosen);
					}
					int set1 = map.findSet(chosen);
					if (map.findSet(chosen+width)   == -1) {
						map.create(chosen+width);
					}
					
					int set2 = map.findSet(chosen+width);
					//System.out.println("set 2 " + set2);
					//System.out.println("set 1 " + set1);

					int sX = chosen / height;
					int sY = chosen % height;
					//System.out.println("x, y " + sX+ ", " + sY);
					//System.out.println("width = " + width);
					//System.out.println("height = " + height);
					Wall wall = new Wall(sX, sY, CardinalDirection.East);
					map.union(set1, set2);
					cells.deleteWall(wall);
					//System.out.println("--------------");
				}
				
				
				
				
				
				
				
				
				
				
				
				element += 1;
				//
				//System.out.println("Total TRRUUUEE sets = " + map.getNumberofDisjointSets() + " curSubSets = " +subSets.size() );
				
			
			
			//map.printData();
			//System.out.println("Done?");
			element -=1;

			
			}
			
			
			//here
			
		}
		
		
		//map.printData();
		//System.out.print(" cycles exist ? " + map.checkCycle(element));
		
	}

}

