package src.view.widget;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabelledField extends JPanel {
    JTextField field;

    public LabelledField(String text) {
        WidgetFactory.styleComponent(this);
        JLabel label = WidgetFactory.createJLabel(text);
        field = WidgetFactory.createJTextField();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(label);
        this.add(field);
    }
}
