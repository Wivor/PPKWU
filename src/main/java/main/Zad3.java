package main;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Zad3 {
    String ics() {
        String Url = "http://www.weeia.p.lodz.pl/";

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        Document doc = null;
        try {
            doc = Jsoup.connect(Url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        VEvent vEvent;
        Date date = null;
        for (Element element : doc.getElementsByClass("active").select("td")) {
            SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
            try {
                int day = Integer.parseInt(element.select("a").text()) + 1;
                date = new Date(format.parse(day + "-11-2019"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vEvent = new VEvent(date, element.select("p").text());
            UidGenerator ug = new RandomUidGenerator();
            vEvent.getProperties().add(ug.generateUid());
            calendar.getComponents().add(vEvent);
        }

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("calendar.ics");
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return calendar.toString();
    }
}
