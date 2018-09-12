//package hw4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class SimpleCalendar {
	static JPanel monthView = new JPanel();
	static JTextArea theDate = new JTextArea();
	static JTextArea theDateEvents = new JTextArea();
	static Model m = new Model();
	static File myFile;
	static GregorianCalendar currentCal = new GregorianCalendar();
	
	public static void main (String args[]) throws IOException{
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		JPanel dayView = new JPanel();

		//load events in calendar
		Boolean loading= true;
		String filename = "inputEventsTxt.txt";
		String workingDirectory = System.getProperty("user.dir");
		File myFile = new File(workingDirectory, filename);
		FileReader fr = new FileReader(myFile);
		BufferedReader br = new BufferedReader(fr);
		String name = br.readLine();
		while(loading == true){
			int year = Integer.valueOf(br.readLine());
			int month = Integer.valueOf(br.readLine())-1;
			int day = Integer.valueOf(br.readLine());;
			int startHour = Integer.valueOf(br.readLine());
			int startMin = Integer.valueOf(br.readLine());
			int endHour = Integer.valueOf(br.readLine());
			int endMin = Integer.valueOf(br.readLine());
			GregorianCalendar cal1 = new GregorianCalendar(year, month, day, startHour, startMin);
			GregorianCalendar cal2 = new GregorianCalendar(year, month, day, endHour, endMin);
			Event e = new Event(name, cal1, cal2);
			m.getCalendar().add(e);
			String next = br.readLine();
			if(next.equals("Q")){
				loading = false;
			}
			name = next;
		}
		br.close();
		fr.close();

		theDate.setText(m.getCalendar().printDay(currentCal));
		theDateEvents.append("Events: \n");
		theDateEvents.append(m.getCalendar().getSelectedEvent(currentCal));
		JPanel arrows = new JPanel();

		// make month view
		monthView = m.getCalendar().printCalendar(currentCal);
		ArrayList<JButton> b = m.getCalendar().getButtons();
		for (JButton button : b){
			button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton source = (JButton) e.getSource();
					int newDay = Integer.valueOf(source.getText());
					GregorianCalendar newCal = new GregorianCalendar(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), newDay);
					currentCal = newCal;
					m.notifyModel(newCal);
					theDate.setText(m.getCalendar().printDay(newCal));
					theDateEvents.setText("Events: \n");
					theDateEvents.append(m.getCalendar().getSelectedEvent(newCal));
				}
			});
		}
		//attach listeners: day buttons, and arrow button
		JButton arrow1 = new JButton("<");
		arrow1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int newDay = currentCal.get(Calendar.DAY_OF_MONTH) - 1;
				GregorianCalendar newCal = new GregorianCalendar(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH) , newDay);
				currentCal = newCal;
				theDate.setText(m.getCalendar().printDay(newCal));
				theDateEvents.setText("Events: \n");
				theDateEvents.append(m.getCalendar().getSelectedEvent(newCal));
				m.getCalendar().printCalendar(newCal);
				m.notifyModel(newCal);
				m.getCalendar().printCalendar(newCal);
				JPanel p = m.getCalendar().getMonthPanel();
				frame.remove(monthView);
				monthView = p;
				frame.add(monthView, BorderLayout.CENTER);
				ArrayList<JButton> b = m.getCalendar().getButtons();
				for (JButton button : b){
					button.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							JButton source = (JButton) e.getSource();
							int newDay = Integer.valueOf(source.getText());
							GregorianCalendar newCal = new GregorianCalendar(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), newDay);
							currentCal = newCal;
							m.notifyModel(newCal);
							theDate.setText(m.getCalendar().printDay(newCal));
							theDateEvents.setText("Events: \n");
							theDateEvents.append(m.getCalendar().getSelectedEvent(newCal));
							frame.remove(monthView);
							monthView = p;
							frame.add(monthView, BorderLayout.CENTER);
						}
					});
				}
			}
		});
		JButton arrow2 = new JButton(">");
		arrow2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int newDay = currentCal.get(Calendar.DAY_OF_MONTH) + 1;
				GregorianCalendar newCal = new GregorianCalendar(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH) , newDay);
				currentCal = newCal;
				theDate.setText(m.getCalendar().printDay(newCal));
				theDateEvents.setText("Events: \n");
				theDateEvents.append(m.getCalendar().getSelectedEvent(newCal));
				m.getCalendar().printCalendar(newCal);
				m.notifyModel(newCal);
				m.getCalendar().printCalendar(newCal);
				JPanel p = m.getCalendar().getMonthPanel();
				frame.remove(monthView);
				monthView = p;
				frame.add(monthView, BorderLayout.CENTER);
				currentCal = newCal;
				ArrayList<JButton> b = m.getCalendar().getButtons();
				for (JButton button : b){
					button.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							JButton source = (JButton) e.getSource();
							int newDay = Integer.valueOf(source.getText());
							GregorianCalendar newCal = new GregorianCalendar(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), newDay);
							currentCal = newCal;
							m.notifyModel(newCal);
							theDate.setText(m.getCalendar().printDay(newCal));
							theDateEvents.setText("Events: \n");
							theDateEvents.append(m.getCalendar().getSelectedEvent(newCal));
							frame.remove(monthView);
							monthView = p;
							frame.add(monthView, BorderLayout.CENTER);
						}
					});
				}
			}
		});
		arrows.add(arrow1);
		arrows.add(arrow2);
		dayView.setLayout(new BorderLayout());
		dayView.add(arrows, BorderLayout.NORTH);
		dayView.add(theDate, BorderLayout.CENTER);
		dayView.add(theDateEvents, BorderLayout.SOUTH);
		//listener

		JPanel bottomButtons= new JPanel();
		//Create button
		JButton create = new JButton("Create");
		//listener
		create.addActionListener(new ActionListener(){
			JFrame creation = new JFrame();
			@Override
			public void actionPerformed(ActionEvent e) {
				creation.setLayout(new BorderLayout());
				JTextField eventInput = new JTextField();
				JPanel timeDayInput = new JPanel();
				JTextArea date = new JTextArea();
				date.setText(m.getCalendar().printDay(currentCal));
				JTextField starthour = new JTextField();
				starthour.setColumns(2);
				JTextArea colon = new JTextArea(":");
				JTextArea colon2 = new JTextArea(":");
				JTextField startmin = new JTextField();
				startmin.setColumns(2);
				JTextField endhour = new JTextField();
				endhour.setColumns(2);
				JTextField endmin = new JTextField();
				endmin.setColumns(2);
				JTextArea to = new JTextArea("to");
				timeDayInput.add(date);
				timeDayInput.add(starthour);
				timeDayInput.add(colon);
				timeDayInput.add(startmin);
				timeDayInput.add(to);
				timeDayInput.add(endhour);
				timeDayInput.add(colon2);
				timeDayInput.add(endmin);
				JButton save = new JButton("Save");
				save.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						GregorianCalendar c1 = new GregorianCalendar();
						c1.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
						c1.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
						c1.set(Calendar.HOUR, Integer.valueOf(starthour.getText()));
						c1.set(Calendar.MINUTE, Integer.valueOf(startmin.getText()));
						GregorianCalendar c2 = new GregorianCalendar();
						c2.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
						c2.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
						c2.set(Calendar.HOUR, Integer.valueOf(endhour.getText()) );
						c2.set(Calendar.MINUTE,Integer.valueOf(endmin.getText()) );
						Event ev = new Event (eventInput.getText(), c1, c2);
						String result = m.getCalendar().add(ev);
						m.notifyModel(c1);
						JTextArea resultMessage = new JTextArea(result);
						JFrame notification = new JFrame();
						notification.setSize(100, 100);
						notification.add(resultMessage);
						notification.setVisible(true);
						notification.pack();
						notification.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						creation.remove(eventInput);
						creation.remove(timeDayInput);
						creation.remove(save);
						creation.setVisible(false);
						theDateEvents.setText("");
						theDateEvents.append(m.getCalendar().getSelectedEvent(currentCal));
					}
				});
				creation.add(eventInput, BorderLayout.NORTH);
				creation.add(timeDayInput, BorderLayout.CENTER);
				creation.add(save, BorderLayout.SOUTH);
				creation.setVisible(true);
				creation.pack();
				creation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		});

		bottomButtons.add(create);

		//quit button
		JButton quit = new JButton("Quit");
		//listener
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String content = m.getCalendar().getAllEvents();
				String filename = "events.txt";
				String workingDirectory = System.getProperty("user.dir");
				File results = new File(workingDirectory, filename);
				FileWriter fw;
				try {
					fw = new FileWriter(results.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(content);
					bw.close();
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		bottomButtons.add(quit);
		frame.setResizable(false);
		frame.add(bottomButtons, BorderLayout.NORTH);
		frame.add(monthView, BorderLayout.CENTER);
		frame.add(dayView, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
