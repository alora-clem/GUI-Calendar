//package hw4;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class Model {
	private MyCalendar cal;
	private ArrayList<ChangeListener> list;

	public Model(){
		cal = new MyCalendar();
		list = new ArrayList<>();
	}

	public void notifyModel(GregorianCalendar currentCal){
		cal.setDay(currentCal);
		ChangeEvent e = new ChangeEvent(this);
		JPanel p = cal.printCalendar(currentCal);
		cal.setMonthPanel(p);
		cal.printDay(currentCal);
		for(ChangeListener l : list){
			l.stateChanged(e);
		}
	}

	public MyCalendar getCalendar(){
		return cal;
	}

	public void addListener(ChangeListener l){
		list.add(l);
	}
}
