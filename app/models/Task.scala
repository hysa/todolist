package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

/**
 * Created with IntelliJ IDEA.
 * User: hysa
 * Date: 2013/05/02
 * Time: 1:42
 * To change this template use File | Settings | File Templates.
 */
case class Task(id: Long, label: String, description: String)

object Task {
  val task = {
    get[Long]("id") ~
    get[String]("label") ~
    get[String]("description") map {
      case id~label~description => Task(id, label, description)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL(
      """
        | select id, label, description
        | from task
      """.stripMargin).as(task *)
  }

  def create(label: String, description: String) {
    DB.withConnection { implicit c =>
      SQL(
        """
          |insert into task (label, description)
          |values ({label}, {description})
        """.stripMargin).on(
          'label -> label,
          'description -> description
        ).executeUpdate()
     }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL(
        """
          |delete from task
          |where id = {id}
        """.stripMargin).on(
          'id -> id
        ).executeUpdate()
    }
  }
}
