package si.fri.prpo.ponudniki.beans;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@ApplicationScoped
public class QueueManager {

    private Logger log = Logger.getLogger(QueueManager.class.getName());

    private Connection connection;
    private Channel channel;

    @PostConstruct
    private void init() {

        ConnectionFactory factory = new ConnectionFactory();

        // konfiguracija factory-ja
        factory.setHost(ConfigurationUtil.getInstance().get("rabbitmq.host").orElse(null));
        factory.setUsername(ConfigurationUtil.getInstance().get("rabbitmq.username").orElse(null));
        factory.setPassword(ConfigurationUtil.getInstance().get("rabbitmq.password").orElse(null));

        try {
            // vzpostavitev povezave
            connection = factory.newConnection();

            // ustvarjanje channel-a
            channel = connection.createChannel();

            // deklaracija vrste na channel-u
            channel.queueDeclare("dodaniPonudnikiChannel", true, false, false, null);

        } catch (IOException e) {
            log.severe(e.toString());
        } catch (TimeoutException e) {
            log.severe(e.toString());
        }
    }

    @PreDestroy
    private void stop() {

        // zaprtje channel-a in uničenje povezave
        try {
            channel.close();
            connection.close();
        } catch (TimeoutException e) {
            log.severe(e.toString());
        } catch(IOException e) {
            log.severe(e.toString());
        }

    }

    public void posljiObvestiloODodanemPonudniku(Integer ponudnikId) {

        log.info("Pošiljam obvestilo o dodanem ponudniku s ponudnikId: " + ponudnikId);

        try {
            channel.basicPublish("", "dodaniPonudnikiChannel", null, ponudnikId.toString().getBytes());

        } catch (IOException e) {
            log.severe(e.toString());
        }
    }
}