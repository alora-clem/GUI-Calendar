//package hw4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

/**
 * hw2
 * @author Alora Clem
 * version 2
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MyCalendar implements MonthsAndDays
{
	private static GregorianCalendar today;
	private ArrayList<Event> events;
	private ArrayList<JButton>buttons;
	private JPanel panel;

	/**
	 * To make a calendar, with an array list of events
	 * PostCondtion: calendar will be made
	 */
	public MyCalendar(){
		today = new GregorianCalendar();
		events = new ArrayList<Event>();
		buttons = new ArrayList<JButton>();
		panel = new JPanel();
	}
	public void setDay(GregorianCalendar c){
		today = c;
	}
	/**
	 * Prints a month view of a calendar
	 * @param GregorianCalendar c
	 * PostCondition: calendar will be printed
	 */
	public JPanel printCalendar(GregorianCalendar c){
		JPanel p = new JPanel(new BorderLayout());
		JPanel days = new JPanel();
		GridLayout g = new GridLayout();
		g.setRows(7);
		g.setColumns(4);
		days.setLayout(g);
		MONTHS[] arrayOfMonths = MONTHS.values();
		int dayCount = 1;
		JTextArea top = new JTextArea();
		top.append(arrayOfMonths[c.get(Calendar.MONTH)] + " ");
		top.append(Integer.toString(c.get(Calendar.YEAR)));
		top.append("\n");
		top.append("Su                  Mo                  Tu                  We                  Th                  Fr                  Sa");
		top.append("\n");
		p.add(top, BorderLayout.NORTH);
		GregorianCalendar firstDayOfMonth = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		int dayOfWeek= firstDayOfMonth.get(Calendar.DAY_OF_WEEK)-1;
		int daysOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		JPanel week1 = new JPanel();
		JPanel week2 = new JPanel();
		JPanel week3 = new JPanel();
		JPanel week4 = new JPanel();
		JPanel week5 = new JPanel();
		ArrayList<JPanel> weeks = new ArrayList<>();
		weeks.add(week1);
		weeks.add(week2);
		weeks.add(week3);
		weeks.add(week4);
		weeks.add(week5);
		while(dayOfWeek >0){
			JButton blank = new JButton();
			week1.add(blank);
			dayOfWeek --;
		}
		dayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1;
		int week = 0;
		JPanel currentWeek = week1;
		while((7-dayOfWeek) > 0 && dayCount <= daysOfMonth){
			JButton date = new JButton(Integer.toString(dayCount));
			if(dayCount == c.get(Calendar.DAY_OF_MONTH)){
				date.setOpaque(true);
				date.setForeground(Color.RED);
				date.setBackground(Color.RED);
			}
			buttons.add(date);
			currentWeek.add(date);
			dayCount++;
			dayOfWeek ++;
			if(dayOfWeek  % 7 == 0){
				week ++;
				if(week < 5){
					currentWeek = weeks.get(week);
				}
				dayOfWeek = 0;
			}
		}
		while(7-dayOfWeek>0){
			JButton blank = new JButton();
			week5.add(blank);
			dayOfWeek++;
		}
		for (JPanel pan: weeks){
			days.add(pan);
		}
		p.add(days, BorderLayout.CENTER);
		panel = p;
		return p;
	}


	/**
	 * To print a calendar from the day view
	 * @param c
	 * PostCondition: calendar will be printed from day view
	 */
	public String printDay(GregorianCalendar c){
		MONTHS[] arrayOfMonths = MONTHS.values();
		DAYS[] arrayOfDays = DAYS.values();
		return (arrayOfDays[c.get(Calendar.DAY_OF_WEEK)- 1]+ ", " + arrayOfMonths[c.get(Calendar.MONTH)] +" "+ c.get(Calendar.DAY_OF_MONTH) + ", " + c.get(Calendar.YEAR));
	}
	/**
	 * To add an event to the calendar
	 * @param Event e
	 * PostCondition: event will be added
	 */
	public String add(Event e){
		boolean added = false;
		int i = 0;
		Event temp1;
		if(this.events.isEmpty()){
			this.events.add(e);
		}
		else{
			for (int j = 0; j < this.events.size(); j ++){
				temp1 = this.events.get(j);
				if(temp1.checkConflict(e) == true){
					return ("Error, unable to add due a time conflict");
					
				}
			}
			while(i < this.events.size() && added == false){
				temp1 = this.events.get(i);
				int temp1StartHour = temp1.getStartHour();
				int temp1StartMin = temp1.getStartMin();
				if(temp1.getYear() != e.getYear()){
					if(temp1.getYear() > e.getYear() && added == false){
						this.events.add(i, e);
						added = true;
					}
					else if (temp1.getYear() < e.getYear()&& added == false){
						i++;
					}
				}
				else if(temp1.getMonth() != e.getMonth()){

					if(temp1.getMonth() > e.getMonth()&& added == false){
						this.events.add(i, e);
						added = true;
					}
					else if(temp1.getMonth() < e.getMonth()&& added == false){
						i++;
					}
				}
				else if(temp1.getDay()!= e.getDay()){

					if(temp1.getDay() > e.getDay()&& added == false){
						this.events.add(i, e);
						added = true;
					}
					else if(temp1.getDay() < e.getDay()&& added == false){
						i++;
					}

				}
				else if(temp1.getStartHour()!= e.getStartHour()){
					if(temp1StartHour > e.getStartHour() && added == false){
						this.events.add(i, e);
						added = true;
					}
					else if(temp1StartHour < e.getStartHour() && added == false){
						i++;
					}

				}

				else{
					if(temp1StartMin > e.getStartMin() && added == false){
						this.events.add(i, e);
						added = true;
					}

				}
			}
			if(added == false){
				events.add(e);
			}
		}
		return "Added";
	}

	/**
	 * To return the events in a calendar
	 * @return String of all the event in the calendar
	 * PostCondition: string of events will be returned
	 */
	public String getAllEvents(){
		String result = "";
		for(int i = 0; i < events.size(); i ++){
			Event temp = events.get(i);
			result += temp.printEvent() +"\n";
		}
		return result;
	}
	/**
	 * To return a single event
	 * @param GregorianCalendar c
	 * @return string of selected event
	 * PostCondition: string of event will be returned
	 */
	public String getSelectedEvent(GregorianCalendar c){
		String result = "";
		for(int i = 0; i < events.size(); i ++){
			Event temp = events.get(i);
			if(temp.getDay() == c.get(Calendar.DAY_OF_MONTH) && temp.getMonth() == c.get(Calendar.MONTH)){
				result += temp.printEvent() +"\n";
			}
		}
		return result;
	}
	/**
	 * To remove all events on a single day
	 * @param GregorianCalendar c
	 * PostCondition: all events on that given day will be removed
	 */
	public void removeAll(GregorianCalendar c){
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for(int i = 0; i < events.size(); i ++){
			Event temp = events.get(i);
			if(temp.getDay()==(c.get(Calendar.DAY_OF_MONTH))){
				ints.add(i);
			}
		}
		for (int i = 0; i < ints.size(); i++){
			events.remove(ints.get(i)-i);
		}
	}
	/**
	 * To remove a single event given by it's name
	 * @param String eventName the event to remove
	 * PostCondition: the event will be removed
	 */
	public void removeEvent(String eventName){
		for(int i = 0; i < events.size(); i ++){
			String tempName = events.get(i).getName();
			if(tempName.equals(eventName)){
				events.remove(i);
			}
		}
	}
	public ArrayList<JButton> getButtons() {
		return buttons;
	}

	public void setMonthPanel(JPanel p){
		panel = p;
	}
	public JPanel getMonthPanel(){
		return panel;
	}
}