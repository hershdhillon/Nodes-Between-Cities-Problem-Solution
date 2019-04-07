package codility;

import java.util.*;

class Solution {

    public class City {
        int id;
        int distance;
        List<City> linkedCities;

        public City(int id) {
            this.id = id;
            linkedCities = new ArrayList<>();
        }
    }

    public int[] solutionBFS(int[] T) {
        if (T == null || T.length <= 1) {
            return null;
        }

        City capital = null;
        City[] cities = new City[T.length];

        // Set up the city nodes
        for (int i=0; i < T.length; ++i) {
            cities[i] = new City (i);
        }

        for (int i=0; i < T.length; ++i) {
            if (T[i] == i) {
                capital = cities[i];
            }
            else {
                cities[i].linkedCities.add(cities[T[i]]);
                cities[T[i]].linkedCities.add(cities[i]);
            }
        }

        Queue<City> haveVisitedQ = new LinkedList<>();
        int[] numberAtThisDistance = new int[T.length-1];
        boolean[] visited = new boolean[T.length];
        for (int i=0; i < T.length; ++i) {
            visited[i] = false;
        }

        capital.distance = 0;
        haveVisitedQ.add (capital);
        while (haveVisitedQ.size() > 0) {
            City nowVisiting = haveVisitedQ.poll();
            visited[nowVisiting.id] = true;
            for (City linkedCity: nowVisiting.linkedCities) {
                if (!visited[linkedCity.id]) {
                    linkedCity.distance = nowVisiting.distance + 1;
                    ++numberAtThisDistance[linkedCity.distance-1];
                    haveVisitedQ.add(linkedCity);
                }
            }
        }

        return numberAtThisDistance;
    }

    public int[] solutionDFS(int[] T) {
        if (T == null || T.length <= 1) {
            return null;
        }

        City capital = null;
        City[] cities = new City[T.length];

        // Set up the city nodes
        for (int i=0; i < T.length; ++i) {
            cities[i] = new City (i);
        }

        for (int i=0; i < T.length; ++i) {
            if (T[i] == i) {
                capital = cities[i];
            }
            else {
                cities[i].linkedCities.add(cities[T[i]]);
                cities[T[i]].linkedCities.add(cities[i]);
            }
        }

        Stack<City> haveVisitedStack = new Stack<>();
        int[] numberAtThisDistance = new int[T.length-1];
        boolean[] visited = new boolean[T.length];
        for (int i=0; i < T.length; ++i) {
            visited[i] = false;
        }

        capital.distance = 0;
        haveVisitedStack.push (capital);
        Iterator<City>[] adj = (Iterator <City>[]) new Iterator[T.length];
        for (int i=0; i < T.length; ++i) {
            adj[i] = cities[i].linkedCities.iterator();
        }

        while (!haveVisitedStack.isEmpty()) {
            City nowVisiting = haveVisitedStack.peek();
            visited[nowVisiting.id] = true;
            if (adj[nowVisiting.id].hasNext()) {
                City linkedCity = adj[nowVisiting.id].next();
//            for (City linkedCity: nowVisiting.linkedCities) {
                if (!visited[linkedCity.id]) {
                    linkedCity.distance = nowVisiting.distance + 1;
                    ++numberAtThisDistance[linkedCity.distance-1];
                    haveVisitedStack.push(linkedCity);
                }
            }
            else {
                haveVisitedStack.pop();
            }
        }

        return numberAtThisDistance;
    }

    public static void main (String[] args) {

        int T[] = {9, 1, 4, 9, 0, 4, 8, 9, 0, 1};
        Solution dist = new Solution();
        int[] result = dist.solution(T);
        System.out.print("BFS: " + Arrays.toString(result));
        int[] resultDFS = dist.solutionDFS(T);
        System.out.print("\nDFS: " + Arrays.toString(resultDFS));

        System.out.println();
        int T2[] = {0, 0};
        result = dist.solution(T);
        System.out.print(Arrays.toString(result));

        System.out.println();
        int T3[] = {0};
        result = dist.solution(T3);
        System.out.print(Arrays.toString(result));
    }
}