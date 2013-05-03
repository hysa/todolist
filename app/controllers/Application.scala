package controllers

import play.api._
import play.api.mvc._
import models.Task
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {

  val taskForm = Forms(
    "label" -> nonEmptyText,
    "description" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }

  def task(id: Long) = Action {
    Ok(views.html.task(Task.read(id)))
  }

  def deleteTask(id: Long) = Action { implicit request =>
    Task.delete(id)
    Redirect(routes.Application.tasks)

  }
}