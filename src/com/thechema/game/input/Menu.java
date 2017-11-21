package com.thechema.game.input;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import com.thechema.game.Main;

@SuppressWarnings("serial")
public class Menu extends JFrame{
	private JPanel menu;
	private JButton start, exit;
	
	boolean IsRunning = false;
	
	public Menu() throws IOException{
	final BufferedImage image = ImageIO.read(new File("C:\\Users\\szilv\\Downloads\\Computer Graphic programming\\2DAirplane2.0\\2DAirplane2.0\\res\\panel.jpg"));
	setTitle("Flappy Airplane - UlTIMATE EDITION"); 
	setSize(400,260);
	setLocationRelativeTo(null);
	menu = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
	}; 
	menu.setLayout(null);
	this.getContentPane().add( menu);
	start = new JButton();
	start.setText("Start");
	Image img = ImageIO.read(new File("C:\\Users\\szilv\\Downloads\\Computer Graphic programming\\2DAirplane2.0\\2DAirplane2.0\\res\\start.jpg"));
    start.setIcon(new ImageIcon(img));
	menu.add(start);
	start.setBounds(150, 157, 100, 25);
	start.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(IsRunning == false){
				new Main().start();
				IsRunning = true;
			}
		}
	});
	
	exit = new JButton();
	exit.setText("Exit");
	Image img1 = ImageIO.read(new File("C:\\Users\\szilv\\Downloads\\Computer Graphic programming\\2DAirplane2.0\\2DAirplane2.0\\res\\exit.jpg"));
    exit.setIcon(new ImageIcon(img1));
	menu.add(exit);
	exit.setBounds(150, 190, 100, 25);
	exit.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
			dispose();
			
		}
	});
	}
	public void setIsRunning(boolean isRunning) {
		IsRunning = isRunning;
	}
	
	
}