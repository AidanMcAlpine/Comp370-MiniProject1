package mini_project_01;

import java.util.*;

public class FailoverManager {
    private ElectionAlgorithm electionAlgorithm;

    public FailoverManager() {
        this.electionAlgorithm = new LowestIdElection();
    }

    // Elects a new primary from the list of backups/associated addresses
    // Should notify them of the change but the logic for that is not currently in place
    // (nor is the concept of primary/backup from the server's perspective at all)
    public int initiateFailover(HashMap<Integer, Map.Entry<String, Integer>> backups) {
        int chosenPrimary = electionAlgorithm.electPrimary(new ArrayList<>(backups.keySet()));
        System.out.println("Electing server " + chosenPrimary + " as primary");
        // TODO: notify the primary of their new status, once the server logic is in place to do so
        return chosenPrimary;
    }
}

interface ElectionAlgorithm {
    int electPrimary(ArrayList<Integer> backups);
}

class LowestIdElection implements ElectionAlgorithm {
    @Override
    public int electPrimary(ArrayList<Integer> backups) {
        return backups.stream().min(Integer::compare).orElse(-1);
    }
}