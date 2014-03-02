package com.deweyvm.clock

import com.badlogic.gdx.ApplicationListener

class ClockGame extends ApplicationListener {
  lazy val widget:ClockWidget = new ClockWidget

  def render(){
    widget.update()
    widget.draw()
  }

  def create(){}
  def pause(){}
  def resume(){}
  def dispose(){}
  def resize(width: Int, height: Int){}
}
