package com.spaceshooter.framework;

import com.spaceshooter.framework.Graphics.ImageFormat;

public interface Image {
	
    public int getWidth();
    
    public int getHeight();
    
    public ImageFormat getFormat();
    
    public void dispose();
}
