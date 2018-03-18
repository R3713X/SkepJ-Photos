public class ImageMetadata(){


    private String [] tagName = new String[100];
    private String [] tagDesc = new String[100];
    
    
    public void InsertImageMetadata (String tName , String tDesc , int i )
    {
        tagName[i] = tName;  
        tagDesc[i] = tDesc;
    }
    
    
    public String [] getTagName()
    {
        return tagName;
    }
    
    public String [] getTagDesc()
    {
        return tagDesc;
    }



}
