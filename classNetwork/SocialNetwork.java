package classNetwork;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

class SocialNetwork {
    //initializes maps for the students and their connections
    private Map<Integer, Student> students = new HashMap<>();
    private Map<Integer, Map<Integer, Integer>> connections = new HashMap<>();

    public void addStudent(int id, String firstName, String lastName, int waitDays) {
	/*@param id - student ID
	*@param firstName - student first name
	*@param lastName - student last name
	*@param waitDays - days a student must wait before making another connection (edge weight)
	*/
        if (students.containsKey(id)) {
            System.out.println("Student with ID " + id + " already exists. Skipping.");
            return;
        }
        students.put(id, new Student(id, firstName, lastName, waitDays));
    }

    public String getName(int id) {
	/*@param id - student ID
	*@return - student name
	*/

        Student student = students.get(id);
        return student.firstName;
    }

    public void addConnection(int fromId, int toId) {
	/*@param fromId - ID of student from which the connection originates
	*@param toId - ID of student being connected to
	*/
        Student fromStudent = students.get(fromId);
        Student toStudent = students.get(toId);
        int waitDays = fromStudent.getWaitDays();
    
        if (fromStudent == null || toStudent == null) {
            System.out.println("Invalid connection: one or both student IDs not found.");
            return;
        }
    
        connections.computeIfAbsent(fromId, k -> new HashMap<>()).put(toId, waitDays);
    } 

    public List<Integer> getNetworkList(int studentId) {
	/*@param studentId - student whose connections is being listed
	*@return - list of connected student IDs
	*/
        return new ArrayList<>(connections.getOrDefault(studentId, Collections.emptyMap()).keySet());
    }

    public Map<Integer, Integer> findShortestPath(int startId, int endId) {
`	/*@param startId - ID of originating student
	*@param endId - ID of destination student
	*@return - map in which the key is the total weight of the shortest path and the value is the number of edges
	*/
        if (!students.containsKey(startId) || !students.containsKey(endId)) return null;
    
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
    
        pq.offer(new int[]{startId, 0});
        distances.put(startId, 0);
    
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentId = current[0];
            int currentDistance = current[1];
    
            if (currentId == endId) {
                int pathLength = calculatePathLength(previous, startId, endId);
                Map<Integer, Integer> result = new HashMap<>();
                result.put(currentDistance, pathLength);
                return result;
            }
    
            for (Map.Entry<Integer, Integer> entry : connections.getOrDefault(currentId, new HashMap<>()).entrySet()) {
                int neighbor = entry.getKey();
                int weight = entry.getValue();
                int newDistance = currentDistance + weight;
    
                if (newDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentId);
                    pq.offer(new int[]{neighbor, newDistance});
                }
            }
        }
        return null; // No path found
    }    

    private int calculatePathLength(Map<Integer, Integer> previous, int startId, int endId) {
	/*@param previous - map in which the key is a student ID and the value is the previous one in the path being taken
	*@param startId - ID of starting student
	*@param endId - ID of ending student
	*/
        int length = 0;
        Integer current = endId;
        while (current != null && current != startId) {
            length++;
            current = previous.get(current);
        }
        return length;
    }

    public void disconnect(int fromId, int toId) {
	/*@param fromId - ID of student from which the connection being terminated originates
	*@param toId - ID of student to which the connection being terminated connects
	*/
        Map<Integer, Integer> neighbors = connections.get(fromId);
        if (neighbors != null) {
            neighbors.remove(toId);
        }
    }    

    public void modifyWaitDays(int fromId, int days, boolean increase) {
	/*@param fromId - originating student ID
	*@param toId - destination student ID
	*@param increase - determines whether waitDays increases or decreases
	*/
        Map<Integer, Integer> neighbors = connections.get(fromId);
        if (neighbors != null) {
            for (Map.Entry<Integer, Integer> entry : neighbors.entrySet()) {
                int toId = entry.getKey();
                int newWeight = entry.getValue() + (increase ? days : -days);
                neighbors.put(toId, Math.max(0, newWeight));
            }
        }
    }

    private static class Student {
        int id;
        String firstName;
        String lastName;
        int waitDays;
        List<Integer> connections;

        public Student(int id, String firstName, String lastName, int waitDays) {
	    /*@param id - student ID
	     *@param firstName - student first name
	     *@param lastName - student last name
	     *@param waitDays - days student must wait between making connections (edge weight)
	     */
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.waitDays = waitDays;
            this.connections = new ArrayList<>();
        }

        public int getWaitDays() {
            return waitDays;
        }
    }
}
