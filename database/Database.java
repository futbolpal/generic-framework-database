package database;

import java.io.IOException;
import java.util.ArrayList;

import framework.ObjectFactory;

/**
 * Designed for singleton implementation
 */
public abstract class Database
{
  protected static Database this_;
  
  private static ArrayList<DatabaseListener> listeners_ = new ArrayList<DatabaseListener>();
  
  private ArrayList<DatabaseRecord> records_;
  
  private boolean save_;

  protected Database()
  {
    records_ = new ArrayList<DatabaseRecord>();
    save_ = true;
  }
  
  public static void addListener(DatabaseListener l)
  {
    listeners_.add(l);
  }
  
  public static void removeListener(DatabaseListener l)
  {
    listeners_.remove(l);
  }

  public void add(DatabaseRecord r)
  {
    records_.add(r);
    fireAdded(r);
    save_ = true;
  }

  public void remove(DatabaseRecord r)
  {
    records_.remove(r);
    fireRemoved(r);
    save_ = true;
  }
  
  public int getSize()
  {
    return records_.size();
  }
  
  public boolean isSaved()
  {
    return !save_;
  }

  public boolean isEmpty()
  {
    return records_.isEmpty();
  }
  
  public void load(DatabaseReader in)
  {
    try
    {
      int records = in.readInt();
      for(int i = 0; i < records; i++)
      {
        String class_name = in.readString();
        DatabaseRecord r = (DatabaseRecord)ObjectFactory.createObject(class_name);
        r.load(in);
        this.add(r);
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    fireLoadComplete();
  }
  
  public void save(DatabaseWriter out)
  {
    try
    {
      out.writeInt(records_.size());
      for(DatabaseRecord r:records_)
      {
        out.writeString(r.getClass().getName());
        r.save(out);
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    fireSaveComplete();
  }

  public void fireAdded(DatabaseRecord r)
  {
    for(DatabaseListener l:listeners_)
    {
      l.recordAdded(r);
    }    
  }
  
  public void fireRemoved(DatabaseRecord r)
  {
    for(DatabaseListener l:listeners_)
    {
      l.recordRemoved(r);
    }    
  }
  
  public void fireLoadComplete()
  {
    for(DatabaseListener l:listeners_)
    {
      l.databaseLoaded();
    }    
  }
  
  public void fireSaveComplete()
  {
    for(DatabaseListener l:listeners_)
    {
      l.databaseSaved();
    }
  }
}
