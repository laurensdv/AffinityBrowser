package org.scientificprofiling.affinitybrowser.visualization;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Computator {
	public static Set<Integer> intersectArrays(List<Integer> first, List<Integer> second) {  
        // initialize a return set for intersections  
        Set<Integer> intsIntersect = new HashSet<Integer>();  
  
        // load first array to a hash  
        HashSet<Integer> array1ToHash = new HashSet<Integer>();  
        for (int i = 0; i < first.size(); i++) {  
            array1ToHash.add(first.get(i));  
        }  
  
  
        // check second array for matches within the hash  
        for (int i = 0; i < second.size(); i++) {  
            if (array1ToHash.contains(second.get(i))) {  
                // add to the intersect array  
                intsIntersect.add(second.get(i));  
            }  
        }  
  
        return intsIntersect;  
          
    }  
	
}
