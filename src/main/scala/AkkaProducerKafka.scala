import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{as, complete, entity, onComplete, path, post}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.io.StdIn

object AkkaProducerKafka extends kafkaProducer {

  // curl -H "Content-Type: application/json" -X POST -d 'test message' http://localhost:8080/sendMessage

  // Actors configuration for the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher


  def main(args: Array[String]) {

    initializeAppConfig(args(0))

    val producer = instanciateProducer

    val route: Route =
        post {
          path("sendMessage") {

            entity(as[String]) { message =>
              val pushed: Future[Done] = pushMessageToKafka(message, producer)
              onComplete(pushed) {done =>
                complete("Message pushed to kafka")
              }
            }
          }
        }


    val bindingFuture = Http().bindAndHandle(route, interface, port.toInt)
    println(s"Server online at http://$interface:$port/")

    StdIn.readLine() // Press enter to stop the server
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ ⇒ system.terminate()) // close server

    producer.close() // close kafka producer

  }

}
