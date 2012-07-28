package database;

import java.io.IOException;


public abstract class DatabaseRecord
{
  private static double VERSION = 1.0;

  private double version_;

  private String class_;

  public DatabaseRecord()
  {
    version_ = VERSION;
  }

  public String getType()
  {
    return null;
  }

  public String getClassName()
  {
    return class_;
  }
  
  public void load(DatabaseReader in)
  {
    try
    {
      version_ = in.readDouble();
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }    
  }

  public void save(DatabaseWriter out)
  {
    try
    {
      out.writeDouble(version_);
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }

  public void onClose()
  {

  }
}
