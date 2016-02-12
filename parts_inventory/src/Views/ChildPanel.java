package Views;
import java.awt.Container;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;


	//ChildPanel : the GUI stuff that will display the inner frames
	//				all it does is update its title when you press the button (wow)
	public class ChildPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private MasterFrame master;//container of inner frame parent
		private String myTitle;
		
		public ChildPanel(MasterFrame m) {
			master = m;
		}
		
		//set the title of the containing JInternalFrame
		private void setInternalFrameTitle() {
			Container parent = this;
			//get climbing parent hierarchy until we find the JInnerFrame
			while(!(parent instanceof JInternalFrame) && parent != null) 
	            parent = parent.getParent();
			if(parent != null)
				((JInternalFrame) parent).setTitle(this.myTitle);
		}
		public void hideInternalFrame()
		{
			Container parent = this;
			
			while(!(parent instanceof JInternalFrame) && parent != null) 
	            parent = parent.getParent();
			if(parent != null)
				parent.setVisible(false);
			
		}
		public String getTitle() {
			return myTitle;
		}
		public void setTitle(String t)
		{
			this.myTitle = t;
			this.setInternalFrameTitle();
		}
		public MasterFrame getMaster()
		{
			return this.master;
		}
	}