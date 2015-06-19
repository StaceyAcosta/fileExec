package viewer;

import java.awt.Dimension;
import java.util.TreeMap;

import javax.swing.JFrame;

import tools.KVConfigReader;

public class Demo2Viewer {
	

	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
	 * @param hashMap 
     */
    private static void createAndShowGUI(String windowTitle, TreeMap<String, String> hashMap) {
        //Create and set up the window.
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        frame.setMinimumSize(new Dimension(300, 50));
        frame.setPreferredSize(new Dimension(300, 400));
        
        DemoPanel panel = new DemoPanel(hashMap);
		frame.getContentPane().add(panel);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
    	
    	if (args.length != 1){
    		System.out.println("Usage: java Demo2Viewer <pathAndNameOfConfigFile>");
    		return;
    	}
    	
    	final KVConfigReader kvc = new KVConfigReader(args[0]);
    	if (kvc.loadConfigMap() == false){
    		return;
    	}
    	kvc.configMapValid(true, true);
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(kvc.getAppWindowName(),kvc.getConfigMap());
            }
        });
    }

}
