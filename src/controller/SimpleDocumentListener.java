package src.controller;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * SimpleDocumentListeners simplify the DocumentListener interface by delegating
 * all three update methods (insertUpdate, removeUpdate, and changedUpdate) to a
 * single update() method.
 */
public abstract class SimpleDocumentListener implements DocumentListener {

    /**
     * This method is called whenever the user inserts, removes, or changes the
     * document being listened to (such as a JTextField, JTextArea, etc).
     * 
     * @param e the {@link DocumentEvent} which occurred.
     */
    public abstract void update(DocumentEvent e);

    @Override
    public void insertUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update(e);
    }
}