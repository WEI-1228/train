package cn.anlper.train.utils;

public class TrainUtil {
    public static int getIndex(String seatTypeCode, String seat) {
        if (seatTypeCode.equals("1")) return getYdzIndex(seat);
        if (seatTypeCode.equals("2")) return getEdzIndex(seat);
        return 0;
    }

    private static int getYdzIndex(String seat) {
        return switch (seat) {
            case "A1" -> 0;
            case "C1" -> 1;
            case "D1" -> 2;
            case "F1" -> 3;
            case "A2" -> 4;
            case "C2" -> 5;
            case "D2" -> 6;
            case "F2" -> 7;
            default -> 0;
        };
    }

    private static int getEdzIndex(String seat) {
        return switch (seat) {
            case "A1" -> 0;
            case "B1" -> 1;
            case "C1" -> 2;
            case "D1" -> 3;
            case "F1" -> 4;
            case "A2" -> 5;
            case "B2" -> 6;
            case "C2" -> 7;
            case "D2" -> 8;
            case "F2" -> 9;
            default -> 0;
        };
    }
}
