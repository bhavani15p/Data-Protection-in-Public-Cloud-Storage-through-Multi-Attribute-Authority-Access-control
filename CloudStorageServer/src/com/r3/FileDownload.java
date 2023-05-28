package com.r3;


import java.io.Serializable;

public class FileDownload extends Message implements Serializable {
private static final long serialVersionUID = 1L;
   public FileDownload()
    {
        type = 2;
    }

    public  String filename;
   
 
   
 
}
