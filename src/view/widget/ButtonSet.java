package src.view.widget;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import src.view.widget.WidgetFactory.Fonts;

public class ButtonSet extends JPanel {
    ArrayList<JButton> buttons;

    public ButtonSet(String header, String[] labels) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 1;
        WidgetFactory.styleComponent(this);

        JPanel inner = WidgetFactory.createJPanel();
        inner.setLayout(new GridLayout(labels.length + 1, 1));


        inner.add(WidgetFactory.createJLabel(header, Fonts.SUBTITLE));
        this.buttons = new ArrayList<>();
        for (String s : labels) {
            JButton b = WidgetFactory.createJButton(s, Fonts.SUBTITLE);
            this.buttons.add(b);
            inner.add(b);
        }

        this.add(inner, c);
    }

    public void setListener(ActionListener al) {
        for (JButton b : buttons) {
            b.addActionListener(al);
        }
    }

    public void setButtonTexts(String[] buttonTexts) {
        for (int i = 0; i < buttonTexts.length; i++) {
            this.buttons.get(i).setText(buttonTexts[i]);
        }
    }
}
