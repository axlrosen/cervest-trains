package net.axlrosen.trains;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Graph {
    private Map<String, Integer> tracks = new HashMap<>();

    // Construct a graph from a string of the form "AB2, BC3, CD999, ..."
    public Graph(String trackList) {
        String[] trackStrings = trackList.split(", ");
        for (String trackString : trackStrings) {
            if (trackString.length() < 3) {
                throw new RuntimeException("Invalid track string: " + trackString);
            }
            String key = trackString.substring(0, 2);
            tracks.put(key, parseDistance(trackString));
        }
    }

    public Optional<Integer> getDistance(String from, String to) {
        String key = from + to;
        if (tracks.containsKey(key)) {
            return Optional.of(tracks.get(key));
        }
        return Optional.empty();
    }

    public Map<String, Integer> getTracks(String from) {
        // This is not efficient. If efficiency matters, we should instead hold
        // a map of maps. This would map each "from" station to a map of "to" stations
        // to distances.
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> track : tracks.entrySet()) {
            if (track.getKey().startsWith(from)) {
                result.put(track.getKey().substring(1), track.getValue());
            }
        }
        return result;
    }

    private static int parseDistance(String trackString) {
        try {
            return Integer.parseInt(trackString.substring(2));
        }
        catch(NumberFormatException ex) {
            throw new RuntimeException("Invalid track string: " + trackString);
        }
    }
}

