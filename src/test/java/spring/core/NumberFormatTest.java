package spring.core;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.lang.annotation.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NumberFormatTest {

    @Test
    public void testMetaAnnotation() {
        ConversionService conversionService = new DefaultFormattingConversionService();

        FormattingBean bean = new FormattingBean();
        BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
        beanWrapper.setConversionService(conversionService);

        beanWrapper.setPropertyValue("amount", "JPY 100,000");

        assertThat(bean.getAmount(), is(100000L));

        String monetaryAmountString = (String) conversionService.convert(bean.getAmount(), beanWrapper.getPropertyTypeDescriptor("amount"), TypeDescriptor.valueOf(String.class));
        assertThat(monetaryAmountString, is("JPY 100,000"));
    }


    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @NumberFormat(pattern = "JPY #,###")
    public @interface Yen {

    }

    public static class FormattingBean {

        @Yen
        private Long amount;

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }

}
