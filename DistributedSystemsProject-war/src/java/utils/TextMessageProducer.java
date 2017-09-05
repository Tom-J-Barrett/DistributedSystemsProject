package utils;

import Controllers.viewControllers.CartController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

/**
 *
 * @author Robbie
 * 
 * Handles production of messages for the message driven bean.
 */
public class TextMessageProducer {
    
    public static void writeMessage(Session session, MessageProducer messageProducer, String message) {
        try {
            TextMessage loggingMessage = session.createTextMessage();
            loggingMessage.setText(message);
            messageProducer.send(loggingMessage);
        } catch (JMSException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
