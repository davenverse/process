package io.chrisdavenport.process

import munit.CatsEffectSuite
import cats.effect._

class ChildProcessSpec extends CatsEffectSuite {

  test("Main should exit succesfully") {
    assertEquals(true, true)
  }

}
