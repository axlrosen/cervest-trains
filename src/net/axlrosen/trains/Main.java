package net.axlrosen.trains;

import net.axlrosen.trains.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph("AB2, BC3, CD6, DC6, DE8, AD4, CE8, EB4, AE5");
        Algorithms alg = new Algorithms(graph);

        System.out.println("Output #1: " + writeDistance(alg,"A-B-C"));
        System.out.println("Output #2: " + writeDistance(alg,"A-D"));
        System.out.println("Output #3: " + writeDistance(alg,"A-D-C"));
        System.out.println("Output #4: " + writeDistance(alg,"A-E-B-C-D"));
        System.out.println("Output #5: " + writeDistance(alg,"A-E-D"));

        long answer6 = alg.findAllTrips("C", 3).stream()
                .filter(trip -> trip.endsWith("C"))
                .count();
        System.out.println("Output #6: " + answer6);

        long answer7 = alg.findAllTrips("A", 4).stream()
                .filter(trip -> trip.endsWith("C"))
                .filter(trip -> trip.length() == 5)
                .count();
        System.out.println("Output #7: " + answer7);

        System.out.println("Output #8: " + alg.getShortestRoute("A", "C"));
        System.out.println("Output #9: " + alg.getShortestRoute("B", "B"));
    }

    private static String writeDistance(Algorithms alg, String path) {
        Optional<Integer> distance =  alg.computeDistance(path);
        if (distance.isPresent()) {
            return Integer.toString(distance.get());
        }
        else {
            return "NO SUCH ROUTE";
        }
    }
}
