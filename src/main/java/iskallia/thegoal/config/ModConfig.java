package iskallia.thegoal.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import iskallia.thegoal.TheGoal;

import java.io.*;

public abstract class ModConfig {

    private static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected String root = String.format("config/%s/", TheGoal.MOD_ID);

    public void generateConfig() {
        try {
            File dir = new File(this.root);
            dir.mkdirs();

            File config = new File(this.root + this.getLocation());
            config.createNewFile();

            this.resetConfig();
            this.writeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getLocation();

    public ModConfig readConfig() {
        try {
            return GSON.fromJson(new FileReader(this.root + this.getLocation()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generateConfig();
        }
        return this;
    }

    protected abstract void resetConfig();

    public void writeConfig() throws IOException {
        FileWriter writer = new FileWriter(this.root + this.getLocation());
        GSON.toJson(this, writer);
        writer.flush();
        writer.close();
    }

}
