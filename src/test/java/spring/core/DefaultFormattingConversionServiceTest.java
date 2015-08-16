package spring.core;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.support.DefaultFormattingConversionService;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DefaultFormattingConversionServiceTest {

    @Test
    public void testJavaMoney() {
        ConversionService conversionService = new DefaultFormattingConversionService();

        FormattingBean bean = new FormattingBean();
        BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
        beanWrapper.setConversionService(conversionService);

        beanWrapper.setPropertyValue("monetaryAmount", "JPY 100,000");
        beanWrapper.setPropertyValue("currencyUnit", "JPY");

        assertThat(bean.getMonetaryAmount().getNumber().intValue(), is(100000));
        assertThat(bean.getMonetaryAmount().getCurrency().getCurrencyCode(), is("JPY"));
        assertThat(bean.getCurrencyUnit().getCurrencyCode(), is("JPY"));

        String monetaryAmountString = (String) conversionService.convert(bean.getMonetaryAmount(), beanWrapper.getPropertyTypeDescriptor("monetaryAmount"), TypeDescriptor.valueOf(String.class));
        assertThat(monetaryAmountString, is("JPY 100,000"));
        String currencyUnitString = (String) conversionService.convert(bean.getCurrencyUnit(), beanWrapper.getPropertyTypeDescriptor("currencyUnit"), TypeDescriptor.valueOf(String.class));
        assertThat(currencyUnitString, is("JPY"));
    }

    public static class FormattingBean {
        @NumberFormat(pattern = "JPY #,###")
        private MonetaryAmount monetaryAmount;
        private CurrencyUnit currencyUnit;

        public MonetaryAmount getMonetaryAmount() {
            return monetaryAmount;
        }

        public void setMonetaryAmount(MonetaryAmount monetaryAmount) {
            this.monetaryAmount = monetaryAmount;
        }

        public CurrencyUnit getCurrencyUnit() {
            return currencyUnit;
        }

        public void setCurrencyUnit(CurrencyUnit currencyUnit) {
            this.currencyUnit = currencyUnit;
        }
    }
}
