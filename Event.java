//package hw4;
/**
 * hw2
 * @author Alora Clem
 * version 1
 */


import java.util.Calendar;
import java.util.GregorianCalendar;

public class Event implements MonthsAndDays{
	private String name;
	private GregorianCalendar date;
	private GregorianCalendar startingTime;
	private GregorianCalendar endingTime;
	/**
	 * To create an event
	 * @param String s to supply the name of the event
	 * @param GregorianCalendar c1 supplied to fill in the date, and starting time of event
	 * @param GregorianCalendar c2 supplied to fill in the ending time
	 * PostCondition: a event will be created
	 */
	public Event(String s, GregorianCalendar c1, GregorianCalendar c2){
		this.name = s;
		this.date = c1;
		this.startingTime = c1;
		this.endingTime = c2;
		this.date.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));
		this.date.set(Calendar.MONTH, c1.get(Calendar.MONTH));
		this.startingTime.set(Calendar.HOUR, c1.get(Calendar.HOUR));
		this.startingTime.set(Calendar.MINUTE, c1.get(Calendar.MINUTE));
		this.endingTime.set(Calendar.HOUR, c2.get(Calendar.HOUR));
		this.endingTime.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));
	}
	/**
	 * To check if a given event conflicts the other event based on day and time
	 * @param Event e1 the event to check
	 * @return boolean true if they conflict, false if they don't
	 * PreCondition: has to be called on an event with another event as a param
	 * PostCondition: returns true of false
	 */
	public boolean checkConflict(Event e1){
		if(this.getDay() == e1.getDay()){
			int startH = this.getStartHour();
			int startM = this.getStartMin();
			int endH = this.getEndHour();
			int endM = this.getEndMin();
			if(e1.getStartHour() == startH && e1.getStartMin() >= startM){
				return true;
			}
			else if(e1.getStartHour()> startH && e1.getStartHour() < endH){
				return true;
			}
			else if(e1.getEndHour() == endH && e1.getEndMin() <= endM){
				return true;
			}
		}
		return false;
	}
	/**
	 * to get the name of the event
	 * @return name of event
	 * PostCondition: returns name
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * to get the month of the event
	 * @return month 
	 * PostCondition: returns month
	 */
	public int getMonth(){
		return this.date.get(Calendar.MONTH);
	}
	/**
	 * to get the year of the event
	 * @return year 
	 * PostCondition: returns year
	 */
	public int getYear(){
		return this.date.get(Calendar.YEAR);
	}
	/**
	 * to get the date of the event in the form of a gregorianCalendar
	 * @return date 
	 * PostCondition: returns date
	 */
	public GregorianCalendar getDate(){
		return this.date;
	}
	/**
	 * to get the day of the month of the event
	 * @return day 
	 * PostCondition: returns day
	 */
	public int getDay(){
		return this.date.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * to get the starting hour of the event
	 * @return starting hour 
	 * PostCondition: returns starting hour
	 */
	public int getStartHour(){
		return this.startingTime.get(Calendar.HOUR);
	}
	/**
	 * to get the starting minute of the event
	 * @return starting minute 
	 * PostCondition: returns starting minute
	 */
	public int getStartMin(){
		return this.startingTime.get(Calendar.MINUTE);
	}
	/**
	 * to get the ending hour of the event
	 * @return ending hour 
	 * PostCondition: returns ending hour
	 */
	public int getEndHour(){
		return this.endingTime.get(Calendar.HOUR);
	}
	/**
	 * to get the ending minute of the event
	 * @return ending minute 
	 * PostCondition: returns ending minute
	 */
	public int getEndMin(){
		return this.endingTime.get(Calendar.MINUTE);
	}
	/**
	 * to get the day of the week (sat, sun, mon, etc)
	 * @return day of week
	 * Post condition: day of week returned
	 */
	public DAYS getDayOfWeek(){
		DAYS[] arrayOfDays = DAYS.values();	
		return arrayOfDays[date.get(Calendar.DAY_OF_WEEK)-1];
	}
	/**
	 * to print the event
	 * @return string event
	 * PostCondition: event information returned
	 */
	public String printEvent(){
		MONTHS[] arrayOfMonths = MONTHS.values();
		String startmin = String.valueOf(this.getStartMin());
		String endmin = String.valueOf(this.getEndMin());
		if(startmin.length()== 1){
			startmin = "0"+ startmin;
		}
		if(endmin.length() == 1){
			endmin = "0"+ endmin;
		}
		return this.getName()+" "+this.getYear() +" "+ arrayOfMonths[date.get(Calendar.MONTH)]
				+" "+ this.getDay() + " "+ this.getStartHour() + ":"+ startmin +"-"+ this.getEndHour() + ":"+ endmin;
	}
}
