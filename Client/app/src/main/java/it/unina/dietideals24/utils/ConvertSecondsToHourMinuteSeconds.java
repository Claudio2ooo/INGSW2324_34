package it.unina.dietideals24.utils;

public class ConvertSecondsToHourMinuteSeconds {
    private ConvertSecondsToHourMinuteSeconds() {}

    /**
     * Formats an interval from seconds to hh:mm:ss
     *
     * @param timeInSeconds interval in seconds
     * @return the interval formatted in hh:mm:ss
     */
    public static String formatSeconds(Long timeInSeconds) {
        long days = timeInSeconds / 86400;
        long hours = (timeInSeconds / 3600) % 24;
        long secondsLeft = timeInSeconds % 60;
        long minutes = (timeInSeconds / 60) % 60;

        if (days > 0)
            return days + "d:" + minutes + "m:" + secondsLeft + "s";
        else if (hours == 0)
            return minutes + "m:" + secondsLeft + "s";
        else
            return hours + "h:" + minutes + "m:" + secondsLeft + "s";
    }
}