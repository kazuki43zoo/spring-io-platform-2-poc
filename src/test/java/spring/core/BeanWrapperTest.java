package spring.core;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BeanWrapperTest {

    @Test
    public void testBeanWrapperGrowIsFalse() {
        RootBean rootBean = new RootBean();
        BeanWrapper beanWrapper = new BeanWrapperImpl(rootBean);
        beanWrapper.setPropertyValue("stringValue", "test");
        beanWrapper.setPropertyValue("longValue", "1");
        beanWrapper.setPropertyValue("intValue", "2");
        beanWrapper.setPropertyValue("nestedBean", new ChildBean());
        beanWrapper.setPropertyValue("nestedBean.stringValue", "nested test");
        beanWrapper.setPropertyValue("nestedBean.longValue", "10");
        beanWrapper.setPropertyValue("nestedBean.intValue", "20");
        beanWrapper.setPropertyValue("nestedBeans", Arrays.asList(new ChildBean()));
        beanWrapper.setPropertyValue("nestedBeans[0].stringValue", "list test");
        beanWrapper.setPropertyValue("nestedBeans[0].longValue", "100");
        beanWrapper.setPropertyValue("nestedBeans[0].intValue", "200");
        beanWrapper.setPropertyValue("nestedBeanMap", Collections.singletonMap("key1", new ChildBean()));
        beanWrapper.setPropertyValue("nestedBeanMap[key1].stringValue", "map test");
        beanWrapper.setPropertyValue("nestedBeanMap[key1].longValue", "1000");
        beanWrapper.setPropertyValue("nestedBeanMap[key1].intValue", "2000");
        beanWrapper.setPropertyValue("nestedBeanArray", new ChildBean[]{new ChildBean()});
        beanWrapper.setPropertyValue("nestedBeanArray[0].stringValue", "array test");
        beanWrapper.setPropertyValue("nestedBeanArray[0].longValue", "10000");
        beanWrapper.setPropertyValue("nestedBeanArray[0].intValue", "20000");

        assertThat(rootBean.getStringValue(), is("test"));
        assertThat(beanWrapper.getPropertyValue("stringValue"), is("test"));
        assertThat(rootBean.getLongValue(), is(1L));
        assertThat(beanWrapper.getPropertyValue("longValue"), is(1L));
        assertThat(rootBean.getIntValue(), is(2));
        assertThat(beanWrapper.getPropertyValue("intValue"), is(2));

        assertThat(rootBean.getNestedBean().getStringValue(), is("nested test"));
        assertThat(beanWrapper.getPropertyValue("nestedBean.stringValue"), is("nested test"));
        assertThat(rootBean.getNestedBean().getLongValue(), is(10L));
        assertThat(beanWrapper.getPropertyValue("nestedBean.longValue"), is(10L));
        assertThat(rootBean.getNestedBean().getIntValue(), is(20));
        assertThat(beanWrapper.getPropertyValue("nestedBean.intValue"), is(20));

        assertThat(rootBean.getNestedBeans().get(0).getStringValue(), is("list test"));
        assertThat(beanWrapper.getPropertyValue("nestedBeans[0].stringValue"), is("list test"));
        assertThat(rootBean.getNestedBeans().get(0).getLongValue(), is(100L));
        assertThat(beanWrapper.getPropertyValue("nestedBeans[0].longValue"), is(100L));
        assertThat(rootBean.getNestedBeans().get(0).getIntValue(), is(200));
        assertThat(beanWrapper.getPropertyValue("nestedBeans[0].intValue"), is(200));

        assertThat(rootBean.getNestedBeanMap().get("key1").getStringValue(), is("map test"));
        assertThat(beanWrapper.getPropertyValue("nestedBeanMap[key1].stringValue"), is("map test"));
        assertThat(rootBean.getNestedBeanMap().get("key1").getLongValue(), is(1000L));
        assertThat(beanWrapper.getPropertyValue("nestedBeanMap[key1].longValue"), is(1000L));
        assertThat(rootBean.getNestedBeanMap().get("key1").getIntValue(), is(2000));
        assertThat(beanWrapper.getPropertyValue("nestedBeanMap[key1].intValue"), is(2000));

        assertThat(rootBean.getNestedBeanArray()[0].getStringValue(), is("array test"));
        assertThat(beanWrapper.getPropertyValue("nestedBeanArray[0].stringValue"), is("array test"));
        assertThat(rootBean.getNestedBeanArray()[0].getLongValue(), is(10000L));
        assertThat(beanWrapper.getPropertyValue("nestedBeanArray[0].longValue"), is(10000L));
        assertThat(rootBean.getNestedBeanArray()[0].getIntValue(), is(20000));
        assertThat(beanWrapper.getPropertyValue("nestedBeanArray[0].intValue"), is(20000));

    }

    @Test
    public void testBeanWrapperGrowIsTrue() {
        RootBean rootBean = new RootBean();
        BeanWrapper beanWrapper = new BeanWrapperImpl(rootBean);
        beanWrapper.setAutoGrowNestedPaths(true);
        beanWrapper.setPropertyValue("stringValue", "test");
        beanWrapper.setPropertyValue("longValue", "1");
        beanWrapper.setPropertyValue("intValue", "2");
        beanWrapper.setPropertyValue("nestedBean.stringValue", "nested test");
        beanWrapper.setPropertyValue("nestedBean.longValue", "10");
        beanWrapper.setPropertyValue("nestedBean.intValue", "20");
        beanWrapper.setPropertyValue("nestedBeans[0].stringValue", "list test");
        beanWrapper.setPropertyValue("nestedBeans[0].longValue", "100");
        beanWrapper.setPropertyValue("nestedBeans[0].intValue", "200");
        beanWrapper.setPropertyValue("nestedBeanMap[key1].stringValue", "map test");
        beanWrapper.setPropertyValue("nestedBeanMap[key1].longValue", "1000");
        beanWrapper.setPropertyValue("nestedBeanMap[key1].intValue", "2000");
        beanWrapper.setPropertyValue("nestedBeanArray[0].stringValue", "array test");
        beanWrapper.setPropertyValue("nestedBeanArray[0].longValue", "10000");
        beanWrapper.setPropertyValue("nestedBeanArray[0].intValue", "20000");

        assertThat(rootBean.getStringValue(), is("test"));
        assertThat(beanWrapper.getPropertyValue("stringValue"), is("test"));
        assertThat(rootBean.getLongValue(), is(1L));
        assertThat(beanWrapper.getPropertyValue("longValue"), is(1L));
        assertThat(rootBean.getIntValue(), is(2));
        assertThat(beanWrapper.getPropertyValue("intValue"), is(2));

        assertThat(rootBean.getNestedBean().getStringValue(), is("nested test"));
        assertThat(beanWrapper.getPropertyValue("nestedBean.stringValue"), is("nested test"));
        assertThat(rootBean.getNestedBean().getLongValue(), is(10L));
        assertThat(beanWrapper.getPropertyValue("nestedBean.longValue"), is(10L));
        assertThat(rootBean.getNestedBean().getIntValue(), is(20));
        assertThat(beanWrapper.getPropertyValue("nestedBean.intValue"), is(20));

        assertThat(rootBean.getNestedBeans().get(0).getStringValue(), is("list test"));
        assertThat(beanWrapper.getPropertyValue("nestedBeans[0].stringValue"), is("list test"));
        assertThat(rootBean.getNestedBeans().get(0).getLongValue(), is(100L));
        assertThat(beanWrapper.getPropertyValue("nestedBeans[0].longValue"), is(100L));
        assertThat(rootBean.getNestedBeans().get(0).getIntValue(), is(200));
        assertThat(beanWrapper.getPropertyValue("nestedBeans[0].intValue"), is(200));

        assertThat(rootBean.getNestedBeanMap().get("key1").getStringValue(), is("map test"));
        assertThat(beanWrapper.getPropertyValue("nestedBeanMap[key1].stringValue"), is("map test"));
        assertThat(rootBean.getNestedBeanMap().get("key1").getLongValue(), is(1000L));
        assertThat(beanWrapper.getPropertyValue("nestedBeanMap[key1].longValue"), is(1000L));
        assertThat(rootBean.getNestedBeanMap().get("key1").getIntValue(), is(2000));
        assertThat(beanWrapper.getPropertyValue("nestedBeanMap[key1].intValue"), is(2000));

        assertThat(rootBean.getNestedBeanArray()[0].getStringValue(), is("array test"));
        assertThat(beanWrapper.getPropertyValue("nestedBeanArray[0].stringValue"), is("array test"));
        assertThat(rootBean.getNestedBeanArray()[0].getLongValue(), is(10000L));
        assertThat(beanWrapper.getPropertyValue("nestedBeanArray[0].longValue"), is(10000L));
        assertThat(rootBean.getNestedBeanArray()[0].getIntValue(), is(20000));
        assertThat(beanWrapper.getPropertyValue("nestedBeanArray[0].intValue"), is(20000));

    }

    public static class RootBean {
        private String a;
        private Long b;
        private int c;
        private ChildBean d;
        private List<ChildBean> e;
        private Map<String, ChildBean> f;
        private ChildBean[] g;

        public String getStringValue() {
            return a;
        }

        public void setStringValue(String stringValue) {
            this.a = stringValue;
        }

        public Long getLongValue() {
            return b;
        }

        public void setLongValue(Long longValue) {
            this.b = longValue;
        }

        public int getIntValue() {
            return c;
        }

        public void setIntValue(int intValue) {
            this.c = intValue;
        }

        public ChildBean getNestedBean() {
            return d;
        }

        public void setNestedBean(ChildBean nestedBean) {
            this.d = nestedBean;
        }

        public List<ChildBean> getNestedBeans() {
            return e;
        }

        public void setNestedBeans(List<ChildBean> nestedBeans) {
            this.e = nestedBeans;
        }

        public Map<String, ChildBean> getNestedBeanMap() {
            return f;
        }

        public void setNestedBeanMap(Map<String, ChildBean> nestedBeanMap) {
            this.f = nestedBeanMap;
        }

        public ChildBean[] getNestedBeanArray() {
            return g;
        }

        public void setNestedBeanArray(ChildBean[] nestedBeanArray) {
            this.g = nestedBeanArray;
        }
    }

    public static class ChildBean {
        private String h;
        private Long i;
        private int j;

        public String getStringValue() {
            return h;
        }

        public void setStringValue(String stringValue) {
            this.h = stringValue;
        }

        public Long getLongValue() {
            return i;
        }

        public void setLongValue(Long longValue) {
            this.i = longValue;
        }

        public int getIntValue() {
            return j;
        }

        public void setIntValue(int intValue) {
            this.j = intValue;
        }
    }

}
