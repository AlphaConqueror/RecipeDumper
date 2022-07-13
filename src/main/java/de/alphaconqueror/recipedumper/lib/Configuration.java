package de.alphaconqueror.recipedumper.lib;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Configuration {
    
    public static File createDumpFile() {
        return createFile("recipes.log");
    }
    
    public static File createIDFile() {
        File file = createFile("items.log");

        assert file != null;
        System.out.println("Created file at '" + file.getAbsolutePath() + "'.");

        return file;
    }
    
    public static File createOreFile() {
        return createFile("oredict.log");
    }

    public static File createIdMapFile() {
        return createFile("idmap.log");
    }
    
    public static File createFluidFile(){
    	return createFile("fluidreg.log");
    }

    
    private static File createFile(String filename)
    {
        try{
            /*File f = new File("dumps" + File.separator + "vsdump-"
                    + getDateString() + "-" + filename);*/
            File f = new File(filename);
            if(f.exists()) f.delete();
            f.createNewFile();
            LogHelper.debug(String.format("File %s created", filename));
            return f;
        }catch(Exception ex)
        {
            LogHelper.severe(String.format("Exception during %s file creation", filename));
            ex.printStackTrace();
        }
        return null;        
    }

    private static String getDateString() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss");
        return sdf.format(date);
    }
    
}
