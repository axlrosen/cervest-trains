package net.axlrosen.trains;

import net.axlrosen.trains.Graph;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph("AB2, BC3, CD6, DC6, DE8, AD4, CE8, EB4, AE5");
        System.out.println("Output #1: " + writeDistance(graph,"A-B-C"));
        System.out.println("Output #2: " + writeDistance(graph,"A-D"));
        System.out.println("Output #3: " + writeDistance(graph,"A-D-C"));
        System.out.println("Output #4: " + writeDistance(graph,"A-E-B-C-D"));
        System.out.println("Output #5: " + writeDistance(graph,"A-E-D"));
    }

    private static String writeDistance(Graph graph, String path) {
        Optional<Integer> distance =  computeDistance(graph, path);
        if (distance.isPresent()) {
            return Integer.toString(distance.get());
        }
        else {
            return "NO SUCH ROUTE";
        }
    }

    // "path" is of the form "A-B-C"
    private static Optional<Integer> computeDistance(Graph graph, String path) {
        int distance = 0;
        String[] towns = path.split("-");
        for (int i = 0; i < towns.length - 1; i++) {
            Optional<Integer> trackDistance = graph.getDistance(towns[i], towns[i+1]);
            // If there's no track between these two stations, then the overall distance
            // cannot be computed.
            if (!trackDistance.isPresent()) {
                return Optional.empty();
            }

            distance += trackDistance.get();
        }
        return Optional.of(distance);
    }
}
