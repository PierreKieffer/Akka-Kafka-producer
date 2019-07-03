package utils

import java.io.{File, FileInputStream}
import java.util
import java.net.URI
import org.yaml.snakeyaml.Yaml


trait AppConfiguration {
  private var props : java.util.HashMap[String,String] = new util.HashMap

  var bootstrapServers = ""
  var interface = ""
  var port = ""
  var topic = ""


  def initializeAppConfig(configFilePath : String) : Unit = {

    val fileInputStream = new FileInputStream(new File(configFilePath))
    val confObj = new Yaml().load(fileInputStream)

    props = confObj.asInstanceOf[java.util.HashMap[String,String]]
    bootstrapServers = props.get("bootstrapServers")
    interface = props.get("interface")
    port = props.get("port")
    topic = props.get("topic")

  }
}
