package sample;

public class ImageMetadata{


    private String [] tagName = new String[100];
    private String [] tagDesc = new String[100];

    
    public void InsertImageMetadata (String tName , String tDesc , int i )
    {
        tagName[i] = tName;
        tagDesc[i] = tDesc;
    }
    
    
    public void ShowBasicTags()
    {
        int i = 0;

        while (tagName[i] != null)
        {
            if(
               tagName[i].equals("File Name") ||
               tagName[i].equals("File Modified Date")  ||
               tagName[i].equals("Subject Distance Range")
               )
            {
                System.out.println(tagName[i] + ": " + tagDesc[i]);
            }

            i++;
        }
        
    }

    public void ShowAllTags()
    {
        int i = 0;

        while (tagName[i] != null)
        {
            System.out.println(tagName[i] + ": " + tagDesc[i]);
            i++;
        }

    }



}
