import java.util.ArrayList;

public class Container {

    // M1 - static counter starts at 1
    private static int nextContainerId = 1;
    private String containerId;
    private String destination;
    private double maxWeightKg;
    private ArrayList<Package> packages;

    // M2: full constructor
    public Container(String destination, double maxWeightKg) {

        if (destination == null) {
            throw new IllegalArgumentException("Destination must not be null");
        }
        if (maxWeightKg <= 0) {
            throw new IllegalArgumentException("Max weight must be greater than 0");
        }
        this.containerId = String.format("CNT-%03d", nextContainerId);
        nextContainerId++;

        this.destination = destination;
        this.maxWeightKg = maxWeightKg;
        this.packages = new ArrayList<Package>();
    }

    // M3: shorter constructor, default capacity is 500 kg
    public Container(String destination) {
        this(destination, 500.0);
    }

    // M4: getters
    public String getContainerId() {
        return containerId;
    }

    public String getDestination() {
        return destination;
    }

    public double getMaxWeightKg() {
        return maxWeightKg;
    }

    // M8: add package if it is valid for this container
    public boolean addPackage(Package p) {

        if (p == null) {
            return false;
        }
        if (!p.getDestination().equals(destination)) {
            return false;
        }
        if (getCurrentWeightKg() + p.getWeightKg() > maxWeightKg) {
            return false;
        }
        packages.add(p);
        return true;
    }


    // M8: sum actual package weight, not billable weight
    public double getCurrentWeightKg() {
        double total = 0.0;
        for (Package p : packages) {
            total = total + p.getWeightKg();
        }

        return total;
    }

    //M8: remaining actual weight capacity
    public double getRemainingCapacityKg() {
        return maxWeightKg - getCurrentWeightKg();
    }
    // M8: sum the shipping costs of all packages
    public double getTotalRevenue() {
        double total = 0.0;
        for (Package p : packages) {
            total = total + p.getShippingCost();
        }

        return total;
    }

    // M8: number of packages inside
    public int getPackageCount() {
        return packages.size();
    }

    // M9: full multi-line manifest
    public String getManifest() {
        StringBuilder text = new StringBuilder();
        text.append(String.format("=== %s -> %s (%d packages, %.2f / %.2f kg) ===",
                containerId,
                destination,
                getPackageCount(),
                getCurrentWeightKg(),
                maxWeightKg));

        for (Package p : packages) {
            text.append("\n");
            text.append("  ");
            text.append(p.toString());
        }

        text.append("\n");
        text.append(String.format("  Container revenue: $%.2f", getTotalRevenue()));
        return text.toString();
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    // M9 summary
    @Override
    public String toString() {
        return String.format("%s -> %s [%d packages, %.2f / %.2f kg]",
                containerId,
                destination,
                getPackageCount(),
                getCurrentWeightKg(),
                maxWeightKg);
    }
}
