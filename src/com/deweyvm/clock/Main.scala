package com.deweyvm.clock

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas
import java.awt.BorderLayout
import javax.swing.JFrame
import java.awt.event.{MouseEvent, MouseAdapter}
import com.badlogic.gdx.Gdx

object Main {
  def main(args:Array[String]) {
    var (posX, posY) = (0,0)
    val (width, height) = (300,60)
    val canvas = new LwjglAWTCanvas(new ClockGame, true)
    val frame = new JFrame
    val mode = Gdx.graphics.getDesktopDisplayMode
    val y = mode.height - height
    val x = (mode.width/3)*2
    canvas.getCanvas.addMouseListener(new MouseAdapter() {
      override def mousePressed(e:MouseEvent) {
        posX = e.getX
        posY = e.getY
      }
    })
    canvas.getCanvas.addMouseMotionListener(new MouseAdapter() {
      override def mouseDragged(e:MouseEvent) {
        frame.setLocation(e.getXOnScreen - posX, e.getYOnScreen - posY)
      }
    })
    frame.setAlwaysOnTop(true)
    frame.setUndecorated(true)
    frame.getContentPane.add(canvas.getCanvas, BorderLayout.CENTER)
    frame.setSize(width, height)
    frame.setLocation(x, y)
    frame.setVisible(true)
  }
}
