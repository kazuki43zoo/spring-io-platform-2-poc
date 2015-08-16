package spring.core;

import org.junit.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DefaultConversionServiceTest {

    @Test
    public void testStreamToList() {
        ConversionService conversionService = new DefaultConversionService();
        List<String> list = conversionService.convert(Arrays.asList("1", "2").stream(), List.class);

        assertThat(list.size(), is(2));
        assertThat(list.get(0), is("1"));
        assertThat(list.get(1), is("2"));
    }

    @Test
    public void testListToStream() {
        ConversionService conversionService = new DefaultConversionService();
        Stream<String> stream = conversionService.convert(Arrays.asList("1", "2"), Stream.class);


        Iterator<String> iterator = stream.iterator();
        assertThat(iterator.next(), is("1"));
        assertThat(iterator.next(), is("2"));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testStringToCharset() {
        ConversionService conversionService = new DefaultConversionService();
        Charset charset = conversionService.convert("UTF-8", Charset.class);

        assertThat(charset, is(StandardCharsets.UTF_8));
    }

    @Test
    public void testStringToCurrency() {
        ConversionService conversionService = new DefaultConversionService();
        Currency currency = conversionService.convert("JPY", Currency.class);

        assertThat(currency, is(Currency.getInstance("JPY")));
        assertThat(currency, is(Currency.getInstance(Locale.JAPAN)));
        assertThat(currency.getSymbol(), is("￥"));

    }

    @Test
    public void testStringToTimeZone() {
        ConversionService conversionService = new DefaultConversionService();
        TimeZone timeZone = conversionService.convert("Asia/Tokyo", TimeZone.class);

        assertThat(timeZone, is(TimeZone.getTimeZone("Asia/Tokyo")));
        assertThat(timeZone.getRawOffset(), is(32400000));

    }

}
