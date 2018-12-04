package edu.wm.cs.cs301.skylarbarrera.generation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.wm.cs.cs301.skylarbarrera.generation.SingleRandom;


/**
 * @author skylarbarrera
 *
 */
public class DisjointSets{

	
		public ArrayList<ArrayList<Integer>> dSet;
		
		public ArrayList<Integer> emptySets ;
		public int width1;
		public int widthS;
		public int heightS;
		public int notEmpty = 0;
		public boolean[] visited ;
		public int elementsP;
		
		/**
		 * 
		 * @return copy of this class for testing purposes
		 */
		public DisjointSets copyReturn() {
			DisjointSets obj = new DisjointSets(widthS, heightS);
			obj.dSet =  new ArrayList<ArrayList<Integer>>(dSet);
	
			
			
		return obj;	
		}
		
		/**
		 * INIT
		 * @param width
		 * @param height
		 */
		public DisjointSets(int width, int height) {
			
			width1 = width *2;
			heightS = height;
			widthS = width;
			visited = new boolean[widthS*heightS];
			dSet = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < width1; ++i) {
				 ArrayList<Integer> row = new ArrayList<Integer>();
				  dSet.add(row);
				}
			emptySets = new ArrayList<Integer>();
			for (int i = 0; i < width1; ++i) {
				
				  emptySets.add(i);
				  
				}
			//this.printData();
			//this.printDataEmpty();
		
			}
		
		/**
		 * Updates empty value in class
		 */
		public void updateEmpty() {
			emptySets.clear();
			for (int i = 0; i < width1; ++i) {
				if (dSet.get(i).size() == 0) {
					
					emptySets.add(i);
				}
					
			}
			notEmpty = dSet.size() - emptySets.size();
		}
		
		
		
		
		/**
		 * adds element into random empty set
		 * @param element
		 */
		public void create(int element) {
			//System.out.println("Creating - " + element);
			//Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
			//System.out.println("set size = " + emptySets.size());
			//System.out.println("Dset size = " + dSet.size());
			updateEmpty();
			///this.printDataEmpty();
			
			int rand;
			if (emptySets.size()== 0) {
				rand = 0;
			} else {
			rand = (int)Math.round((Math.random()*(((emptySets.size()-1)-1)+1)) +0);
			}
			//System.out.println("RAND = " + rand);
			int emptySet = emptySets.get(rand);
			//System.out.print("INDEX =  = " + emptySet);
			dSet.get(emptySet).add(element);
			updateEmpty();
		}
		
		/**
		 * Prints Set
		 * @param index
		 */
		public void printSet(int index) {
			ArrayList<Integer> set = getSet(index);
			System.out.print(" Set " + index +" - ");
			for (int i = 0; i < set.size(); i++) {
				
				System.out.print(" " + set.get(i));
			}
			System.out.println();
		}
		
		/**
		 * Adds all of second set to first set and then clears second set
		 * @param first
		 * @param second
		 */
		public void union(int first, int second) {
			//System.out.print("\n\nUNION of "+ first + " & "+ second + "\n");
			//this.printDataEmpty();
			//System.out.println("=============================");
			//this.printData();
			
			
			
			
			ArrayList<Integer> first_set = dSet.get(first);
			ArrayList<Integer> second_set = dSet.get(second);

			/*
			System.out.print("FIRST SET  =  ");
			for (int i = 0; i<first_set.size(); i++) {
				System.out.print( first_set.get(i));
			}
			
			System.out.print("\nSECOND SET = ");
			for (int i = 0; i<second_set.size(); i++) {
				System.out.print( second_set.get(i));
			}*/
			
			first_set.addAll(second_set);
			
			/*System.out.print("\nFIRST SET AFTER UNION pf " + first+ "&" + second+ " = ");
			for (int i = 0; i<first_set.size(); i++) {
				System.out.print( " " +first_set.get(i));
			}
			System.out.println();*/
			
			dSet.set(first, first_set);
			dSet.get(second).clear();
			
			updateEmpty();
			
			//this.printDataEmpty();
			//System.out.println("=============================");
			//this.printData();
			
			return;
		}
		
		
		/**
		 * returns index of set containing element 
		 * @param element
		 * @return
		 */
		public int findSet(int element) {
			
			for (int index = 0; index < dSet.size(); index++) {
				ArrayList<Integer> map = dSet.get(index);
					if (map.contains(element)) {
						return index;
					}
				}
			return -1;
		}
		
		/**
		 * returns index of set containing element
		 * @param bfsArr
		 * @param element
		 * @return
		 */
		public int findSetBFS(ArrayList<ArrayList<Integer>> bfsArr, int element) {
			//printBfsArr(elementsP, bfsArr );
			//System.out.println("looking for " + element);
			for (int index = 0; index < bfsArr.size(); index++) {
				ArrayList<Integer> map = bfsArr.get(index);
					if (map.contains(element)) {
						return index;
					}
				}
			return -1;
		}
		
		
		
		/**
		 * Prints all of main data structure
		 */
		public void printData() {
			
			for (int index = 0; index < dSet.size(); index++) {
				ArrayList<Integer> map = dSet.get(index);
				System.out.print( index + " size["+ map.size() + "]  = " );
	
				for (Integer element : map) {
					System.out.print( " " + element );
				}
				System.out.print("\n");
			}
			}
		
		/**
		 * prints list of empty sets
		 */
		public void printDataEmpty() {
			System.out.print( "emptySET = ");
			for (int index = 0; index < emptySets.size(); index++) {
				int map = emptySets.get(index);
				
					
				System.out.print( " " + map );
				}
				System.out.print("\n");
			}
			
			
			
		/**
		 * gives set at index given as param
		 * @param index
		 * @return set at index
		 */
		public ArrayList<Integer> getSet(int index) {
			if ( index >= dSet.size() ) {
				return null;
			}
			ArrayList<Integer>map = dSet.get(index);
		
			return map;
			
			
		}
		
		/**
		 * gets set at index given in the the given arrayList
		 * @param bfsArr
		 * @param index
		 * @return
		 */
		public ArrayList<Integer> getSetBFS(ArrayList<ArrayList<Integer>> bfsArr,int index) {
			//System.out.println("Size - " + bfsArr.size());
			if ( index >= bfsArr.size() ) {
				return null;
			}
			ArrayList<Integer>map = bfsArr.get(index);
		
			return map;
			
			
		}
		
		/**
		 * @return
		 */
		public int getNumberofDisjointSets() {
			return dSet.size();
		}
		
		/**
		 * gets random index of a set
		 * @param set
		 * @param r
		 * @return
		 */
		public int getRandomInSet(ArrayList<Integer> set, int r) {
			//System.out.println("getRandomInSet row = " + r + "size of set - " + set.size());
			
			ArrayList<Integer> rowSet = new ArrayList<>();
			for (int i = 0; i < set.size();i++) {
				int cur = set.get(i);
				//
				//System.out.println("row of element "+ cur + " is " + (cur / widthS  ));
				if ( cur / widthS == r ){
					rowSet.add(cur);
					//System.out.println("adding the following - " + cur);
				}
			}
			if (rowSet.size() > 0) {
				int rand = SingleRandom.getRandom().nextIntWithinInterval(0, rowSet.size()-1);
				
				return rowSet.get(rand);} 
			else {
				System.out.println();
				
				for (int i = 0; i < set.size();i++){
					System.out.print(set.get(i)+ " ");
				}
				System.out.println();
				System.out.println("WERE FUCKED");
				return -1;
			}
		}
		/**
		 * makes a ajacency list from the list of sets for all elements
		 * @param elements
		 * @return
		 */
		public ArrayList<ArrayList<Integer>> bfsMake(int elements) {
			elementsP = elements;
			ArrayList<ArrayList<Integer>> bfsArr = new ArrayList<ArrayList<Integer>>();
			//System.out.println("Before making");
			//printBfsArr(bfsArr.size(), bfsArr);
			for (int element = 0; element < elements+1;element++) {
				
				
				
				int cur = findSet(element);
				ArrayList<Integer> curList = getSet(cur);
				ArrayList<Integer> newList = new ArrayList<Integer>() ;
				//System.out.println("element - " + element + "set  - " + cur);
				
					
				if ( element % widthS != (widthS-1)) {
				if (findSet(element+1) == cur) {
					//System.out.println("adding "  + (element+1) + "to " + cur);
					newList.add(element+1);
					
					}
				}
				if (element % widthS != 0) {
					if (findSet(element-1) == cur) {
						newList.add(element-1);
					}
				}
				if (element / heightS > 0) {
					if (findSet(element- widthS) == cur) {
						newList.add(element- widthS);
					}
				}
				if (element / heightS < widthS-1) {
					if (findSet(element+ widthS) == cur) {
						newList.add(element+ widthS);
					}
				}
				bfsArr.add(newList);
				//System.out.println("bfs arr size = " + bfsArr.size());
			}
			//printBfsArr(bfsArr.size(), bfsArr);
			return bfsArr;
		}
		
		/**
		 * Performs BFS search
		 * @param bfsArr
		 * @return
		 */
		public boolean bfs(ArrayList<ArrayList<Integer>> bfsArr) {
			//printBfsArr(elementsP, bfsArr );
			int[] parent = new int[widthS*heightS];
		    
		    for (int i = 0; i< widthS*heightS; i++) {
		    	parent[i] = -1;
		    }
		    
		    ArrayDeque<Integer> queue = new ArrayDeque<>();
	        int node;
	        queue.add(0);
	        visited[0] = true;
	        //printData();
	        while(!queue.isEmpty()) {
	           
	        	//Dequeue element
	            node = queue.poll();
	            
	            
	            
	              
	            		System.out.println("getting set for " + node);
	            		int bfsIndex = findSetBFS(bfsArr, node);
	            		ArrayList<Integer> bfsSet = getSetBFS(bfsArr, bfsIndex );
	            		//System.out.println("MOTHER");
	                	for (int i = 0; i < bfsSet.size();i++){
	                	int index = bfsSet.get(i);
	                    if(visited[index] == false) {
	                        
	                        
	                        visited[index] = true;
	                        queue.add(index);
	                        parent[index] = node;
	                        
	                    } else if (parent[node] != index ) {
	                    	return true;
	                    }
	                }
	            
	        }
	        return false;
	}
		
		/**
		 * checks cycle calling BFS
		 * @param elements
		 * 
		 * @return true if cycle exists
		 */
		public boolean checkCycle(int elements) {
			
			ArrayList<ArrayList<Integer>> bfsArr = bfsMake(elements);
		    for (int i = 0; i < widthS+heightS;i++) {
		    	visited[i] = false;
		    }
    		System.out.println("Checking Cycle");	

		    for (int i =0; i< widthS+heightS;i++) {
		    	if (!visited[i] && bfs(bfsArr)) {
		    		
		    		System.out.println("Cycle found at element: " + i);	
		    		return true;
		    	
		    	}
		    }
    		System.out.println("Cycle NOT found");	

		    return false;
		    

}
		
		/**
		 * prints given array
		 * @param elements
		 * @param bfsArr
		 */
		public void printBfsArr( int elements, ArrayList<ArrayList<Integer>> bfsArr) {
			System.out.println("PRINTING BFS ARRAY/n");

			for (int element = 0; element < bfsArr.size(); element++) {
				System.out.print("array for element - " + element +" :  ");
				ArrayList<Integer> set = getSetBFS(bfsArr, element);
				for(int index = 0; index < set.size(); index++ ) {
				
				System.out.print(" " +set.get(index) );
			}
			System.out.println();

		}
		}
		
}
		
		
		
		
		

