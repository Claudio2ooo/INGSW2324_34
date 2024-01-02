package it.unina.dietideals24.utils;

public class ConvertSecondsToHourMinuteSeconds {
    private ConvertSecondsToHourMinuteSeconds() {}

    /**
     * Formats an interval from seconds to hh:mm:ss
     * @param timeInSeconds interval in seconds
     * @return the interval formatted in hh:mm:ss
     */
    public static String formatSeconds(Long timeInSeconds) {
        long days = timeInSeconds / 86400;
        long hours = (timeInSeconds / 3600) % 24;
        long secondsLeft = timeInSeconds % 60;
        long minutes = (timeInSeconds /60) % 60;

        if (days > 0)
            return days + "d:" + minutes + "m" + secondsLeft + "s";
        else if (hours == 0)
            return minutes + "m:" + secondsLeft + "s";
        else
            return hours + "h:" + minutes + "m" + secondsLeft + "s";
    }

/*
    /**
     * Formats an interval from seconds to hh:mm:ss
     * @param timeInSeconds interval in seconds
     * @return the interval formatted in hh:mm:ss
     */
//    public static String formatSeconds(long timeInSeconds)
//    {
//        long hours = timeInSeconds / 3600;
//        long secondsLeft = timeInSeconds - hours * 3600;
//        long minutes = secondsLeft / 60;
//        long seconds = secondsLeft - minutes * 60;
//
//        String formattedTime = "";
//        if (hours < 10)
//            formattedTime += "0";
//        formattedTime += hours + "h:";
//
//        if (minutes < 10)
//            formattedTime += "0";
//        formattedTime += minutes + "m:";
//
//        if (seconds < 10)
//            formattedTime += "0";
//        formattedTime += seconds + "s";
//
//        return formattedTime;
//    }
}
