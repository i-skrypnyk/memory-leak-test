package memory.leak

import scala.reflect.ClassTag

object Implicits {

  implicit class UntypedOptionToTyped(option: Option[_]) {
    def when[A: ClassTag]: Option[A] = option.collect { case a: A => a }
  }

}
