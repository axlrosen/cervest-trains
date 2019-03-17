package net.axlrosen.trains;

import java.util.*;

public class Algorithms {

    private final Graph graph;

    public Algorithms(Graph graph) {
        this.graph = graph;
    }

    // "path" is of the form "A-B-C"
    public Optional<Integer> computeDistance(String path) {
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

    public List<String> findAllTrips(String startStation, int maxStops) {
        // 'currentTrips' contains each trip as we are building them up, station by station.
        // So every trip in 'currentTrips' always have the same length. Whereas 'result'
        // contains each trip that we build along the way, so it will contain trips
        // of different lengths.
        List<String> result = new ArrayList<>();
        List<String> currentTrips = new ArrayList<>();

        currentTrips.add(startStation);

        for (int i = 0; i < maxStops; i++) {
            currentTrips = addOneStop(currentTrips);
            result.addAll(currentTrips);
        }

        return result;
    }


    // Return all possible trips that are one stop longer than the input list of trips.
    private List<String> addOneStop(List<String> trips) {
        List<String> result = new ArrayList<>();
        for (String trip : trips) {
            String lastStation = trip.substring(trip.length() - 1);
            Map<String, Integer> tracks = graph.getTracks(lastStation);
            for (String destStation : tracks.keySet()) {
                result.add(trip + destStation);
            }
        }
        return result;
    }

    // Return the total distance of the shortest route to the given destination.
    public int getShortestRoute(String from, String to) {
        Map<String, Integer> trips = new HashMap<>();
        trips.put(from, 0);

        boolean foundImprovement;
        do {
            foundImprovement = false;
            // For each trip we've computed so far, extend it to all possible next stops.
            // We need to copy the current set of trips, because we'll be modifying it
            // as we go.
            Set<Map.Entry<String, Integer>> tripsClone = new HashSet<>(trips.entrySet());
            for (Map.Entry<String, Integer> existingTripEntry : tripsClone) {
                String existingTrip = existingTripEntry.getKey();
                int existingTripDistance = existingTripEntry.getValue();
                String lastStation = existingTrip.substring(existingTrip.length() - 1);
                // Find all the tracks that extend the current trip.
                Map<String, Integer> tracksFromExistingTrip = graph.getTracks(lastStation);
                for (Map.Entry<String, Integer> track : tracksFromExistingTrip.entrySet()) {
                    // If this new trip (i.e. existing trip plus new track) is better than
                    // the best trip to that destination, then we've improved things.
                    int newDistance = existingTripDistance + track.getValue();
                    if (foundBetterTrip(trips, track.getKey(), newDistance)) {
                        trips.put(track.getKey(), newDistance);
                        foundImprovement = true;
                    }
                }
            }
        } while(foundImprovement); // go until we haven't improved any trip

        // Note: We could also end more quickly. If our best distance to our target is currently 5,
        // and our best distance to all other towns is > 5, then we can stop now. I didn't add this
        // because in this case I favored readability over efficiency, but if speed is important,
        // that condition could be added.

        return trips.get(to);
    }

    // If we have no trip to this destination, or if the new trip is shorter than the old trip,
    // then we've found a better trip.
    private boolean foundBetterTrip(Map<String, Integer> trips, String dest, int newDistance) {
        if (!trips.containsKey(dest)) {
            return true;
        }
        int oldDistance = trips.get(dest);
        return (oldDistance == 0 || newDistance < oldDistance);
    }

}
