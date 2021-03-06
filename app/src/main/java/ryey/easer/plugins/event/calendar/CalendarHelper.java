package ryey.easer.plugins.event.calendar;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.Calendar;

@SuppressLint("MissingPermission")
class CalendarHelper {

    static String getCalendarName(ContentResolver contentResolver, long calendar_id) {
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] PROJECTION = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
        };
        String SELECTION = "((" + CalendarContract.Calendars._ID + " = ?))";
        String[] ARGS = new String[]{
                String.valueOf(calendar_id),
        };
        Cursor cur = contentResolver.query(uri, PROJECTION, SELECTION, ARGS, null);
        if (cur == null)
            return null;
        if (cur.moveToNext())
            return cur.getString(1);
        else
            return null;
    }

    static Long nextEvent_start(ContentResolver contentResolver, long calendar_id) {
        final String[] EVENT_PROJECTION = new String[] {
                CalendarContract.Events._ID,                           // 0
                CalendarContract.Events.DTSTART,                  // 1
                CalendarContract.Events.DTEND,         // 2
        };
        Calendar calendar = Calendar.getInstance();
        long current_time = calendar.getTimeInMillis();

        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "((" + CalendarContract.Events.CALENDAR_ID + " = ?)" +
                " AND (" + CalendarContract.Events.DTSTART + " > ?)" +
                ")";
        String[] selectionArgs = new String[] {
                String.valueOf(calendar_id),
                String.valueOf(current_time),
        };
        Cursor cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs,
                CalendarContract.Events.DTSTART + " ASC");
        if (cur == null)
            return null;
        if (cur.moveToNext())
            return cur.getLong(1);
        else
            return null;
    }

    static Long nextEvent_end(ContentResolver contentResolver, long calendar_id) {
        final String[] EVENT_PROJECTION = new String[] {
                CalendarContract.Events._ID,                           // 0
                CalendarContract.Events.DTSTART,                  // 1
                CalendarContract.Events.DTEND,         // 2
        };
        Calendar calendar = Calendar.getInstance();
        long current_time = calendar.getTimeInMillis();

        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "((" + CalendarContract.Events.CALENDAR_ID + " = ?)" +
                " AND (" + CalendarContract.Events.DTEND + " > ?)" +
                ")";
        String[] selectionArgs = new String[] {
                String.valueOf(calendar_id),
                String.valueOf(current_time),
        };
        Cursor cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs,
                CalendarContract.Events.DTEND + " ASC");
        if (cur == null)
            return null;
        if (cur.moveToNext())
            return cur.getLong(2);
        else
            return null;
    }
}
