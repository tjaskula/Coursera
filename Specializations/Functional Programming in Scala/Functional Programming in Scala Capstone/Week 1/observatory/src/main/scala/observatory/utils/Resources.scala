package observatory.utils

import java.nio.file.Paths

object Resources {

  /** @return The filesystem path of the given resource */
  def fsPath(resource: String): String =
    Paths.get(getClass.getResource(resource).toURI).toString
}
