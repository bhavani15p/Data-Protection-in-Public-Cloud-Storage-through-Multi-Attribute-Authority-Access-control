package com.r3;


import java.io.Serializable;


public class FileUPLOAD extends Message implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public FileUPLOAD()
    {
        type=1;
    }

    public String filename;
    
  
    
    public String username;

    byte [] filecontent;

   


}
