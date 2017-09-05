/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageDrivenBeans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Robbie
 * 
 * This Message Driven Bean handles logging TextMessage events to the standard system log file. 
 * 
 * It takes the text from the JMS TextMessage and logs it straight to the file.
 */
@JMSDestinationDefinition(name = "java:app/LoggingQueue", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "LoggingQueue")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/LoggingQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class LoggingBean implements MessageListener {
    
    private final static Logger LOGGER = Logger.getLogger(LoggingBean.class.getName());;
    
    @Resource
    private MessageDrivenContext mdc;
    
    public LoggingBean() {
     
        LOGGER.setLevel(Level.CONFIG);
    
    }
    
    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
             if(message instanceof TextMessage){
                msg = (TextMessage) message;
                LOGGER.info(msg.getText());
             } else {
                LOGGER.log(Level.WARNING, "Message of wrong type: {0}", message.getClass().getName());
        }
        } catch (JMSException ex) {
            Logger.getLogger(LoggingBean.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}
