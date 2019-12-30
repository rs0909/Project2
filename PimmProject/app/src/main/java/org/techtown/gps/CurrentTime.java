package org.techtown.gps;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTime
{
    private Date date;
    private String nowTime;
    private long now;

    public CurrentTime()
    {
        now = System.currentTimeMillis();
        date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        nowTime = sdf.format(date);
    }

    public String getNowTime()
    {
        now = System.currentTimeMillis();
        date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        nowTime = sdf.format(date);
        return nowTime;
    }
}
