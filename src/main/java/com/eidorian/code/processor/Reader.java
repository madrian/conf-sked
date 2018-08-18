package com.eidorian.code.processor;

import com.eidorian.code.data.Talks;
import com.eidorian.code.data.Talk;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Helper class to read JSON and property files.
 */
public class Reader {

    private Reader() {};

    /**
     * Reads a JSON input file of conference talks.
     *
     * @param filename
     * @return a pool of talks.
     */
    public static List<Talk> readJson(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        Talks talks = null;
        InputStream input = null;
        try {
            if((new File(filename)).exists()) {
                input = new FileInputStream(filename);
            } else {
                input = Reader.class.getClassLoader()
                        .getResourceAsStream(filename);
            }
            talks = mapper.readValue(input, Talks.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return talks.getTalks();
    }

    /**
     * Reads a property file configuration.
     *
     * @param propertyFile
     * @return
     */
    public static Properties readProperties(String propertyFile) {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            if((new File(propertyFile)).exists()) {
                input = new FileInputStream(propertyFile);
            } else {
                input = Reader.class.getClassLoader()
                        .getResourceAsStream(propertyFile);
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
