import java.util.Scanner;

public class leapDayCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of test cases
        int T = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int t = 1; t <= T; t++) {
            // Read the start and end dates
            String startDate = scanner.nextLine();
            String endDate = scanner.nextLine();

            // Extract the years from the dates
            int startYear = extractYear(startDate);
            int endYear = extractYear(endDate);

            // Adjust the years based on February 29 inclusion
            if (!isLeapYear(startYear) || !isAfterOrOnFeb29(startDate)) {
                startYear++;
            }
            if (!isLeapYear(endYear) || !isBeforeOrOnFeb29(endDate)) {
                endYear--;
            }

            // Count the leap years between the adjusted years
            int leapDaysCount = countLeapYears(startYear, endYear);

            // Print the result
            System.out.println("Case " + t + ": " + leapDaysCount);
        }

        scanner.close();
    }

    // Extract year from the date string
    private static int extractYear(String date) {
        String[] parts = date.split(", ");
        return Integer.parseInt(parts[1]);
    }

    // Check if a year is a leap year
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

    // Check if the date is on or after February 29
    private static boolean isAfterOrOnFeb29(String date) {
        String[] parts = date.split(" ");
        String month = parts[0];
        int day = Integer.parseInt(parts[1].replace(",", ""));
        if (month.equals("January")) {
            return false;
        }
        if (month.equals("February") && day < 29) {
            return false;
        }
        return true;
    }

    // Check if the date is on or before February 29
    private static boolean isBeforeOrOnFeb29(String date) {
        String[] parts = date.split(" ");
        String month = parts[0];
        int day = Integer.parseInt(parts[1].replace(",", ""));
        if (!month.equals("February") && !month.equals("January")) {
            return false;
        }
        if (month.equals("February") && day > 29) {
            return false;
        }
        return true;
    }

    // Count the number of leap years between two years (inclusive)
    private static int countLeapYears(int startYear, int endYear) {
        if (startYear > endYear) {
            return 0;
        }
        return leapYearCount(endYear) - leapYearCount(startYear - 1);
    }

    // Calculate the total number of leap years up to a given year
    private static int leapYearCount(int year) {
        return (year / 4) - (year / 100) + (year / 400);
    }
}