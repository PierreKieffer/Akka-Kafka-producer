import java.util.Properties

import akka.Done
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import spray.json.JsonFormat
import utils.AppConfiguration

import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global

trait kafkaProducer extends AppConfiguration {

  // Kafka producer configuration
  def instanciateProducer : KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrapServers)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    producer
  }

  def pushMessageToKafka(message : String, producer : KafkaProducer[String,String]) : Future[Done] = {
    val record = new ProducerRecord(topic, "key", message)
    producer.send(record)
    Future{Done}
  }

}
