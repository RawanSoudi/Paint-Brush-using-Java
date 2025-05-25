# Paint-Brush-using-Java
# Overview
This is a simple yet feature-rich paint application built using Java's AWT (Abstract Window Toolkit) framework. The application allows users to draw various shapes, freehand sketches, erase, and save/load their artwork.

# Features
  Drawing Tools
  Shapes: Rectangle, Oval, Line
  
  Freehand Drawing: Draw freely with mouse movements
  
  Eraser Tool: Remove parts of your drawing
  
  Color Selection: Black, Red, Green, Blue, Yellow
  
  Special Effects:

  Dotted lines
  
  Filled shapes
  
  Utility Functions
  Undo: Remove the last drawn element
  
  Clear All: Reset the canvas
  
  Save/Load: Save drawings as PNG files and load them later
  
  Image Import: Open existing images to edit


  # Techical Implementation
  # Classes
  PaintProject: Main applet class handling UI and drawing logic
  
  Shape: Inner class representing drawn elements
  
  ColorButtonHandler: ActionListener for color buttons
  
  # Key Methods
  paint(): Handles all rendering of shapes and images
  
  saveDrawing(): Saves canvas to PNG file
  
  openImage(): Loads an image onto the canvas
  
  clearAll(): Resets the drawing area
  
  undoLastAction(): Removes the most recent shape


  # Requirements
  Java Runtime Environment (JRE) 8 or later
  
  AWT/Swing libraries (included with Java)


  
