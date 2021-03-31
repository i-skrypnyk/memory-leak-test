package memory.leak

trait A[+T] {
  self: {
    def field: Map[String, Any]
    def anotherField(d: Map[String, Any]): T
  } =>
}
