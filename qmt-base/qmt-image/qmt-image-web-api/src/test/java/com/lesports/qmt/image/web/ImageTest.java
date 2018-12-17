package com.lesports.qmt.image.web;

import org.junit.Test;

/**
 * Created by denghui on 2017/3/8.
 */
public class ImageTest {

    @Test
    public void testPrefix() {
        String prefix = "10.185.30.236=/image2,10.185.30.230=/image";
        for (String pair : prefix.split(",")) {
            String[] arr = pair.split("=");
            System.out.println(arr[0]);
            System.out.println(arr[1]);
        }
    }
}
