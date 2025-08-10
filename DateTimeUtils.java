package com.example.event_planner.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static final String DATE_FORMAT = "MMM dd, yyyy";
    public static final String TIME_FORMAT = "hh:mm a";
    public static final String DATETIME_FORMAT = "MMM dd, yyyy 'at' hh:mm a";
    public static final String FULL_DATETIME_FORMAT = "EEEE, MMM dd, yyyy 'at' hh:mm a";

    public static String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatDateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatFullDateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATETIME_FORMAT, Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String formatEventDateTime(long startTime, long endTime) {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.setTimeInMillis(startTime);
        endCal.setTimeInMillis(endTime);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());

        String startDate = dateFormat.format(startCal.getTime());
        String startTimeStr = timeFormat.format(startCal.getTime());
        String endTimeStr = timeFormat.format(endCal.getTime());
        if (isSameDay(startCal, endCal)) {
            return startDate + " from " + startTimeStr + " to " + endTimeStr;
        } else {
            String endDate = dateFormat.format(endCal.getTime());
            return startDate + " " + startTimeStr + " - " + endDate + " " + endTimeStr;
        }
    }

    public static String getRelativeTime(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = timestamp - now;

        if (Math.abs(diff) < 60000) {
            return "Now";
        } else if (diff > 0) {
            if (diff < 3600000) {
                int minutes = (int) (diff / 60000);
                return "In " + minutes + " minute" + (minutes > 1 ? "s" : "");
            } else if (diff < 86400000) {
                int hours = (int) (diff / 3600000);
                return "In " + hours + " hour" + (hours > 1 ? "s" : "");
            } else if (diff < 604800000) {
                int days = (int) (diff / 86400000);
                if (days == 1) return "Tomorrow";
                return "In " + days + " days";
            } else {
                return formatDate(timestamp);
            }
        } else {
            diff = Math.abs(diff);
            if (diff < 3600000) {
                int minutes = (int) (diff / 60000);
                return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
            } else if (diff < 86400000) {
                int hours = (int) (diff / 3600000);
                return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
            } else if (diff < 604800000) {
                int days = (int) (diff / 86400000);
                if (days == 1) return "Yesterday";
                return days + " days ago";
            } else {
                return formatDate(timestamp);
            }
        }
    }

    public static String getTimeUntilEvent(long eventTime) {
        long now = System.currentTimeMillis();
        long diff = eventTime - now;

        if (diff <= 0) {
            return "Event has passed";
        }

        if (diff < 3600000) {
            int minutes = (int) (diff / 60000);
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " left";
        } else if (diff < 86400000) {
            int hours = (int) (diff / 3600000);
            return hours + " hour" + (hours > 1 ? "s" : "") + " left";
        } else {
            int days = (int) (diff / 86400000);
            return days + " day" + (days > 1 ? "s" : "") + " left";
        }
    }
    public static boolean isSameDay(long timestamp1, long timestamp2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(timestamp1);
        cal2.setTimeInMillis(timestamp2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static long getStartOfDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getEndOfDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    public static long getTimestamp(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth, hourOfDay, minute, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long addMinutes(long timestamp, int minutes) {
        return timestamp + (minutes * 60 * 1000L);
    }

    public static long addHours(long timestamp, int hours) {
        return timestamp + (hours * 60 * 60 * 1000L);
    }

    public static long addDays(long timestamp, int days) {
        return timestamp + (days * 24 * 60 * 60 * 1000L);
    }

    public static boolean isToday(long timestamp) {
        return isSameDay(timestamp, System.currentTimeMillis());
    }

    public static boolean isTomorrow(long timestamp) {
        return isSameDay(timestamp, System.currentTimeMillis() + 86400000);
    }

    public static boolean isThisWeek(long timestamp) {
        Calendar now = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTimeInMillis(timestamp);

        return now.get(Calendar.WEEK_OF_YEAR) == target.get(Calendar.WEEK_OF_YEAR) &&
                now.get(Calendar.YEAR) == target.get(Calendar.YEAR);
    }
}