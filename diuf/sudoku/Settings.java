/*
 * Project: Sudoku Explainer
 * Copyright (C) 2006-2007 Nicolas Juillerat
 * Available under the terms of the Lesser General Public License (LGPL)
 */
package diuf.sudoku;

import java.util.*;
//port java.util.prefs.*;

import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.UIManager;

/**
 * Global settings of the application.
 * Implemented using the singleton pattern.
 */
public class Settings {

    public final static int VERSION = 1;
    public final static int REVISION = 2;
    public final static String SUBREV = ".1";

    private static Settings instance = null;

    private static String jsonFilename = "Sudoku16Explainer.json";

    private boolean isRCNotation = true;
    private boolean isAntialiasing = true;
    private boolean isShowingCandidates = true;
    private boolean isShowingCandidateMasks = true;
    private String  lookAndFeelClassName = null;
    private int iPuzzleFormat = 4;

    private EnumSet<SolvingTechnique> techniques;

    private int LoadError = 0;          // =1 if settings load error, a save is done

    private Settings() {
        init();
        load();
    }

    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public void setRCNotation(boolean isRCNotation) {
      if ( this.isRCNotation != isRCNotation ) {
        this.isRCNotation = isRCNotation;
        save();
      }
    }

    public boolean isRCNotation() {
        return isRCNotation;
    }

    public void setAntialiasing(boolean isAntialiasing) {
      if ( this.isAntialiasing != isAntialiasing ) {
        this.isAntialiasing = isAntialiasing;
        save();
      }
    }

    public boolean isAntialiasing() {
        return this.isAntialiasing;
    }

    public void setShowingCandidates(boolean value) {
      if ( this.isShowingCandidates != value ) {
        this.isShowingCandidates = value;
        save();
      }
    }

    public boolean isShowingCandidates() {
        return this.isShowingCandidates;
    }

    public void setShowingCandidateMasks(boolean value) {
      if ( this.isShowingCandidateMasks != value ) {
        this.isShowingCandidateMasks = value;
        save();
      }
    }

    public boolean isShowingCandidateMasks() {
        return this.isShowingCandidateMasks;
    }

    public String getLookAndFeelClassName() {
        return lookAndFeelClassName;
    }

    public void setLookAndFeelClassName(String lookAndFeelClassName) {
      if ( !(this.lookAndFeelClassName.equals(lookAndFeelClassName)) ) {
        this.lookAndFeelClassName = lookAndFeelClassName;
        save();
      }
    }

    public void setPuzzleFormat(int format) {
      if ( this.iPuzzleFormat != format ) {
        this.iPuzzleFormat = format;
        save();
      }
    }

    public int getPuzzleFormat() {
        return iPuzzleFormat;
    }

    public EnumSet<SolvingTechnique> getTechniques() {
        return EnumSet.copyOf(this.techniques);
    }

    public void setTechniques(EnumSet<SolvingTechnique> techniques) {
        this.techniques = techniques;
    }

    public boolean isUsingAllTechniques() {
        EnumSet<SolvingTechnique> all = EnumSet.allOf(SolvingTechnique.class);
        return this.techniques.equals(all);
    }

    public boolean isUsingOneOf(SolvingTechnique... solvingTechniques) {
        for (SolvingTechnique st : solvingTechniques) {
            if (this.techniques.contains(st))
                return true;
        }
        return false;
    }

    public boolean isusingAll(SolvingTechnique... solvingTechniques) {
        for (SolvingTechnique st : solvingTechniques) {
            if (!this.techniques.contains(st))
                return false;
        }
        return true;
    }

    public boolean isUsingAllButMaybeNot(SolvingTechnique... solvingTechniques) {
        List<SolvingTechnique> list = Arrays.asList(solvingTechniques);
        for (SolvingTechnique st : EnumSet.allOf(SolvingTechnique.class)) {
            if (!this.techniques.contains(st) && !list.contains(st))
                return false;
        }
        return true;
    }

//  Load / Save

    private void init() {
        techniques = EnumSet.allOf(SolvingTechnique.class);
        // these are problematic
        techniques.remove(SolvingTechnique.UniqueLoop);
        techniques.remove(SolvingTechnique.AlignedPairExclusion);
        techniques.remove(SolvingTechnique.AlignedTripletExclusion);
        // these are rare
        techniques.remove(SolvingTechnique.NakedSeptuplet);
        techniques.remove(SolvingTechnique.HiddenSeptuplet);
        // these are elusive
        techniques.remove(SolvingTechnique.NakedOctuplet);
        techniques.remove(SolvingTechnique.HiddenOctuplet);
        techniques.remove(SolvingTechnique.LochNessMonster);
    }

    @SuppressWarnings("unchecked")
    public void load() {
        LoadError = 0;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(jsonFilename)) {
            Object obj = jsonParser.parse(reader);
            JSONArray jSettings = (JSONArray)obj;
            jSettings.forEach( Item -> {
                JSONObject stgObject = (JSONObject)Item;
                JSONObject stgDetails = (JSONObject)stgObject.get("Settings");
                String s = "";

                try {
                    s = (String)stgDetails.get("isRCNotation");
                    isRCNotation = s.equals("true")?true:false;
                }
                catch (NullPointerException e) { LoadError = 1; }
                try {
                    s = (String)stgDetails.get("isAntialiasing");
                    isAntialiasing = s.equals("true")?true:false;
                }
                catch (NullPointerException e) { LoadError = 1; }
                try {
                    s = (String)stgDetails.get("isShowingCandidates");
                    isShowingCandidates = s.equals("true")?true:false;
                }
                catch (NullPointerException e) { LoadError = 1; }
                try {
                    s = (String)stgDetails.get("isShowingCandidateMasks");
                    isShowingCandidateMasks = s.equals("true")?true:false;
                }
                catch (NullPointerException e) { LoadError = 1; }

                try {
                    lookAndFeelClassName = (String)stgDetails.get("lookAndFeelClassName");
                }
                catch (NullPointerException e) { LoadError = 1;
                    lookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
                }

                try {
                    s = (String)stgDetails.get("iPuzzleFormat");
                    iPuzzleFormat = s.charAt(0) - '0';
                }
                catch (NullPointerException e) { LoadError = 1; }

            });
            if ( LoadError == 1 ) {
                save();
            }
        } catch (FileNotFoundException e) {
        //  create new json file
            lookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            save();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void save() {
        JSONObject stgDetails = new JSONObject();
        stgDetails.put("isRCNotation", isRCNotation?"true":"false");
        stgDetails.put("isAntialiasing", isAntialiasing?"true":"false");
        stgDetails.put("isShowingCandidates", isShowingCandidates?"true":"false");
        stgDetails.put("isShowingCandidateMasks", isShowingCandidateMasks?"true":"false");
        stgDetails.put("lookAndFeelClassName", lookAndFeelClassName);
        stgDetails.put("iPuzzleFormat", ""+iPuzzleFormat);

        JSONObject stgObject = new JSONObject();
        stgObject.put("Settings", stgDetails);

        JSONArray jSettings = new JSONArray();
        jSettings.add(stgObject);

        try (FileWriter file = new FileWriter(jsonFilename)) {
            file.write(jSettings.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
