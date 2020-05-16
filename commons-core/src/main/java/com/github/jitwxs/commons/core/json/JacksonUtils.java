package com.github.jitwxs.commons.core.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JackJson
 * @author jitwxs
 * @date 2020年05月16日 22:13
 */
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
     */
    public static void resetObjectMapper(ObjectMapper mapper) {
        MAPPER = mapper;
    }

    private JacksonUtils() {
    }

    /**
     * 对象-->Json字符串
     * @author jitwxs
     * @version 创建时间：2018年4月17日 下午3:39:35
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
     * Json字符串-->对象
     * @author jitwxs
     * @version 创建时间：2018年4月17日 下午3:39:45
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
     * Json字符串--> List<对象>
     * @author jitwxs
     * @version 创建时间：2018年4月17日 下午3:40:09
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

    public static <T> Map<String, T> jsonToMap(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonData, Map.class);
        } catch (IOException e) {
            logger.log(Level.WARNING, "JsonUtils#jsonToMap failed, data: " + jsonData, e);
        }
        return Collections.emptyMap();
    }

    public static String mapToJson(Map map) {
        try {
            return MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "JsonUtils#mapToJson failed, map: " + map, e);
        }
        return StringUtils.EMPTY;
    }

    public static <T> Set<T> jsonToSet(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonData, Set.class);
        } catch (IOException e) {
            logger.log(Level.WARNING, "JsonUtils#jsonToSet failed, data: " + jsonData, e);
        }
        return Collections.emptySet();
    }

    public static String setToJson(Set<?> set) {
        try {
            return MAPPER.writeValueAsString(set);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "JsonUtils#setToJson failed, data: " + set, e);
        }
        return StringUtils.EMPTY;
    }
}
