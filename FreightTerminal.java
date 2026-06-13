import java.util.ArrayList;

/**
 * The hub that receives packages, packs them into containers,
 * dispatches containers, and produces financial reports.
 */
public class FreightTerminal {

    private String terminalName;
    private ArrayList<Package> pendingPackages;
    private ArrayList<Container> activeContainers;
    private ArrayList<Container> dispatchedContainers;

    // M2: constructor
    public FreightTerminal(String terminalName) {
        this.terminalName = terminalName;
        this.pendingPackages = new ArrayList<Package>();
        this.activeContainers = new ArrayList<Container>();
        this.dispatchedContainers = new ArrayList<Container>();
    }

    // M4: receive a package if it is not null
    public void receivePackage(Package p) {
        if (p != null) {
            pendingPackages.add(p);
        }
    }

    // M4: number of packages waiting to be packed
    public int getPendingCount() {
        return pendingPackages.size();
    }
        public int packContainers() {
        // WIP M8:
        

        ArrayList<String> destinations = new ArrayList<String>();

        for (Package p : pendingPackages) {
            String dest = p.getDestination();

            if (!destinations.contains(dest)) {
                destinations.add(dest);
            }
        }

        for (String dest : destinations) {
            Container c = new Container(dest);

            for (Package p : pendingPackages) {
                if (p.getDestination().equals(dest)) {
                    c.addPackage(p);
                }
            }

            activeContainers.add(c);
        }

        pendingPackages.clear();

        return destinations.size();
    }

    public int dispatchAll() {
        // TODO M9:
        // testing packing
        return 0;
    }

    public double getTotalRevenue() {
        // TODO M9:
        return 0.0;
    }

    public int getTotalPackagesShipped() {
        // TODO M9:
        return 0;
    }

    public Package findPackage(String trackingId) {
        // WIP M9:
        // Right now this only searches pending packages.
        // Later I need to search active and dispatched containers too.

        for (Package p : pendingPackages) {
            if (p.getTrackingId().equals(trackingId)) {
                return p;
            }
        }

        return null;
    }

    public ArrayList<Container> getActiveContainers() {
        return activeContainers;
    }

    public void printDailyReport() {
        // TODO M10:
        // Temporary debug report, not final assignment format.

        System.out.println("DEBUG REPORT FOR " + terminalName);
        System.out.println("Pending packages: " + pendingPackages.size());
        System.out.println("Active containers: " + activeContainers.size());
        System.out.println("Dispatched containers: " + dispatchedContainers.size());
    }

}