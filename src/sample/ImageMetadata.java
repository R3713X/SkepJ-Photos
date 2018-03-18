public class ImageMetadata(){


    private String [] tagName = new String[100];
    private String [] tagDesc = new String[100];
    
    
    public void InsertImageMetadata (String tName , String tDesc , int i )
    {
        tagName[i] = tName;  
        tagDesc[i] = tDesc;
    }
    
    
    public void ShowBasicTags()
    {
        for(int i =0;i<tagName.length;i++)
        {
            if(tagName.contains("File Name")    ||
               tagName.contains("Date Created") ||
               tagName.contains("Dimensions")   ||
               tagName.contains("Latitude")     ||
               tagName.contains("Longitude")
              )
            {
                System.out.println(tagName[i] + ": " + tagDesc[i]); 
            }
        }
        
    }
    
    
    public String [] GetTagName()
    {
        return tagName;
    }
    
    public String [] GetTagDesc()
    {
        return tagDesc;
    }



}
