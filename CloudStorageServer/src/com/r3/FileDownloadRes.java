package com.r3;


import java.io.Serializable;

public class FileDownloadRes extends Message implements Serializable {
private static final long serialVersionUID = 1L;

  public  FileDownloadRes()
    {
        type = 4;
    }

    int error = 0; // -1 in case of error

    byte [] filecontents;

   String filename;
}
