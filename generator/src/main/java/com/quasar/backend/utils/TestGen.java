package com.quasar.backend.utils;

import com.quasar.backend.entity.ColumnEntity;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Logan
 * @Date: 2018/8/24 7:59 AM
 */
public class TestGen {
    public static void main(String[] args) {
        test2();
    }

    public static void test2() {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        Template t = ve.getTemplate("template/list.vue2.vm");
        VelocityContext ctx = new VelocityContext();

       ColumnEntity pk =   new ColumnEntity();
       pk.setAttrname("id");
       pk.setAttrName("id");
       pk.setColumnName("ID");

        ctx.put("tableName", "Product");
        ctx.put("comments", "Product");
        ctx.put("pk", pk);
        ctx.put("className", "Product");
        ctx.put("classname", "product");
        ctx.put("pathName", "product");
        ctx.put("package","goods");
        ctx.put("moduleName", "base");


        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);

        System.out.println(sw.toString());
    }

    public static void test4(){
        Velocity.init();

        VelocityContext ctx = new VelocityContext() ;

        ColumnEntity pk =   new ColumnEntity();
        pk.setAttrname("id");
        pk.setAttrName("id");
        pk.setColumnName("ID");

        ctx.put("tableName", "Product");
        ctx.put("pk", pk);
        ctx.put("className", "Product");
        ctx.put("classname", "product");
        ctx.put("pathName", "product");
        ctx.put("package","goods");
        ctx.put("moduleName", "base");

        StringWriter out = new StringWriter();
        Velocity.evaluate(ctx , out ,"" ,"this.#[[$router]]#.push({path: '/${moduleName}/${pathName}/form',query:{${pk.attrname} : ${classname}.${pk.attrname}}})") ;
        System.out.println( out.toString());

    }

    public static void test3(){
        Velocity.init();

        VelocityContext velocityContext = new VelocityContext() ;
        velocityContext.put("name","bb8");

        StringWriter out = new StringWriter();
        Velocity.evaluate(velocityContext , out ,"" ,"name ===> $name") ;
        System.out.println( out.toString());

        out = new StringWriter();
        Velocity.evaluate(velocityContext , out ,"" ,"name ===>#[[ $name ]]#") ;
        System.out.println( out.toString());
    }

    public static void test1() {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        Template t = ve.getTemplate("template/hello.vm");
        VelocityContext ctx = new VelocityContext();

        ctx.put("name", "velocity");
        ctx.put("date", (new Date()).toString());

        List temp = new ArrayList();
        temp.add("1");
        temp.add("2");
        ctx.put("list", temp);

        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);

        System.out.println(sw.toString());
    }
}
