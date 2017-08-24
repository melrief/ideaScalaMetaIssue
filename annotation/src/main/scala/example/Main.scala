package example

import scala.meta._
import scala.collection.immutable.Seq

class Main extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"object $name { ..$stats }" =>
        MainMacroImpl.expand(name, stats)
      case _ =>
        abort("@main must annotate an object.")
    }
  }
}

// This is an example how we can refactor the macro implementation into a utility
// function which can be used for unit testing, see MainUnitTest.
object MainMacroImpl {
  def expand(name: Term.Name, stats: Seq[Stat]): Stat = {
    val main = q"def main(args: Array[String]): Unit = { ..$stats }"
    val newObjectName = Term.Name(name.value + "Aux")
    q"""object $name { $main };

       object $newObjectName { $main };
     """
  }
}