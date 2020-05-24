package com.github.jitwxs.commons.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Jackson工具类
 * @author jitwxs
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonUtils {
    private static final Logger logger = Logger.getLogger(JacksonUtils.class.getName());

    private static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 反序列化忽略不存在项
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 驼峰转换
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    /**
     * 如不需要默认的 Mapper 配置，调用该方法，进行自定义配置
     *
     * @param mapper 序列化对象
     */
    public static void resetObjectMapper(ObjectMapper mapper) {
        MAPPER = mapper;
    }

    /**
     * 对象 转 Json字符串
     *
     * @param data java对象
     * @return json字符串
     */
    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "JsonUtils#objectToJson failed, data: " + data, e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * Json字符串 转 对象
     *
     * @param jsonData json字符串
     * @param clazz java对象类型
     * @param <T> 泛型
     * @return java对象
     */
    public static <T> T jsonToObject(String jsonData, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonData, clazz);
        } catch (Exception e) {
            logger.log(Level.WARNING, "JsonUtils#jsonToObject failed, data: " + jsonData + ", class: " + clazz, e);
        }
        return null;
    }

    /**
     * Json字符串 转 List
     *
     * @param jsonData json字符串
     * @param clazz java对象类型
     * @param <T> 泛型
     * @return 对象List
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> clazz) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            logger.log(Level.WARNING, "JsonUtils#jsonToList failed, data: " + jsonData + ", class: " + clazz, e);
        }
        return Collections.emptyList();
    }

    /**
     * Json字符串 转 Map
     *
     * @param jsonData json字符串
     * @param <T> 泛型
     * @return 对象Map
     */
    public static <T> Map<String, T> jsonToMap(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonData, Map.class);
        } catch (IOException e) {
            logger.log(Level.WARNING, "JsonUtils#jsonToMap failed, data: " + jsonData, e);
        }
        return Collections.emptyMap();
    }

    /**
     * Json字符串 转 Set
     *
     * @param jsonData json字符串
     * @param <T> 泛型
     * @return 对象Set
     */
    public static <T> Set<T> jsonToSet(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonData, Set.class);
        } catch (IOException e) {
            logger.log(Level.WARNING, "JsonUtils#jsonToSet failed, data: " + jsonData, e);
        }
        return Collections.emptySet();
    }
}
