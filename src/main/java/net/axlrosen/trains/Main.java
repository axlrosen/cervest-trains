package net.axlrosen.trains;

import org.junit.Assert;

import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
                .filter(trip -> trip.getLastStation().equals("C"))
                .count();
        System.out.println("Output #6: " + answer6);

        long answer7 = alg.findAllTrips("A", 4).stream()
                .filter(trip -> trip.getLastStation().equals("C"))
                .filter(trip -> trip.path.length() == 5)
                .count();
        System.out.println("Output #7: " + answer7);

        System.out.println("Output #8: " + alg.getShortestRoute("A", "C").get());
        System.out.println("Output #9: " + alg.getShortestRoute("B", "B").get());

        long answer10 = alg.findAllTripsLessThan("C", 30).stream()
                .filter(trip -> trip.getLastStation().equals("C"))
                .count();
        System.out.println("Output #10: " + answer10);

        testAdditionalEdgeCases(alg);
    }

    // Add some tests of additional edge cases. A better structure would be to have
    // real unit tests of the different classes, rather than just these end-to-end tests.
    private static void testAdditionalEdgeCases(Algorithms alg) {
        assertEquals(0, alg.computeDistance("C").get().intValue());
        assertFalse(alg.computeDistance("C-Q").isPresent());
        assertEquals(0, alg.findAllTrips("C", 0).size());
        assertEquals(0, alg.findAllTrips("Q", 3).size());
        assertFalse(alg.getShortestRoute("C", "Q").isPresent());
        assertEquals(0, alg.findAllTripsLessThan("C", 1).size());
        assertEquals(0, alg.findAllTripsLessThan("Q", 1).size());
    }


    private static String writeDistance(Algorithms alg, String path) {
        Optional<Integer> distance = alg.computeDistance(path);
        if (distance.isPresent()) {
            return Integer.toString(distance.get());
        }
        else {
            return "NO SUCH ROUTE";
        }
    }
}
