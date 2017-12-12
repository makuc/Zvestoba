package si.fri.prpo.zvestoba.zrna;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.rabbitmq.client.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.ws.rs.Consumes;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@ApplicationScoped
public class DodaniPonudnikiWorker {

    private Logger log = Logger.getLogger(DodaniPonudnikiWorker.class.getName());

    private Connection connection;
    private Channel channel;


    private void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        ConfigurationUtil configurationUtil = ConfigurationUtil.getInstance();

        ConnectionFactory factory = new ConnectionFactory();

        // konfiguracija factory-ja
        factory.setHost(ConfigurationUtil.getInstance().get("rabbitmq.host").orElse(null));
        factory.setUsername(ConfigurationUtil.getInstance().get("rabbitmq.username").orElse(null));
        factory.setPassword(ConfigurationUtil.getInstance().get("rabbitmq.password").orElse(null));


        // dodajanje Consumer-ja na channel
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                // Prejet ID
                String ponudnikId = new String(body, "UTF-8");
                log.info("Dodan ponudnik z ID-jem: " + ponudnikId);

                // Implementacija odziva na dogodke
                // Smo preveč leni
            }
        };
        try {
            // vzpostavitev povezave
            connection = factory.newConnection();

            // ustvarjanje channel-a
            channel = connection.createChannel();
            // deklaracija vrste na channel-u
            channel.queueDeclare("dodaniPonudnikiChannel", true, false, false, null);
            // dodajanje poslušalca na channel
            channel.basicConsume("dodaniPonudnikiChannel", true, consumer);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }

    private void stop(@Observes @Destroyed(ApplicationScoped.class) Object destroyed) {

        // zaprtje channel-a in uničenje povezave
        try {
            channel.close();
            connection.close();
        } catch (TimeoutException |IOException e) {
            log.severe(e.toString());
        }

    }
}
