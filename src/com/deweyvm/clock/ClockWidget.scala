package com.deweyvm.clock

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.GL20
import java.util.Calendar
import com.badlogic.gdx.graphics.Color

class ClockWidget {
  val fontFile = Gdx.files.internal("jackinput.ttf")
  val size = 46
  val fontGen = new FreeTypeFontGenerator(fontFile)
  val font = makeFont
  val batch = new SpriteBatch
  val dayNames = List("A", "B", "C", "D", "E", "F")
  def makeFont = fontGen.generateFont(size)
  def update() {
    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      Gdx.app.exit()
    }
  }

  def getColor(hour:Int):Color = {
    val h = 1 - (hour/100f + 0.3f)
    val c = java.awt.Color.getHSBColor(h, 1, 1)
    val r = c.getRed / 255f
    val b = c.getBlue / 255f
    val g = c.getGreen / 255f
    new Color(r, g, b, 1)
  }


  def getDrawable:(Color,String) = {
    val cal = Calendar.getInstance()
    val day = cal.get(Calendar.DAY_OF_YEAR)
    val dayName = if (day == 366) {
      dayNames.last
    } else {
      dayNames((day - 1) % 5)
    }
    val hour = ((cal.get(Calendar.HOUR_OF_DAY) - 6) + 24) % 24
    val minute = cal.get(Calendar.MINUTE)
    val second = cal.get(Calendar.SECOND)
    val milli = cal.get(Calendar.MILLISECOND)

    val hoursFloat = 100*(hour + minute/60f + second/3600f + milli/(3600f*1000f))/24f
    val hoursInt = hoursFloat.toInt
    val minutesFloat = (hoursFloat - hoursInt)*100
    val minutesInt = minutesFloat.toInt
    val secondsFloat = (minutesFloat - minutesInt)*100
    val secondsInt = secondsFloat.toInt
    val s = "%s %02d:%02d:%02d" format (dayName, hoursInt, minutesInt, secondsInt)
    val color = getColor(hoursFloat.toInt)
    (color, s)
  }

  def draw() {
    Gdx.gl.glClearColor(0,0,0,1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    batch.begin()
    val (color, text) = getDrawable
    font.setColor(color.add(0.3f,0.3f,0.3f,0))
    font.draw(batch, text, 10, size)
    batch.end()
    Thread.sleep(32)
  }
}
