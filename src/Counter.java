import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Counter extends JFrame implements ActionListener {
    int seconds =0;
    int minutes =0;
    int hours =0;
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);
    JLabel timeLabel;
    JComboBox<Integer> hComboBox ;
    JComboBox<Integer> mComboBox ;
    JComboBox<Integer> sComboBox ;
    JButton start;
    JButton stop;
    JButton reset;
    JLabel alert;
    Clip clip;
    Counter(){

        //label alert
        alert = new JLabel("READY TO START");
        alert.setFont(new Font("Verdana",Font.PLAIN,35));
        alert.setHorizontalAlignment(JTextField.CENTER);
        alert.setVisible(true);
        //label alert

        //time alert
        timeLabel = new JLabel(hours_string+":"+minutes_string+":"+seconds_string);
        timeLabel.setFont(new Font("Verdana",Font.PLAIN,40));
        timeLabel.setHorizontalAlignment(JTextField.CENTER);
        timeLabel.setVisible(true);
        //time alert

        //comboBox
        Integer[] times_99 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99};
        Integer[] times_59 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};

        hComboBox = new JComboBox<>(times_99);
        mComboBox = new JComboBox<>(times_59);
        sComboBox = new JComboBox<>(times_59);
        hComboBox.setSelectedIndex(0);
        mComboBox.setSelectedIndex(0);
        sComboBox.setSelectedIndex(0);
        hComboBox.setFocusable(false);
        mComboBox.setFocusable(false);
        sComboBox.setFocusable(false);
        hComboBox.setFont(new Font("Verdana",Font.PLAIN,35));
        mComboBox.setFont(new Font("Verdana",Font.PLAIN,35));
        sComboBox.setFont(new Font("Verdana",Font.PLAIN,35));
        hComboBox.addActionListener(this);
        mComboBox.addActionListener(this);
        sComboBox.addActionListener(this);
        //comboBox

        //time panel
        JPanel timePanel = new JPanel();
        timePanel.setVisible(true);
        timePanel.setLayout(new FlowLayout());
        timePanel.add(hComboBox);
        timePanel.add(mComboBox);
        timePanel.add(sComboBox);
        //time panel

        //buttons
        start = new JButton("start");
        stop = new JButton("stop");
        reset = new JButton("reset");
        start.setFocusable(false);
        stop.setFocusable(false);
        reset.setFocusable(false);
        start.setFont(new Font("Verdana",Font.PLAIN,30));
        stop.setFont(new Font("Verdana",Font.PLAIN,30));
        reset.setFont(new Font("Verdana",Font.PLAIN,30));
        start.addActionListener(this);
        stop.addActionListener(this);
        reset.addActionListener(this);
        //buttons
        //time panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setVisible(true);
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(start);
        buttonsPanel.add(stop);
        buttonsPanel.add(reset);
        //time panel

        // frame
        setVisible(true);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,1));
        add(alert);
        add(timeLabel);
        add(timePanel);
        add(buttonsPanel);
        // frame



    }
    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (seconds==0 && minutes==0 && hours==0){
                alert.setText("END OF TIME  (reset timer)");
//              getting the audio
                String audioPath = System.getProperty("user.dir")+"/src/aaa.wav";
                File file = new File(audioPath);
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                }catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                // play an audio
                timer.stop();
            }
            else{
                seconds--;
                if (seconds<0){
                    seconds=59;
                    minutes--;
                    if (minutes<0){
                        minutes=59;
                        hours--;
                    }
                }
            }
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        }
    });

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==hComboBox || e.getSource()==mComboBox || e.getSource()==sComboBox){
            seconds = (int) sComboBox.getSelectedItem();
            minutes = (int) mComboBox.getSelectedItem();
            hours = (int) hComboBox.getSelectedItem();
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        }
        if(e.getSource()==start){
            alert.setText("COUNTDOWN STARTED");
            sComboBox.setEnabled(false);
            mComboBox.setEnabled(false);
            hComboBox.setEnabled(false);
            start();
        }
        if(e.getSource()==stop){
            alert.setText("COUNTDOWN PAUSED");
            stop();
        }
        if(e.getSource()==reset){
            alert.setText("READY TO START");
            sComboBox.setEnabled(true);
            mComboBox.setEnabled(true);
            hComboBox.setEnabled(true);
            reset();
        }
    }
    private void start() {
        timer.start();
    }
    private void stop() {
        timer.stop();
    }
    private void reset() {
        timer.stop();
        seconds =0;
        minutes=0;
        hours=0;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        sComboBox.setSelectedIndex(0);
        mComboBox.setSelectedIndex(0);
        hComboBox.setSelectedIndex(0);
        try{
            clip.stop();
        } catch (Exception ignored){}
    }
}
