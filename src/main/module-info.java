module bex {


    requires io.swagger.v3.oas.annotations;
    requires lombok;
    requires java.logging;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    requires spring.webmvc;
    requires spring.boot.starter.web;
    requires spring.boot.starter.validation;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.boot.starter.logging;
    requires logback.classic;
    requires org.slf4j;
    requires java.base;
    requires java.annotation;
    requires java.compiler;
    requires java.validation;
    exports br.com.bexs;
    exports br.com.bexs.util;
    exports br.com.bexs.vo;
    exports br.com.bexs.controller;
    exports br.com.bexs.model;
    exports br.com.bexs.process;
    exports br.com.bexs.exception;


}