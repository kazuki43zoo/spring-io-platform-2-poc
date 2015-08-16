package spring.core;

import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DirectFieldAccessorTest {

    @Test
    public void testDirectFieldAccessorGrowIsFalse() {
        RootBean rootBean = new RootBean();
        DirectFieldAccessor accessor = new DirectFieldAccessor(rootBean);
        accessor.setPropertyValue("stringValue", "test");
        accessor.setPropertyValue("longValue", "1");
        accessor.setPropertyValue("intValue", "2");
        accessor.setPropertyValue("nestedBean", new ChildBean());
        accessor.setPropertyValue("nestedBean.stringValue", "nested test");
        accessor.setPropertyValue("nestedBean.longValue", "10");
        accessor.setPropertyValue("nestedBean.intValue", "20");
        accessor.setPropertyValue("nestedBeans", Arrays.asList(new ChildBean()));
        accessor.setPropertyValue("nestedBeans[0].stringValue", "list test");
        accessor.setPropertyValue("nestedBeans[0].longValue", "100");
        accessor.setPropertyValue("nestedBeans[0].intValue", "200");
        accessor.setPropertyValue("nestedBeanMap", Collections.singletonMap("key1", new ChildBean()));
        accessor.setPropertyValue("nestedBeanMap[key1].stringValue", "map test");
        accessor.setPropertyValue("nestedBeanMap[key1].longValue", "1000");
        accessor.setPropertyValue("nestedBeanMap[key1].intValue", "2000");
        accessor.setPropertyValue("nestedBeanArray", new ChildBean[]{new ChildBean()});
        accessor.setPropertyValue("nestedBeanArray[0].stringValue", "array test");
        accessor.setPropertyValue("nestedBeanArray[0].longValue", "10000");
        accessor.setPropertyValue("nestedBeanArray[0].intValue", "20000");

        assertThat(rootBean.stringValue, is("test"));
        assertThat(accessor.getPropertyValue("stringValue"), is("test"));
        assertThat(rootBean.longValue, is(1L));
        assertThat(accessor.getPropertyValue("longValue"), is(1L));
        assertThat(rootBean.intValue, is(2));
        assertThat(accessor.getPropertyValue("intValue"), is(2));

        assertThat(rootBean.nestedBean.stringValue, is("nested test"));
        assertThat(accessor.getPropertyValue("nestedBean.stringValue"), is("nested test"));
        assertThat(rootBean.nestedBean.longValue, is(10L));
        assertThat(accessor.getPropertyValue("nestedBean.longValue"), is(10L));
        assertThat(rootBean.nestedBean.intValue, is(20));
        assertThat(accessor.getPropertyValue("nestedBean.intValue"), is(20));

        assertThat(rootBean.nestedBeans.get(0).stringValue, is("list test"));
        assertThat(accessor.getPropertyValue("nestedBeans[0].stringValue"), is("list test"));
        assertThat(rootBean.nestedBeans.get(0).longValue, is(100L));
        assertThat(accessor.getPropertyValue("nestedBeans[0].longValue"), is(100L));
        assertThat(rootBean.nestedBeans.get(0).intValue, is(200));
        assertThat(accessor.getPropertyValue("nestedBeans[0].intValue"), is(200));

        assertThat(rootBean.nestedBeanMap.get("key1").stringValue, is("map test"));
        assertThat(accessor.getPropertyValue("nestedBeanMap[key1].stringValue"), is("map test"));
        assertThat(rootBean.nestedBeanMap.get("key1").longValue, is(1000L));
        assertThat(accessor.getPropertyValue("nestedBeanMap[key1].longValue"), is(1000L));
        assertThat(rootBean.nestedBeanMap.get("key1").intValue, is(2000));
        assertThat(accessor.getPropertyValue("nestedBeanMap[key1].intValue"), is(2000));

        assertThat(rootBean.nestedBeanArray[0].stringValue, is("array test"));
        assertThat(accessor.getPropertyValue("nestedBeanArray[0].stringValue"), is("array test"));
        assertThat(rootBean.nestedBeanArray[0].longValue, is(10000L));
        assertThat(accessor.getPropertyValue("nestedBeanArray[0].longValue"), is(10000L));
        assertThat(rootBean.nestedBeanArray[0].intValue, is(20000));
        assertThat(accessor.getPropertyValue("nestedBeanArray[0].intValue"), is(20000));

    }

    @Test
    public void testDirectFieldAccessorGrowIsTrue() {
        RootBean rootBean = new RootBean();
        DirectFieldAccessor accessor = new DirectFieldAccessor(rootBean);
        accessor.setAutoGrowNestedPaths(true);
        accessor.setPropertyValue("stringValue", "test");
        accessor.setPropertyValue("longValue", "1");
        accessor.setPropertyValue("intValue", "2");
        accessor.setPropertyValue("nestedBean.stringValue", "nested test");
        accessor.setPropertyValue("nestedBean.longValue", "10");
        accessor.setPropertyValue("nestedBean.intValue", "20");
        accessor.setPropertyValue("nestedBeans[0].stringValue", "list test");
        accessor.setPropertyValue("nestedBeans[0].longValue", "100");
        accessor.setPropertyValue("nestedBeans[0].intValue", "200");
        accessor.setPropertyValue("nestedBeanMap[key1].stringValue", "map test");
        accessor.setPropertyValue("nestedBeanMap[key1].longValue", "1000");
        accessor.setPropertyValue("nestedBeanMap[key1].intValue", "2000");
        accessor.setPropertyValue("nestedBeanArray[0].stringValue", "array test");
        accessor.setPropertyValue("nestedBeanArray[0].longValue", "10000");
        accessor.setPropertyValue("nestedBeanArray[0].intValue", "20000");

        assertThat(rootBean.stringValue, is("test"));
        assertThat(accessor.getPropertyValue("stringValue"), is("test"));
        assertThat(rootBean.longValue, is(1L));
        assertThat(accessor.getPropertyValue("longValue"), is(1L));
        assertThat(rootBean.intValue, is(2));
        assertThat(accessor.getPropertyValue("intValue"), is(2));

        assertThat(rootBean.nestedBean.stringValue, is("nested test"));
        assertThat(accessor.getPropertyValue("nestedBean.stringValue"), is("nested test"));
        assertThat(rootBean.nestedBean.longValue, is(10L));
        assertThat(accessor.getPropertyValue("nestedBean.longValue"), is(10L));
        assertThat(rootBean.nestedBean.intValue, is(20));
        assertThat(accessor.getPropertyValue("nestedBean.intValue"), is(20));

        assertThat(rootBean.nestedBeans.get(0).stringValue, is("list test"));
        assertThat(accessor.getPropertyValue("nestedBeans[0].stringValue"), is("list test"));
        assertThat(rootBean.nestedBeans.get(0).longValue, is(100L));
        assertThat(accessor.getPropertyValue("nestedBeans[0].longValue"), is(100L));
        assertThat(rootBean.nestedBeans.get(0).intValue, is(200));
        assertThat(accessor.getPropertyValue("nestedBeans[0].intValue"), is(200));

        assertThat(rootBean.nestedBeanMap.get("key1").stringValue, is("map test"));
        assertThat(accessor.getPropertyValue("nestedBeanMap[key1].stringValue"), is("map test"));
        assertThat(rootBean.nestedBeanMap.get("key1").longValue, is(1000L));
        assertThat(accessor.getPropertyValue("nestedBeanMap[key1].longValue"), is(1000L));
        assertThat(rootBean.nestedBeanMap.get("key1").intValue, is(2000));
        assertThat(accessor.getPropertyValue("nestedBeanMap[key1].intValue"), is(2000));

        assertThat(rootBean.nestedBeanArray[0].stringValue, is("array test"));
        assertThat(accessor.getPropertyValue("nestedBeanArray[0].stringValue"), is("array test"));
        assertThat(rootBean.nestedBeanArray[0].longValue, is(10000L));
        assertThat(accessor.getPropertyValue("nestedBeanArray[0].longValue"), is(10000L));
        assertThat(rootBean.nestedBeanArray[0].intValue, is(20000));
        assertThat(accessor.getPropertyValue("nestedBeanArray[0].intValue"), is(20000));

    }

    public static class RootBean {
        private String stringValue;
        private Long longValue;
        private int intValue;
        private ChildBean nestedBean;
        private List<ChildBean> nestedBeans;
        private Map<String, ChildBean> nestedBeanMap;
        private ChildBean[] nestedBeanArray;
    }

    public static class ChildBean {
        private String stringValue;
        private Long longValue;
        private int intValue;

    }

}
