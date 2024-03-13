package biz.picosoft.demo.config;

import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        try {
            return ZonedDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            // Gérez les erreurs de conversion ici si nécessaire
            return null;
        }
    }

}

