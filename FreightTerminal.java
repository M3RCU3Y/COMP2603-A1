import java.util.ArrayList;

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

    // M8: group pending packages by destination and make containers
    public int packContainers() {
        ArrayList<String> destinations = new ArrayList<String>();

        // Destination will appear based on order for the package
        for (Package p : pendingPackages) {
            if (!destinations.contains(p.getDestination())) {
                destinations.add(p.getDestination());
            }
        }

        // one container per destination
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

    // M9: move active containers to dispatched containers
    public int dispatchAll() {
        int count = activeContainers.size();
        dispatchedContainers.addAll(activeContainers);
        activeContainers.clear();
        return count;
    }

    // M9: total revenue from dispatched containers only
    public double getTotalRevenue() {
        double total = 0.0;
        for (Container c : dispatchedContainers) {
            total = total + c.getTotalRevenue();
        }
        return total;
    }

    // M9: total packages shipped from dispatched containers only
    public int getTotalPackagesShipped() {
        int total = 0;

        for (Container c : dispatchedContainers) {
            total = total + c.getPackageCount();
        }

        return total;
    }

    // M9: search pending, active, then dispatched
    public Package findPackage(String trackingId) {
        if (trackingId == null) {
            return null;
        }
        // packages that are not packed yet
        for (Package p : pendingPackages) {
            if (p.getTrackingId().equals(trackingId)) {
                return p;
            }
        }
        // search packages inside active containers
        for (Container c : activeContainers) {
            for (Package p : c.getPackages()) {
                if (p.getTrackingId().equals(trackingId)) {
                    return p;
                }
            }
        }



        // search for the packages inside dispatched containers
        for (Container c : dispatchedContainers) {
            for (Package p : c.getPackages()) {
                if (p.getTrackingId().equals(trackingId)) {
                    return p;
                }
            }
        }
        return null;
    }
    // used by Driver to print container manifests
    public ArrayList<Container> getActiveContainers() {
        return activeContainers;
    }
    // M10: print daily report
    public void printDailyReport() {
        int packagesReceived = pendingPackages.size();
        int containersPacked = activeContainers.size() + dispatchedContainers.size();

        // count active packages too, just in case report is printed before dispatch
        for (Container c : activeContainers) {
            packagesReceived = packagesReceived + c.getPackageCount();
        }

        for (Container c : dispatchedContainers) {
            packagesReceived = packagesReceived + c.getPackageCount();
        }

        System.out.println("=== Daily Report: " + terminalName + " ===");
        System.out.println("Packages received: " + packagesReceived);
        System.out.println("Containers packed: " + containersPacked);
        System.out.println("Packages shipped: " + getTotalPackagesShipped());
        System.out.printf("Total revenue: $%.2f%n", getTotalRevenue());

        System.out.println();
        System.out.println("Revenue by destination:");

        for (Container c : dispatchedContainers) {
           System.out.printf(" %-12s $%.2f (%d packages)%n", c.getDestination() + ":", c.getTotalRevenue(), c.getPackageCount());
        }
    }
}