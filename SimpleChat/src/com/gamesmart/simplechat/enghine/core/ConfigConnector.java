package com.gamesmart.simplechat.enghine.core;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.gamesmart.simplechat.enghine.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigConnector
{
    private static Logger logger = Logger.getLogger(ConfigConnector.class);

    private static final String MACHINE_CONFIG = "machines/";

    private JSONObject jsonObject;

    private static ConfigConnector configConnector;

    private Map<String, JSONObject> jsonObjectMap;

    public ConfigConnector()
    {
    }

    public static ConfigConnector getInstance()
    {
        if (configConnector == null)
        {
            configConnector = new ConfigConnector();
        }
        return configConnector;
    }

    public Map getJsonObjectMap()
    {
        return jsonObjectMap;
    }


    public void initConfig()
    {
        List<String> contentFolder = readContentFolder();
        init(contentFolder);
    }

    public List<String> readContentFolder()
    {
        File folder = new File(App.CONFIG_DIR + MACHINE_CONFIG);
        boolean exists = folder.exists();
        File[] listOfFiles = folder.listFiles();
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile() && FileUtil.getExtension(listOfFiles[i].getName()).equals("json"))
            {
                list.add(listOfFiles[i].getName());
            }
        }
        return list;
    }

    public void init(List<String> files)
    {
        jsonObjectMap = new HashMap<String, JSONObject>();
        for (int i = 0; i < files.size(); i++)
        {
            JSONObject jsonObj = parseToJson(MACHINE_CONFIG + files.get(i));
            jsonObjectMap.put(files.get(i), jsonObj);
        }

    }


    public JSONObject parseToJson(String filename)
    {
        try
        {
            FileReader reader = new FileReader(App.CONFIG_DIR + filename);
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(reader);
            return jsonObject;
        }
        catch (FileNotFoundException ex)
        {
            logger.error("", ex);
        }
        catch (IOException | org.json.simple.parser.ParseException ex)
        {
            logger.error("", ex);
        }
        return null;
    }
}
