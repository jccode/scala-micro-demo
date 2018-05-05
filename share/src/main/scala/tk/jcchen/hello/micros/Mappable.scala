package tk.jcchen.hello.micros

import scala.reflect.macros.{blackbox, whitebox}

trait Mappable[T] {
  def toMap(t: T): Map[String, Any]
  def fromMap(map: Map[String, Any]): T
}

object Mappable {
  implicit def materializeMappable[T]: Mappable[T] = macro materializeMappableImpl[T]

  def materializeMappableImpl[T : c.WeakTypeTag](c: whitebox.Context): c.Tree = {
    import c.universe._

    val tpe = weakTypeOf[T]
    val companion = tpe.typeSymbol.companion
    val fields = tpe.decls.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor => m
    }.get.paramLists.head
    val (toMapParams, fromMapParams) = fields.map {field =>
      val name = field.name.toTermName
      val decoded = name.decodedName.toString
      val returnType = tpe.decl(name).typeSignature
      (q"$decoded -> t.$name", q"map($decoded).asInstanceOf[$returnType]")
    }.unzip

    q"""
       new Mappable[$tpe] {
         def toMap(t: $tpe): Map[String, Any] = Map(..$toMapParams)
         def fromMap(map: Map[String, Any]): $tpe = $companion(..$fromMapParams)
       }
     """
  }
}
