/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import javax.swing.Icon;
import javax.swing.JProgressBar;

/**
 *
 * @author mrtru
 */
public class Progress extends JProgressBar{
    private ProgressType progresstype=ProgressType.NONE ;

    public ProgressType getProgresstype() {
        return progresstype;
    }

    public void setProgresstype(ProgressType progresstype) {
        this.progresstype = progresstype;
        repaint();
    }
    public Progress(){
        setOpaque(false);
        setUI(new ProgressCircleUI(this));
    }
    
    public static enum ProgressType{
        DOWN_FILE,CANCEL,FILE,NONE
    }
    
}
