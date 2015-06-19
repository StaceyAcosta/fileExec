package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JOptionPane;

public class KVConfigReader {

	private String TITLE_KEY = "APP_TITLE";
	
	private String configName;
	private String appWindowName = "CSG International";
	
	public String getAppWindowName() {
		return appWindowName;
	}


	private TreeMap<String, String> configMap = new TreeMap<String, String>();
	
	public KVConfigReader(String configName) {
		super();
		this.configName = configName;
		
	}
	
	public boolean configMapValid(boolean removeInvalid, boolean visuallyWarn){
		boolean retVal = true;
		
		Vector<String> toRemove = new Vector<String>();
		
		Set<Entry<String, String>> entrySet = configMap.entrySet();
		for(Entry<String, String> e: entrySet){
			
			
			
			File f = new File(e.getValue());
			
			if(!f.isFile()){
				toRemove.add(e.getKey());
			}
		}
		
		if(visuallyWarn && toRemove.size() > 0){
			String cr = System.getProperty("line.separator");
			StringBuffer sb = new StringBuffer();
			
			String invalidNotice = "The following are invalid config file entries:" + cr;
			String removeNotice = "Removing invalid entries.";
			if (!removeInvalid)
				removeNotice = "";
			
			
			for(String s : toRemove){
				sb.append("    ");
				sb.append(s);
				sb.append(cr);
			}
			JOptionPane.showMessageDialog(null, invalidNotice + sb.toString() + removeNotice, "I/O Error", JOptionPane.ERROR_MESSAGE);
		}

		
		if (removeInvalid){
			if(toRemove.size() > 0){
				for(String s : toRemove){
					configMap.remove(s);
				}
				
				retVal = false;
			}
		}
		
		
		return retVal;
	}

	public boolean loadConfigMap(){
		
		try {
			FileReader fr = new FileReader(configName);
			BufferedReader br = new BufferedReader(fr);
			String[] tokens;
			String line = br.readLine();
			while (line != null){
				if (line.startsWith("#")){
					line = br.readLine();
					continue;
				}
				
				tokens = line.split("=");
				if (tokens.length == 2){
					System.out.println(tokens[0]);
					if(tokens[0].equals( TITLE_KEY)){
						appWindowName = tokens[1];
						line = br.readLine();
						continue;
					}
					
					configMap.put(tokens[0], tokens[1]);
				}
				line = br.readLine();
			}
			br.close();
			fr.close();
				
		} catch (FileNotFoundException e) {

			JOptionPane.showMessageDialog(null,
				    "Config file '" + configName + "' could not be found.  Check the file path in the jar manifest.",
				    "File I/O error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Config file '" + configName + "' could not be read.  Check the entries in the file.",
				    "File I/O error",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}


	public TreeMap<String, String> getConfigMap() {
		return configMap;
	}
	
}
