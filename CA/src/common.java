
public class common {
    
    public static common cmn=null;
    
    
    public boolean serverstarted = false;
    public String filepath="file_dir/"; //Change the drive letter according to ur machine configuration
    
    public static common getinstance(){
        if(cmn==null){
            cmn= new common();
        }
        
        return cmn;
    }
    
}
