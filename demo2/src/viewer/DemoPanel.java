package viewer;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import tools.GriddedPanel;

@SuppressWarnings("serial")
public class DemoPanel extends GriddedPanel{
	
	private ImageIcon greenIcon = createImageIcon("/images/Ball-go-32.png","script already ran");			
	private ImageIcon redIcon = createImageIcon("/images/Ball-stop-32.png","script has NOT been run");		

	private final TreeMap<String, String> buttonFileMap;
	private Vector<JButton> buttons = new Vector<JButton>();
	private Vector<JLabel> icons = new Vector<JLabel>();
	private Vector<JCheckBox> checkBoxes = new Vector<JCheckBox>();
	
	private boolean isWindows = false;
	
	public DemoPanel(TreeMap<String, String> buttonFileMap) {
		super();
		
		//System.out.println(System.getProperty("os.name"));
		
		String osname = System.getProperty("os.name", "generic").toLowerCase();
		if (osname.startsWith("windows")) {
			isWindows = true;
		}
		
		this.buttonFileMap = buttonFileMap;
		ControlPanel controlPanel = new ControlPanel(checkBoxes);
		ButtonPanel buttonPanel = new ButtonPanel();
		
		this.addComponent(controlPanel, 0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
		this.addComponent(new JScrollPane(buttonPanel), 1, 0, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH);
	}

	

	

	
	public class ClearAction implements ActionListener {

		public ClearAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for(JCheckBox jcb : checkBoxes){
				jcb.setSelected(false);
			}

			for(JButton jb : buttons){
				jb.setIcon(redIcon);
			}

			
		} }

	
	
	class ControlPanel extends GriddedPanel{

		public ControlPanel(Vector<JCheckBox> boxes) {
			super();
			init(boxes);
		}
		
		private void init(Vector<JCheckBox> boxes){
			JButton clearCheckBoxes = new JButton("Clear");
			clearCheckBoxes.addActionListener(new ClearAction());
			this.addComponent(clearCheckBoxes, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
			
		}
	}

	
	
	
	class ButtonPanel extends GriddedPanel{
		public ButtonPanel() {
			super();
			init();
		}

		
		private void init(){
			
			Set<Entry<String, String>> entrySet = buttonFileMap.entrySet();
			
			for (Entry<String, String> entry : entrySet) {
				JButton b = new JButton(entry.getKey(), redIcon);
				b.setHorizontalAlignment(SwingConstants.LEADING);
				b.addActionListener(new ButtonAction(entry.getValue()));
				buttons.add(b);
			}
			
			int row = 0;
			for (JButton jb : buttons){

				JCheckBox jcb = new JCheckBox();
				checkBoxes.add(jcb);

				JLabel jl = new JLabel(redIcon);
				icons.add(jl);
				
				++row;
				
				if(row < buttons.size()){
					//this.addComponent(jcb, row, 0, 1, 1, .4, 0, 	GridBagConstraints.CENTER, GridBagConstraints.NONE);
					//this.addComponent(jl,  row, 1, 1, 1, 			GridBagConstraints.CENTER, GridBagConstraints.NONE);
					//this.addComponent(jb,  row, 2, 1, 1, .6, 0, 	GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
					
					this.addComponent(jb, 	 row, 2, 1, 1, 1, 0, 	GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
				}else{
					//this.addComponent(jcb, row, 0, 1, 1, .4, 0, 	GridBagConstraints.CENTER, GridBagConstraints.NONE);
					//this.addComponent(jl,  row, 1, 1, 1, 			GridBagConstraints.CENTER, GridBagConstraints.NONE);
					//this.addComponent(jb,  row, 2, 1, 1, .6, 0, 	GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
					
					this.addComponent(jb, 	 row, 2, 1, 1, 1, 1, 	GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
				}
				
			}

		}
		
		public class ButtonAction implements ActionListener {
			private String batch = "";
			private String runDir = "";
			private File file;
			private File dir;
			
			public ButtonAction(String batch) {
				super();
				
				//System.out.println("batch: " + batch);
				
				file = new File(batch);
				
				this.batch = batch;
				this.runDir = file.getParent();
				
				
				try {
					//System.out.println("Canno Path:" + file.getCanonicalPath());
					String canopath = file.getCanonicalPath();
					int index = canopath.lastIndexOf(System.getProperty("file.separator"));
					if(index > 0){
						runDir = canopath.substring(0, index);
						//System.out.println("runDir: " + runDir);
						if(this.runDir != null)
							dir = new File(runDir);

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//System.out.println(arg0);
				JButton jb = (JButton) arg0.getSource();
				jb.setIcon(greenIcon);
				
				class PerformProcess extends SwingWorker<Object, Object>{

					@Override
					protected Object doInBackground() throws Exception {
						try {
							//Runtime.getRuntime().exec("cmd /c start " + path);
							
							//we are assuming windows or linux OS flavors.
							ProcessBuilder pb = null;
							if (isWindows) {
								pb = new ProcessBuilder("cmd", "/c", "start", batch);
							}else
								pb = new ProcessBuilder(batch);
							
							pb.directory(dir);
							Process p = pb.start();
							
							
							String output = loadStream(p.getInputStream());
					        String error  = loadStream(p.getErrorStream());
					        //int rc = p.waitFor();

					        if (output.length() > 0)
					        	JOptionPane.showMessageDialog(null, output);
					        
					        if (error.length() > 0)
					        	JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						
						
						return null;
					}
					
				}
				
				(new PerformProcess()).execute();
				
			}
			
		}

	}
	
	private static String loadStream(InputStream s) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(s));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line=br.readLine()) != null)
            sb.append(line).append("\n");
        return sb.toString();
    }
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	
	
}

