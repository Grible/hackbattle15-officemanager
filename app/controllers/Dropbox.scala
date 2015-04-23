package controllers

import java.io.{InputStream, ByteArrayInputStream}
import java.nio.charset.StandardCharsets

import com.dropbox.core._
import dropbox4s.core.CoreApi
import play.api.cache.Cache
import play.api.libs.json.{Json, JsValue}
import play.api.{Configuration, Play}
import play.api.mvc._
import play.filters.csrf._
import scalaj.http.Http
import play.api.Play.current

/**
 * Created by Lennart on 22/04/15.
 */
object Dropbox extends Controller with CoreApi {

  val app: play.api.Application = Play.unsafeApplication
  val conf: Configuration = app.configuration

  // Get values from the config file. Don't handle the Option
  // as we want the application to crash if they are not
  // provided
  val applicationName = conf.getString("dropbox.app.name").get
  val version = conf.getString("dropbox.app.version").get
  val redirectUri = conf.getString("dropbox.app.redirectUri").get
  val appKey = conf.getString("dropbox.app.key").get
  val appSecret = conf.getString("dropbox.app.secret").get

  val appInfo = new DbxAppInfo(appKey, appSecret)
  val webAuth = new DbxWebAuthNoRedirect(requestConfig, appInfo)

  def index = CSRFAddToken {
    Action { implicit req =>
      val authorizationUrl: String = webAuth.start()
      println(s"authorization url: $authorizationUrl")
      val csrfToken = CSRF.getToken(req).get.value
      Ok(views.html.dropbox.index(appKey, redirectUri, csrfToken))
    }
  }

  def authFinish(code: String, state: String) = CSRFCheck {
    Action { implicit req =>
      // Workaround to finish authentication (can't finish with Dropbox SDK due to Play not adhering to standard Servlet specifications)
      val response = Http("https://api.dropbox.com/1/oauth2/token")
        .postForm(Seq("code" -> code, "grant_type" -> "authorization_code", "redirect_uri" -> redirectUri))
        .auth(appKey, appSecret)
        .asString

      if (response.code == 200) {
        val json: JsValue = Json.parse(response.body)
        val accessToken: String = (json \ "access_token").as[String]
        implicit val auth: DbxAuthFinish = new DbxAuthFinish(accessToken, "", "")

        println(s"Access token: $accessToken")
        println("Linked account: " + this.accountInfo.displayName)
        println("Account info: " + this.accountInfo)

        val userId = this.accountInfo.userId.toString

        Cache.set(userId, accessToken)

        Ok(views.html.index("Finished authentication")).addingToSession("user" -> userId)
      } else {
        InternalServerError("Error finishing the oAuth process: " + response.body)
      }

    }
  }

  def upload(inputStream: InputStream, uploadPath: String, userId: String) = {
    val accessToken = Cache.get(userId).getOrElse(throw new Exception("You haven't given Naggy access to your Dropbox account yet!")).toString
    implicit val auth: DbxAuthFinish = new DbxAuthFinish(accessToken, "", "")
    val client: DbxClient = this.client(accessToken)

    client.uploadFile(uploadPath, DbxWriteMode.force(), inputStream.available(), inputStream)
  }

  def uploadFileTest() = {
    Action { implicit req =>
      try {
        val userId = req.session.get("user").get
        val testString = "Leon - 0612345678"
        val inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8))
        val uploadPath = "/test.txt"
        upload(inputStream, uploadPath, userId)

        Ok(views.html.index("Done"))
      } catch {
        case e: Exception =>
          InternalServerError(e.getMessage)
      }
    }
  }

}
