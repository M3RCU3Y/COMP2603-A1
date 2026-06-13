import java.util.Arrays;
import java.util.List;

public class Package {

    // M1 - static counter to 1
    private static int nextTrackingNumber = 1;

    private static final List<String> VALID_DESTINATIONS = Arrays.asList(
        "Trinidad", "Barbados", "Jamaica", "Antigua", "Grenada"
    );

    private String trackingId;
    private String senderName;
    private String receiverName;
    private double weightKg;
    private int lengthCm;
    private int widthCm;
    private int heightCm;
    private String destination;
    private boolean isFragile;
    private double declaredValue;



    // M2 constructor implemented

    public Package(String senderName, String receiverName, double weightKg,
                   int lengthCm, int widthCm, int heightCm,
                   String destination, boolean isFragile, double declaredValue) {

        // checks for: 
        // sender
        //reciver
        // weight
        // dimensions
        // destination
        // sender

        if (senderName == null || senderName.equals("")) {
            throw new IllegalArgumentException("Sender name must not be null or empty");
        }

        if (receiverName == null || receiverName.equals("")) {
            throw new IllegalArgumentException("Receiver name must not be null or empty");
        }

        if (weightKg <= 0)  {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }

        if (lengthCm <= 0 || widthCm <= 0 || heightCm <= 0) {
            throw new IllegalArgumentException("Dimensions must be greater than 0");
        }

        if (!VALID_DESTINATIONS.contains(destination)) {
            throw new IllegalArgumentException("Invalid destination");
        }

        this.senderName = senderName;
        this.receiverName = receiverName;
        this.weightKg = weightKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.destination = destination;
        this.isFragile = isFragile;
        this.declaredValue = declaredValue;

        // tracking number
        this.trackingId = String.format("PKG-%04d", nextTrackingNumber);
        nextTrackingNumber++;
    }

    // M3: shorter constructor

    public Package(String senderName, String receiverName, double weightKg,
                   int lengthCm, int widthCm, int heightCm, String destination) {

        this(senderName, receiverName, weightKg, lengthCm, widthCm, heightCm,
             destination, false, 0.0);
    }

    // --- Getters ---
    public String getTrackingId() {
        return trackingId;
    }
    public String getSenderName() {
        return senderName;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public double getWeightKg() {
        return weightKg;
    }
    public int getLengthCm() {
        return lengthCm;
    }
    public int getWidthCm() {
        return widthCm;
    }
    public int getHeightCm() {
        return heightCm;
    }
    public String getDestination() {
        return destination;
    }
    public boolean isFragile() {
        return isFragile;
    }
    public double getDeclaredValue() {
        return declaredValue;
    }

    // --- Computed methods ---
    // M5 
    public int getVolumeCm3() {
        return lengthCm * widthCm * heightCm;
    }

    public double getVolumetricWeightKg() {
        return getVolumeCm3() / 5000.0;
    }

    public double getBillableWeightKg() {
        return Math.max(weightKg, getVolumetricWeightKg());
    }

    // shipping cost formula M6
    public double getShippingCost() {
    double ratePerKg;

    switch (destination) {
        case "Trinidad":
            ratePerKg = 8.00;
            break;
        case "Barbados":
            ratePerKg = 12.50;
            break;
        case "Jamaica":
            ratePerKg = 15.00;
            break;
        case "Antigua":
            ratePerKg = 18.00;
            break;
        case "Grenada":
            ratePerKg = 10.00;
            break;
        default:
            ratePerKg = 0.0;
            break;
    }

        double cost = getBillableWeightKg() * ratePerKg;

        if (isFragile) {
            cost = cost * 1.25;
        }

        if (declaredValue > 0) {
            cost = cost + declaredValue * 0.015;
        }

        return Math.round(cost * 100) / 100.0;
    }

    @Override
    public String toString() {
        String text = String.format("%s %s -> %s %s %.2f kg $%.2f",
                trackingId,
                senderName,
                receiverName,
                destination,
                getBillableWeightKg(),
                getShippingCost());

        if (isFragile) {
            text = text + " [FRAGILE]";
        }

        return text;
    }
}

