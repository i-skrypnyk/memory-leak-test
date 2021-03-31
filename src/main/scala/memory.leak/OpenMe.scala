package memory.leak

import memory.leak.Implicits.UntypedOptionToTyped

import scala.language.{existentials, reflectiveCalls}

trait OpenMe[T] {

  type NestedType = {def name: String}

  type TNestedLeak = M forSome { type M <: A[M] {
    def firstValue: Long
    def simple2: String
    def nested1: NestedType
    def nested2: NestedType
  }}

  type TWithLeak = M forSome {type M <: A[M] {
    def simple: String
  }}

  /**
   * Not sure what exactly causes the problem.
   * Reopening this file, scrolling through it, highlighting different methods, values and types
   * will cause IDEA to eat more and more memory and eventually start hanging up and complaining about memory usage.
   * It happens over a couple of minutes with this file,
   * and over a couple of second with bigger files in our production project.
   */
  def method(_object: Option[_]): Option[_] = {
    _object.when[TWithLeak].map { d => Option(d) }
    _object.when[TNestedLeak].map {
      d =>
        println(d.nested1.name)
        println(d.nested2.name)
        println(d.firstValue)
        Option(d)
    }
  }
}