import java.awt.*;

public class ParameterFrame extends Frame {
	
	private static Color backgroundColor = Color.green;
	private static Color labelColor = Color.white;
	private static Color fieldColor = Color.yellow;
	private static int fieldWidth = 4; // Default field width
	private String [] names;
	private TextField [] fields;
	
	public ParameterFrame (String title, int width, int height, String [] names) {
	  this.setTitle(title);
		this.resize(width,height);
		this.setBackground(backgroundColor);
		this.initializeFields(names);
		this.show();
	}
	
	public void initializeFields(String [] names) {
		this.names = names; 
		int size = names.length;
		this.fields = new TextField [size];
		this.setLayout (new GridLayout(size,2));
		try {
			for (int i = 0; i < size; i++) {
				Label label = new Label(names[i]);
				label.setBackground(labelColor);
				this.add(label);
				TextField field = new TextField(fieldWidth);
				field.setBackground(fieldColor);
				this.add(field);
				fields[i] = field;
			}
		} catch (ParameterException e) {
			System.out.println("Error when initializing parameter window fields:\n"
			                    + e.getMessage());
		}
	}
	
	public int getIntParam (String name) {
		return Integer.parseInt(fields[paramIndex(name)].getText());
	}
	
	public double getDoubleParam (String name) {
		return (Double.valueOf(fields[paramIndex(name)].getText())).doubleValue();
	}
	
	public boolean getBooleanParam (String name) {
		return ((fields[paramIndex(name)].getText()).equals("true"));
	}
	
	public String getStringParam (String name) {
		return fields[paramIndex(name)].getText();
	}
	
	public void setIntParam (String name, int value) {
		fields[paramIndex(name)].setText(Integer.toString(value));
	}
	
	public void setBooleanParam (String name, boolean value) {
		fields[paramIndex(name)].setText(value ? "true" : "false");
	}
	
	public void setDoubleParam (String name, double value) {
		fields[paramIndex(name)].setText(Double.toString(value));
	}

	public void setStringParam (String name, String value) {
		fields[paramIndex(name)].setText(value);
	}
	
	protected int paramIndex(String name) {
		for (int i = 0; i < names.length; i++) {
			if (name.equals(names[i])) {
				return i;
			}
		}
		throw new ParameterException("Parameter name \"" + name + "\" not found!");
	}
	
	public boolean handleEvent(Event evt) {
		// Make parameter window go away when click upper left button
		if (evt.id == Event.WINDOW_DESTROY) {
			hide();
			dispose();
		}
		return super.handleEvent(evt);		
	}
	

	
}

class ParameterException extends RuntimeException {
	
	public ParameterException (String msg) {
		super(msg);
	}
	
}
