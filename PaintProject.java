import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



public class PaintProject extends Applet {
    private Color currentColor = Color.BLACK;
    private String currentShape = "FreeHand";
	private String lastShape = "FreeHand"; 
    private int startX, startY, endX, endY;
    private boolean isDragging = false;
	private boolean isErasing = false;
	private boolean isDotted = false;
	private boolean isFilled = false;
	

    private Button redButton, greenButton, blueButton, yellowButton, blackButton;
    private Button rectButton, ovalButton, lineButton;
	private Button freeHandButton;
	private Button eraserButton;
	private Button clearAllButton;
	private Button undoButton;
	private Button saveButton;
	private Button openButton;
	private Checkbox dottedCheckbox;
	private Checkbox filledCheckbox;
	


    
    private List<Shape> shapes = new ArrayList<>();
	private List<Point> currentFreehandPoints = new ArrayList<>();
	private BufferedImage loadedImage;

    public void init() {
        setLayout(new BorderLayout());

        
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());

        
        redButton = new Button();
		redButton.setBackground(Color.RED);
		redButton.setPreferredSize(new Dimension(30, 30)); 
		

		greenButton = new Button();
		greenButton.setBackground(Color.GREEN);
		greenButton.setPreferredSize(new Dimension(30, 30));
		

		blueButton = new Button();
		blueButton.setBackground(Color.BLUE);
		blueButton.setPreferredSize(new Dimension(30, 30));
		

		yellowButton = new Button();
		yellowButton.setBackground(Color.YELLOW);
		yellowButton.setPreferredSize(new Dimension(30, 30));
		

		blackButton = new Button();
		blackButton.setBackground(Color.BLACK);
		blackButton.setPreferredSize(new Dimension(30, 30));
		


        
        rectButton = new Button("Rectangle");
        ovalButton = new Button("Oval");
        lineButton = new Button("Line");

        freeHandButton = new Button("Free Hand");
		
		eraserButton = new Button("Erase");
		
		clearAllButton = new Button("Clear All");
		
		undoButton = new Button("Undo");
		
		saveButton = new Button("Save");
		
		openButton = new Button("Open");
		
		
		dottedCheckbox = new Checkbox("Dotted");
		filledCheckbox = new Checkbox ("Filled");
		
		

        redButton.addActionListener(new ColorButtonHandler(Color.RED));
        greenButton.addActionListener(new ColorButtonHandler(Color.GREEN));
        blueButton.addActionListener(new ColorButtonHandler(Color.BLUE));
        yellowButton.addActionListener(new ColorButtonHandler(Color.YELLOW));
        blackButton.addActionListener(new ColorButtonHandler(Color.BLACK));

        
        rectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isErasing = false; 
				currentShape = "Rectangle";
			}
		});

		
       ovalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isErasing = false;
				currentShape = "Oval";
			}
		});

		lineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isErasing = false;
				currentShape = "Line";
			}
		});


        freeHandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isErasing = false;
				currentShape = "FreeHand";
			}
		});
		
	

		eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isErasing = !isErasing;
				if (isErasing) {
					eraserButton.setBackground(Color.GRAY); 
					lastShape = currentShape; 
					currentShape = "FreeHand"; 
					currentColor = getBackground(); 
				} else {
					eraserButton.setBackground(null); 
					currentShape = lastShape; 
					currentColor = Color.BLACK; 
				}
			}
});



	
		clearAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAll();
			}
		});

		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undoLastAction();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDrawing();
            }
        });
		
		
		openButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				openImage();
			}
		});


		dottedCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				isDotted = dottedCheckbox.getState();
			}
		});

		filledCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				isFilled = filledCheckbox.getState();
			}
		});

		Label colorsLabel = new Label("Colors");
		Font bigFont = new Font("SansSerif", Font.BOLD, 16); 
		colorsLabel.setFont(bigFont);

		buttonPanel.add(colorsLabel);
		
        buttonPanel.add(redButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(yellowButton);
        buttonPanel.add(blackButton);
		
		Label shapesLabel = new Label("Shapes");
		shapesLabel.setFont(bigFont);
		buttonPanel.add(shapesLabel);

		
        buttonPanel.add(rectButton);
        buttonPanel.add(ovalButton);
        buttonPanel.add(lineButton);
		buttonPanel.add(freeHandButton);
		
		Label optionLabel = new Label("Options");
		optionLabel.setFont(bigFont);
		buttonPanel.add(optionLabel);
		
		buttonPanel.add(eraserButton);
		buttonPanel.add(clearAllButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(saveButton);
        buttonPanel.add(openButton);

		Label CheckLabel = new Label("Check Points");
		CheckLabel.setFont(bigFont);
		buttonPanel.add(CheckLabel);
		
		buttonPanel.add(dottedCheckbox);
		buttonPanel.add(filledCheckbox);
		

       
        add(buttonPanel, BorderLayout.SOUTH);

        setBackground(Color.WHITE);

        
        addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startX = e.getX();
				startY = e.getY();
				isDragging = true;
				
				if (currentShape.equals("FreeHand")) {
					currentFreehandPoints.clear(); 
					currentFreehandPoints.add(new Point(startX, startY));
        }
			}

			public void mouseReleased(MouseEvent e) {
				if (currentShape.equals("FreeHand")) {
					shapes.add(new Shape(currentColor, currentFreehandPoints, isDotted));
				} else {
					endX = e.getX();
					endY = e.getY();
					shapes.add(new Shape(currentColor, currentShape, startX, startY, endX, endY, isDotted, isFilled));
				}
				isDragging = false;
				repaint();
		}
});

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isErasing) {
                    shapes.add(new Shape(getBackground(), "Line", startX, startY, e.getX(), e.getY(),isDotted,isFilled));
                    startX = e.getX();
                    startY = e.getY();
                    repaint();
                } else if (currentShape.equals("FreeHand")) {
                    currentFreehandPoints.add(new Point(e.getX(), e.getY())); 
					repaint();
                } else {
                    endX = e.getX();
                    endY = e.getY();
                    repaint();
                }
            }
        });

    }
	
	private void clearAll() {
		shapes.clear(); 
		loadedImage = null;
		repaint(); 
	}

	private void undoLastAction() {
		if (!shapes.isEmpty()) {
			shapes.remove(shapes.size() - 1); 
			repaint(); 
		}
	}
	

	private void saveDrawing() {
    
    FileDialog fileDialog = new FileDialog(new Frame(), "Save Drawing", FileDialog.SAVE);
    fileDialog.setFile("drawing.png"); 
    fileDialog.setVisible(true); 

    
    String directory = fileDialog.getDirectory();
    String fileName = fileDialog.getFile();

    
    if (directory != null && fileName != null) {
        File file = new File(directory, fileName);

        
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        
        paint(g2d);

        
        try {
            ImageIO.write(image, "PNG", file);
            JOptionPane.showMessageDialog(this, "Drawing saved to " + file.getAbsolutePath(), "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save drawing: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            g2d.dispose();
        }
    }
}

	private void openImage() {
        FileDialog fileDialog = new FileDialog(new Frame(), "Open Image", FileDialog.LOAD);
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String fileName = fileDialog.getFile();

        if (directory != null && fileName != null) {
            File file = new File(directory, fileName);

            try {
                loadedImage = ImageIO.read(file); 
                repaint(); 
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g; 
	
	if (loadedImage != null) {
            g2d.drawImage(loadedImage, 0, 0, this);
        }

    for (Shape shape : shapes) {
        g2d.setColor(shape.color);

        if (shape.isDotted) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke(2));
        }

        switch (shape.type) {
            case "Rectangle":
                if (shape.isFilled) {
                    g2d.fillRect(Math.min(shape.startX, shape.endX), Math.min(shape.startY, shape.endY),
                                 Math.abs(shape.endX - shape.startX), Math.abs(shape.endY - shape.startY));
                } else {
                    g2d.drawRect(Math.min(shape.startX, shape.endX), Math.min(shape.startY, shape.endY),
                                 Math.abs(shape.endX - shape.startX), Math.abs(shape.endY - shape.startY));
                }
                break;
            case "Oval":
                if (shape.isFilled) {
                    g2d.fillOval(Math.min(shape.startX, shape.endX), Math.min(shape.startY, shape.endY),
                                 Math.abs(shape.endX - shape.startX), Math.abs(shape.endY - shape.startY));
                } else {
                    g2d.drawOval(Math.min(shape.startX, shape.endX), Math.min(shape.startY, shape.endY),
                                 Math.abs(shape.endX - shape.startX), Math.abs(shape.endY - shape.startY));
                }
                break;
            case "Line":
                g2d.drawLine(shape.startX, shape.startY, shape.endX, shape.endY);
                break;
			
			case "FreeHand":
                
                for (int i = 0; i < shape.points.size() - 1; i++) {
                    Point p1 = shape.points.get(i);
                    Point p2 = shape.points.get(i + 1);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
                break;
        }
    }

    if (isDragging) {
        g2d.setColor(currentColor);

        if (isDotted) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke(2));
        }

        switch (currentShape) {
            case "Rectangle":
                if (isFilled) {
                    g2d.fillRect(Math.min(startX, endX), Math.min(startY, endY),
                                 Math.abs(endX - startX), Math.abs(endY - startY));
                } else {
                    g2d.drawRect(Math.min(startX, endX), Math.min(startY, endY),
                                 Math.abs(endX - startX), Math.abs(endY - startY));
                }
                break;
            case "Oval":
                if (isFilled) {
                    g2d.fillOval(Math.min(startX, endX), Math.min(startY, endY),
                                 Math.abs(endX - startX), Math.abs(endY - startY));
                } else {
                    g2d.drawOval(Math.min(startX, endX), Math.min(startY, endY),
                                 Math.abs(endX - startX), Math.abs(endY - startY));
                }
                break;
            case "Line":
                g2d.drawLine(startX, startY, endX, endY);
                break;
        }
    }
}


    
    private class ColorButtonHandler implements ActionListener {
        private Color color;

        public ColorButtonHandler(Color color) {
            this.color = color;
        }

        public void actionPerformed(ActionEvent e) {
            currentColor = color;
        }
    }
	
	

    private class Shape {
        Color color;
        String type;
        int startX, startY, endX, endY;
		boolean isDotted,isFilled;
		List<Point> points;
		

        public Shape(Color color, String type, int startX, int startY, int endX, int endY, boolean isDotted,boolean isFilled) {
            this.color = color;
            this.type = type;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
			this.isDotted = isDotted;
			this.isFilled = isFilled;
			this.points = null;
        }
		public Shape(Color color, List<Point> points, boolean isDotted) {
			this.color = color;
			this.type = "FreeHand";
			this.isDotted = isDotted;
			this.points = new ArrayList<>(points); 
    }
		
		
    }
}